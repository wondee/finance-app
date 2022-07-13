package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

type UserJson struct {
	ID        string `json:"id"`
	FinanceID int    `json:"financeId"`
}

func PutUser(c *gin.Context) {
	user := LoadCurrentUser(c)

	if user != nil {
		c.AbortWithStatus(http.StatusBadRequest)
		return
	}

	userId := c.GetString("currentUser")

	financeId := CreateUser(userId)

	c.IndentedJSON(http.StatusCreated, UserJson{
		ID:        userId,
		FinanceID: financeId,
	})
}

func GetUser(c *gin.Context) {
	user := LoadCurrentUser(c)

	if user != nil {
		c.IndentedJSON(http.StatusOK, createUser(user))
	} else {
		c.AbortWithStatus(http.StatusNotFound)
	}

}

func createUser(user *User) *UserJson {
	return &UserJson{
		user.ID,
		user.FinanceID,
	}
}
