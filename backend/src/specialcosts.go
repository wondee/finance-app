package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

type JsonSpecialCost struct {
	ID      int        `json:"id"`
	Name    string     `json:"name"`
	Amount  int        `json:"amount"`
	DueDate *YearMonth `json:"dueDate"`
}

func GetSpecialCosts(c *gin.Context) {
	c.IndentedJSON(http.StatusOK, createSpecialCosts())
}

func createSpecialCosts() (result []JsonSpecialCost) {
	specialCosts := LoadSpecialCosts()

	for _, cost := range specialCosts {
		result = append(result, JsonSpecialCost{
			ID:      cost.ID,
			Name:    cost.Name,
			Amount:  cost.Amount,
			DueDate: &cost.DueDate,
		})
	}

	return
}
