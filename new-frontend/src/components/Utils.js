export const toCurrency = (number) =>
  number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " €";


const monthMap = [
  'Januar',
  'Februar',
  'März',
  'April',
  'Mai',
  'Juni',
  'Juli',
  'August',
  'September',
  'Oktober',
  'November',
  'Dezember',
] 

export const monthToString = ({ month, year } = {month: 0, year: 0}) => 
  (month == 0) ?  '-' : `${monthMap[month - 1]} / ${year}`;


const createMonthStrings = p => 
  [...Array(12 / p).keys()]
    .map(i => [...Array(p).keys()].map(n => n * (12 / p) + i + 1))
    .map(list => list.map(month => monthMap[month - 1]).join(', '));


const quaterlyStrings = createMonthStrings(4);
const healfyearlyStrings = createMonthStrings(2);

export const toQuaterlyDueDate = dueDate => quaterlyStrings[dueDate - 1];
export const toHalfyearlyDueDate = dueDate => healfyearlyStrings[dueDate - 1];
export const toMonth = month => monthMap[month - 1];