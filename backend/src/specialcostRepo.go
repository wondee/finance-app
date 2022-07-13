package main

type SpecialCost struct {
	ID        int `gorm:primary_key`
	Name      string
	Amount    int
	DueDate   *YearMonth
	FinanceID int
}

func LoadSpecialCosts(financeId int) *[]SpecialCost {
	var specialCosts []SpecialCost
	DB.Where("finance_id = ?", financeId).Find(&specialCosts)
	return &specialCosts
}

func SaveSpecialCost(cost *SpecialCost) {
	if cost.ID == 0 {
		DB.Create(cost)
	} else {
		checkPermission(cost.ID, cost.FinanceID)
		DB.Save(cost)
	}
}

func DeleteSpecialCost(id int, financeId int) {
	checkPermission(id, financeId)
	DB.Delete(&SpecialCost{}, id)
}

func checkPermission(id int, financeId int) {
	var cost SpecialCost

	DB.Where("id = ?", id).First(&cost)

	if cost.FinanceID != financeId {
		panic("access to costs not in your finance not allowed")
	}
}
