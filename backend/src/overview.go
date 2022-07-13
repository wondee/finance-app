package main

import (
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
)

const MAX_ENTRIES = 10 // 50

type Overview struct {
	CurrentAmount int             `json:"currentAmount"`
	Entries       []OverviewEntry `json:"entries"`
}

type OverviewEntry struct {
	YearMonth       YearMonth `json:"yearMonth"`
	CurrentAmount   int       `json:"currentAmount"`
	SumFixedCosts   int       `json:"sumFixedCosts"`
	SumSpecialCosts int       `json:"sumSpecialCosts"`
}

type CostDetail struct {
	Name   string `json:"name"`
	Amount int    `json:"amount"`
}

type FixedCostDetail struct {
	CostDetail
	DisplayType string `json:"displayType"`
}

type OverviewDetail struct {
	FixedCosts   []FixedCostDetail `json:"fixedCosts"`
	SpecialCosts []CostDetail      `json:"specialCosts"`
}

func GetOverview(c *gin.Context) {
	c.IndentedJSON(http.StatusOK, createOverview(LoadCurrentFinanceId(c)))
}

func GetOverviewDetail(c *gin.Context) {
	n, err := strconv.Atoi(c.Query("index"))

	if err != nil {
		c.AbortWithStatus(http.StatusBadRequest)
		return
	}

	c.IndentedJSON(
		http.StatusOK,
		createOverviewDetail(LoadCurrentFinanceId(c), n),
	)
}

func createOverviewDetail(financeId int, n int) OverviewDetail {
	if n > MAX_ENTRIES {
		return OverviewDetail{}
	}

	relevantFixedCostsMap := createRelevantMap(financeId)
	specialCostMap := createSpecialCostMap(financeId)

	yearMonth := AddMonths(CurrentYearMonth(), n)

	fixedCosts := make([]FixedCostDetail, 0)
	specialCosts := make([]CostDetail, 0)

	for _, cost := range relevantFixedCostsMap[yearMonth.Month] {
		if IsRelevant(yearMonth, cost.From, cost.To) {

			costDetail := FixedCostDetail{}
			costDetail.Amount = cost.Amount
			costDetail.Name = cost.Name
			costDetail.DisplayType = determineDisplayType(cost.DueMonth)

			fixedCosts = append(fixedCosts, costDetail)
		}
	}

	if costs := specialCostMap[*yearMonth]; costs != nil {
		for _, cost := range costs {
			specialCosts = append(specialCosts, CostDetail{
				Name:   cost.Name,
				Amount: cost.Amount,
			})
		}
	}

	return OverviewDetail{
		FixedCosts:   fixedCosts,
		SpecialCosts: specialCosts,
	}
}

func determineDisplayType(dueMonth []int) string {
	switch len(dueMonth) {
	case 1:
		return "jährlich"
	case 2:
		return "halbjährlich"
	case 4:
		return "vierteljährlich"
	case 12:
		return "monatlich"
	default:
		return "ILLEGAL"
	}
}

func createOverview(financeId int) Overview {
	// TODO retrieve current amount

	currentAmount := 3000
	entries := make([]OverviewEntry, MAX_ENTRIES)

	relevantFixedCostsMap := createRelevantMap(financeId)
	specialCostMap := createSpecialCostMap(financeId)

	tmpAmount := currentAmount
	tmpYearMonth := CurrentYearMonth()

	for i := range entries {
		sumFixedCosts := 0

		for _, fixcost := range relevantFixedCostsMap[tmpYearMonth.Month] {
			if IsRelevant(tmpYearMonth, fixcost.From, fixcost.To) {
				sumFixedCosts += fixcost.Amount
			}
		}

		sumSpecialCosts := 0

		for _, specialcost := range specialCostMap[*tmpYearMonth] {
			sumSpecialCosts += specialcost.Amount
		}

		newTmpAmount := tmpAmount + sumFixedCosts + sumSpecialCosts
		entries[i] = OverviewEntry{
			YearMonth:       *tmpYearMonth,
			CurrentAmount:   newTmpAmount,
			SumFixedCosts:   sumFixedCosts,
			SumSpecialCosts: sumSpecialCosts,
		}
		tmpYearMonth = NextYearMonth(tmpYearMonth)
		tmpAmount = newTmpAmount
	}

	return Overview{
		CurrentAmount: currentAmount,
		Entries:       entries,
	}

}

func createRelevantMap(financeId int) map[int][]FixedCost {
	result := make(map[int][]FixedCost)
	for _, cost := range *LoadFixedCosts(financeId) {
		for _, dueMonth := range cost.DueMonth {
			if result[dueMonth] == nil {
				result[dueMonth] = []FixedCost{cost}
			} else {
				result[dueMonth] =
					append(result[dueMonth], cost)
			}
		}
	}

	return result
}

func createSpecialCostMap(financeId int) map[YearMonth][]SpecialCost {
	result := make(map[YearMonth][]SpecialCost)
	for _, cost := range *LoadSpecialCosts(financeId) {
		if result[*cost.DueDate] == nil {
			result[*cost.DueDate] = []SpecialCost{cost}
		} else {
			result[*cost.DueDate] =
				append(result[*cost.DueDate], cost)
		}
	}

	return result
}
