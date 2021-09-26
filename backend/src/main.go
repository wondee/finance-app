package main

import (
	"github.com/gin-gonic/gin"
)

func main() {
	router := gin.Default()

	router.GET("/api/overview/all", GetOverview)
	router.GET("/api/overview/detail", GetOverviewDetail)
	router.GET("/api/costs", GetFixedCosts)

	router.Run("localhost:8082")

}
