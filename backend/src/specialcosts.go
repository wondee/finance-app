package main

import (
	"net/http"
	"strconv"

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

func SaveSpecialCosts(c *gin.Context) {
	var cost JsonSpecialCost
	err := c.ShouldBindJSON(&cost)

	if err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	dbObject := SpecialCost{
		ID:      cost.ID,
		Name:    cost.Name,
		Amount:  cost.Amount,
		DueDate: cost.DueDate,
	}

	SaveSpecialCost(&dbObject)
}

func DeleteSpecialCosts(c *gin.Context) {
	param := c.Param("id")
	id, err := strconv.Atoi(param)

	if err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	DeleteSpecialCost(id)
}

func createSpecialCosts() (result []JsonSpecialCost) {
	result = make([]JsonSpecialCost, 0)

	specialCosts := LoadSpecialCosts()

	for _, cost := range *specialCosts {
		result = append(result, JsonSpecialCost{
			ID:      cost.ID,
			Name:    cost.Name,
			Amount:  cost.Amount,
			DueDate: cost.DueDate,
		})
	}

	return
}
