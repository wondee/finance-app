package main

type SpecialCost struct {
	ID      int `gorm:primary_key`
	Name    string
	Amount  int
	DueDate *YearMonth
}

func LoadSpecialCosts() *[]SpecialCost {
	var specialCosts []SpecialCost
	DB.Find(&specialCosts)
	return &specialCosts
}

func SaveSpecialCost(cost *SpecialCost) {
	if cost.ID == 0 {
		DB.Create(cost)
	} else {
		DB.Save(cost)
	}
}

func DeleteSpecialCost(id int) {
	DB.Delete(&SpecialCost{}, id)
}
