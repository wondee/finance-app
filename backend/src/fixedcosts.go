package main

import (
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

type JsonFixedCost struct {
	ID       int        `json:"id"`
	Name     string     `json:"name"`
	Amount   int        `json:"amount"`
	From     *YearMonth `json:"from"`
	To       *YearMonth `json:"to"`
	DueMonth int        `json:"dueMonth"`
}

func GetFixedCosts(c *gin.Context) {
	c.IndentedJSON(http.StatusOK, createFixedCosts())
}

func SaveMonthlyFixedCosts(c *gin.Context) {
	var cost JsonFixedCost
	err := c.ShouldBindJSON(&cost)

	if err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	dbObject := ToDBStruct(&cost, func(_ int) []int {
		return ALL_MONTHS
	})

	SaveFixedObject(&dbObject)

	c.Status(http.StatusOK)
}

func createFixedCosts() Response {

	costs := LoadFixedCosts()
	currentBalance := 0
	currentYearMonth := CurrentYearMonth()

	monthly := make([]JsonFixedCost, 0)
	quaterly := make([]JsonFixedCost, 0)
	halfyearly := make([]JsonFixedCost, 0)
	yearly := make([]JsonFixedCost, 0)

	for _, cost := range costs {
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

func ToDBStruct(jsonObject *JsonFixedCost, dueMonthCreator func(int) []int) FixedCost {
	return FixedCost{
		ID:       jsonObject.ID,
		Name:     jsonObject.Name,
		Amount:   jsonObject.Amount,
		From:     jsonObject.From,
		To:       jsonObject.To,
		DueMonth: dueMonthCreator(jsonObject.DueMonth),
	}
}
