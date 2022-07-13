package main

import (
	"errors"
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

type Response struct {
	CurrentBalance int             `json:"currentBalance"`
	Monthly        []JsonFixedCost `json:"monthly"`
	Quarterly      []JsonFixedCost `json:"quarterly"`
	Halfyearly     []JsonFixedCost `json:"halfyearly"`
	Yearly         []JsonFixedCost `json:"yearly"`
}

type Balance struct {
	From    *YearMonth `json:"from"`
	To      *YearMonth `json:"to"`
	Balance int        `json:"balance"`
}

type JsonFixedCost struct {
	ID       int        `json:"id"`
	Name     string     `json:"name"`
	Amount   int        `json:"amount"`
	From     *YearMonth `json:"from"`
	To       *YearMonth `json:"to"`
	DueMonth int        `json:"dueMonth"`
}

func GetFixedCosts(c *gin.Context) {
	c.IndentedJSON(http.StatusOK,
		createFixedCosts(LoadCurrentFinanceId(c)),
	)
}

func DeleteFixedCosts(c *gin.Context) {
	param := c.Param("id")
	id, err := strconv.Atoi(param)

	if err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	DeleteFixedCost(id, LoadCurrentFinanceId(c))
}

func SaveYearlyFixedCosts(c *gin.Context) {
	saveFixedCost(c, func(dueMonth int) ([]int, error) {
		if dueMonth < 0 || dueMonth > 12 {
			return nil, errors.New("dueMonth for yearly must be between 1 and 12")
		}
		return []int{dueMonth}, nil
	})
}

func SaveHalfYearlyFixedCosts(c *gin.Context) {
	saveFixedCost(c, func(dueMonth int) ([]int, error) {
		if dueMonth < 0 || dueMonth > 6 {
			return nil, errors.New("dueMonth for halfyearly must be between 1 and 6")
		}

		return []int{dueMonth, dueMonth + 6}, nil
	})
}

func SaveQuaterlyFixedCosts(c *gin.Context) {
	saveFixedCost(c, func(dueMonth int) ([]int, error) {
		if dueMonth < 0 || dueMonth > 3 {
			return nil, errors.New("dueMonth for halfyearly must be between 1 and 3")
		}

		return []int{dueMonth, dueMonth + 3, dueMonth + 6, dueMonth + 9}, nil
	})
}

func SaveMonthlyFixedCosts(c *gin.Context) {
	saveFixedCost(c, func(_ int) ([]int, error) {
		return ALL_MONTHS, nil
	})
}

func saveFixedCost(c *gin.Context, dueMonthConverter func(int) ([]int, error)) {
	var cost JsonFixedCost
	err := c.ShouldBindJSON(&cost)

	if err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	dbObject, err := ToDBStruct(&cost, dueMonthConverter)

	if err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	dbObject.FinanceID = LoadCurrentFinanceId(c)

	SaveFixedObject(dbObject)
}

func createFixedCosts(financeId int) Response {

	costs := LoadFixedCosts(financeId)
	currentBalance := 0
	currentYearMonth := CurrentYearMonth()

	monthly := make([]JsonFixedCost, 0)
	quaterly := make([]JsonFixedCost, 0)
	halfyearly := make([]JsonFixedCost, 0)
	yearly := make([]JsonFixedCost, 0)

	for _, cost := range *costs {
		if IsRelevant(currentYearMonth, cost.From, cost.To) {
			currentBalance += cost.Amount
		}

		switch month := len(cost.DueMonth); month {
		case 1:
			yearly = append(yearly, ToJsonStruct(&cost))
		case 2:
			halfyearly = append(halfyearly, ToJsonStruct(&cost))
		case 4:
			quaterly = append(quaterly, ToJsonStruct(&cost))
		case 12:
			monthly = append(monthly, ToJsonStruct(&cost))
		default:
			panic("only 1, 2, 4 and 12 is valid, but was " + strconv.Itoa(month))
		}

	}

	return Response{
		CurrentBalance: currentBalance,
		Monthly:        monthly,
		Quarterly:      quaterly,
		Halfyearly:     halfyearly,
		Yearly:         yearly,
	}
}

func ToJsonStruct(dbObject *FixedCost) JsonFixedCost {
	return JsonFixedCost{
		ID:       dbObject.ID,
		Name:     dbObject.Name,
		Amount:   dbObject.Amount,
		From:     dbObject.From,
		To:       dbObject.To,
		DueMonth: dbObject.DueMonth[0],
	}
}

func ToDBStruct(
	jsonObject *JsonFixedCost,
	dueMonthCreator func(int) ([]int, error),
) (*FixedCost, error) {
	value, err := dueMonthCreator(jsonObject.DueMonth)

	if err != nil {
		return nil, err
	}

	return &FixedCost{
		ID:       jsonObject.ID,
		Name:     jsonObject.Name,
		Amount:   jsonObject.Amount,
		From:     jsonObject.From,
		To:       jsonObject.To,
		DueMonth: value,
	}, nil
}
