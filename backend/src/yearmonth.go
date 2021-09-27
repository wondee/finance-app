package main

import (
	"database/sql/driver"
	"errors"
	"strconv"
	"strings"
	"time"
)

type YearMonth struct {
	Year  int `json:"year"`
	Month int `json:"month"`
}

func (this *YearMonth) Scan(value interface{}) error {
	str := value.(string)

	if len(str) == 0 {
		return nil
	}

	elems := strings.Split(str, " ")

	year, err := strconv.Atoi(elems[0])
	if err != nil {
		return err
	}

	month, err := strconv.Atoi(elems[1])
	if err != nil {
		return err
	}

	this.Year = year
	this.Month = month

	return nil
}

func (this *YearMonth) Value() (driver.Value, error) {
	if this == nil {
		return nil, nil
	}

	return strconv.Itoa(this.Year) + " " + strconv.Itoa(this.Month), nil
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
	return AddMonths(yearMonth, 1)
}

func AddMonths(yearMonth *YearMonth, n int) *YearMonth {
	nextMonth := yearMonth.Month + n
	nextYear := yearMonth.Year

	if nextMonth > 12 {
		nextYear = nextYear + (nextMonth / 12)
		nextMonth = nextMonth % 12
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
