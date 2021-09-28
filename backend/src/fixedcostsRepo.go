package main

import (
	"database/sql/driver"
	"strconv"
	"strings"
)

type FixedCost struct {
	ID       int `gorm:primary_key`
	Name     string
	Amount   int
	From     *YearMonth
	To       *YearMonth
	DueMonth months `gorm:"type:string"`
}

type months []int

var ALL_MONTHS = []int{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}

func LoadFixedCosts() *[]FixedCost {
	//return costs
	var costs []FixedCost
	DB.Find(&costs)

	return &costs
}

func SaveFixedObject(cost *FixedCost) {

	if cost.ID == 0 {
		DB.Create(cost)
	} else {
		DB.Save(cost)
	}

}

func DeleteFixedCost(id int) {
	DB.Delete(&FixedCost{}, id)
}

func (this *months) Scan(value interface{}) error {
	str := value.(string)

	if len(str) == 0 {
		return nil
	}

	for _, month := range strings.Split(str, " ") {
		value, err := strconv.Atoi(month)
		if err == nil {
			*this = append(*this, value)
		}
	}

	return nil
}

func (this months) Value() (driver.Value, error) {
	if this == nil {
		return nil, nil
	}

	var result string
	for i, month := range this {
		result += strconv.Itoa(month)

		if i != len(this) {
			result += " "
		}
	}

	return result, nil

}
