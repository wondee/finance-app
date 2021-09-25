package main

type SpecialCost struct {
	ID      int
	Name    string
	Amount  int
	DueDate YearMonth
}

var specialCosts = []SpecialCost{
	{
		ID:      1,
		Name:    "Steuer",
		Amount:  100,
		DueDate: YearMonth{2022, 2},
	},
	{
		ID:      2,
		Name:    "Urlaub",
		Amount:  -2500,
		DueDate: YearMonth{2021, 12},
	},
	{
		ID:      3,
		Name:    "Waschmaschine",
		Amount:  -300,
		DueDate: YearMonth{2022, 2},
	},
}

func LoadSpecialCosts() []SpecialCost {
	return specialCosts
}
