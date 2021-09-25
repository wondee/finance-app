package main

type FixedCost struct {
	ID       int
	Name     string
	Amount   int
	From     *YearMonth
	To       *YearMonth
	DueMonth []int
}

var ALL_MONTHS = []int{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}

var costs = []FixedCost{
	{
		ID:       1,
		Name:     "Gehalt",
		Amount:   1200,
		From:     &YearMonth{2021, 2},
		To:       nil,
		DueMonth: ALL_MONTHS,
	},
	{
		ID:       1,
		Name:     "Miete",
		Amount:   -600,
		From:     &YearMonth{2021, 2},
		To:       nil,
		DueMonth: ALL_MONTHS,
	},
	{
		ID:       2,
		Name:     "Versicherung",
		Amount:   -150,
		From:     nil,
		To:       nil,
		DueMonth: []int{4, 10},
	},
}

func LoadFixedCosts() []FixedCost {
	return costs
}
