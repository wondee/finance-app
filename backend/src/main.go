package main

import (
	"fmt"

	"github.com/gin-gonic/gin"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

var DB *gorm.DB

const MOCK_USER = "testee@gmail.com"

func UserMiddleware(c *gin.Context) {
	userId := c.Request.Header.Get("X-MS-CLIENT-PRINCIPAL-ID")

	if gin.Mode() == "debug" && userId == "" {
		userId = MOCK_USER
	}

	if userId == "" {
		c.Writer.WriteHeader(401)
		c.Abort()
		return
	}

	fmt.Println("User found ", userId)

	c.Set("currentUser", userId)

}

func ConnectDataBase() {
	dsn := "host=localhost user=postgres password=admin dbname=postgres port=5432 sslmode=disable"

	database, err := gorm.Open(postgres.Open(dsn), &gorm.Config{
		Logger: logger.Default.LogMode(logger.Info),
	})

	if err != nil {
		panic("Failed to connect to database!")
	}

	err = database.AutoMigrate(
		&FixedCost{},
		&SpecialCost{},
		&User{},
		&Finance{},
	)

	if err != nil {
		panic(err)
	}

	DB = database
}

func main() {

	router := gin.Default()

	router.Use(UserMiddleware)

	ConnectDataBase()

	router.GET("/api/user", GetUser)
	router.PUT("/api/user", PutUser)

	router.GET("/api/overview/all", GetOverview)
	router.GET("/api/overview/detail", GetOverviewDetail)

	router.GET("/api/costs", GetFixedCosts)
	router.DELETE("/api/costs/:id", DeleteFixedCosts)
	router.POST("/api/costs/monthly", SaveMonthlyFixedCosts)
	router.POST("/api/costs/halfyearly", SaveHalfYearlyFixedCosts)
	router.POST("/api/costs/yearly", SaveYearlyFixedCosts)
	router.POST("/api/costs/quaterly", SaveQuaterlyFixedCosts)

	router.GET("/api/specialcosts", GetSpecialCosts)
	router.POST("/api/specialcosts", SaveSpecialCosts)
	router.DELETE("/api/specialcosts/:id", DeleteSpecialCosts)

	router.Run("localhost:8082")

}
