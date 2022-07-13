package main

import (
	"github.com/gin-gonic/gin"
)

type User struct {
	ID        string `gorm:primary_key`
	FinanceID int
	Finance   *Finance
}

type Finance struct {
	ID int `gorm:primary_key`

	//SpecialCosts *[]SpecialCost
	//FixedCost    *[]FixedCost
}

func LoadCurrentFinanceId(c *gin.Context) int {
	return LoadCurrentUser(c).FinanceID
}

func LoadCurrentUser(c *gin.Context) *User {
	userId := c.GetString("currentUser")

	var user User
	err := DB.Where("id = ?", userId).First(&user).Error

	if err != nil {
		return nil
	}

	return &user
}

func CreateUser(userId string) int {
	finance := &Finance{}

	DB.Create(finance)

	user := User{
		ID:        userId,
		FinanceID: finance.ID,
	}

	DB.Create(user)

	return user.FinanceID
}
