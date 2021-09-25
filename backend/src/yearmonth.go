package main

import (
	"errors"
	"time"
)

type YearMonth struct {
	Year  int `json:"year"`
	Month int `json:"month"`
}

func New(year, month int) (*YearMonth, error) {
	if year < 2000 || year > 3000 {
		return nil, errors.New("year must not be less 2000 or greater than 3000")
	}

	if month < 1 || month > 12 {
		return nil, errors.New("month must not be less 1 or greater than 12")
	}

	return &YearMonth{year, month}, nil
}

func CurrentYearMonth() *YearMonth {
	currentTime := time.Now()
	return &YearMonth{currentTime.Year(), int(currentTime.Month())}
}

func NextYearMonth(yearMonth *YearMonth) *YearMonth {
	nextMonth := yearMonth.Month + 1
	nextYear := yearMonth.Year

	if nextMonth > 12 {
		nextMonth = 1
		nextYear = nextYear + 1
	}

	return &YearMonth{Year: nextYear, Month: nextMonth}
}

func compare(this, other *YearMonth) int {
	result := this.Year - other.Year

	if result == 0 {
		result = this.Month - other.Month
	}

	return result
}

func isGreaterThanOrEqual(this, other *YearMonth) bool {
	return compare(this, other) >= 0
}

func isLessThanOrEqual(this, other *YearMonth) bool {
	return compare(this, other) <= 0
}

func IsRelevant(this, from, to *YearMonth) bool {
	if from == nil && to == nil {
		return true
	}
	if from == nil {
		return isLessThanOrEqual(this, to)
	}

	if to == nil {
		return isGreaterThanOrEqual(this, from)
	}

	return isGreaterThanOrEqual(this, from) && isLessThanOrEqual(this, to)
}
