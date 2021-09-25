package main

import (
	"net/http"

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

func GetOverview(c *gin.Context) {
	c.IndentedJSON(http.StatusOK, createOverview())
}

func createOverview() Overview {
	// TODO retrieve current amount

	currentAmount := 3000
	entries := make([]OverviewEntry, MAX_ENTRIES)

	fixedCosts := LoadFixedCosts()

	relevantFixedCostsMap := make(map[int][]FixedCost)

	for _, cost := range fixedCosts {
		for _, dueMonth := range cost.DueMonth {
			if relevantFixedCostsMap[dueMonth] == nil {
				relevantFixedCostsMap[dueMonth] = []FixedCost{cost}
			} else {
				relevantFixedCostsMap[dueMonth] =
					append(relevantFixedCostsMap[dueMonth], cost)
			}
		}
	}

	specialCosts := LoadSpecialCosts()

	specialCostMap := make(map[YearMonth][]SpecialCost)

	for _, cost := range specialCosts {
		if specialCostMap[cost.DueDate] == nil {
			specialCostMap[cost.DueDate] = []SpecialCost{cost}
		} else {
			specialCostMap[cost.DueDate] =
				append(specialCostMap[cost.DueDate], cost)
		}
	}

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
