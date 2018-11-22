function toCurrency(string) {
  return string.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " €";
}
