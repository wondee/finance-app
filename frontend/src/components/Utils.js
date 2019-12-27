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

export const monthStringToString = (str) => {
  if (!str) return null;
  const elems = str.split('-');
  return monthToString({ month: elems[1], year: elems[0] })
}

export const monthToString = ({ month, year } = { month: 0, year: 0 }) =>
  (month == 0) ? '-' : `${monthMap[month - 1]} / ${year}`;


const createMonthStrings = p =>
  [...Array(12 / p).keys()]
    .map(i => [...Array(p).keys()].map(n => n * (12 / p) + i + 1))
    .map(list => list.map(month => monthMap[month - 1]).join(', '));


const quaterlyStrings = createMonthStrings(4);
const healfyearlyStrings = createMonthStrings(2);

export const toQuaterlyDueDate = dueDate => quaterlyStrings[dueDate - 1];
export const toHalfyearlyDueDate = dueDate => healfyearlyStrings[dueDate - 1];
export const toMonth = month => monthMap[month - 1];


// works only for "flat" objects (no arrays or objects as properties)
export const equals = (o1, o2) => {
  const keys1 = Object.keys(o1)
  const diff = Object.keys(o2).filter(key => !keys1.includes(key));
  return diff.length === 0 && !keys1.some(key => o1[key] !== o2[key]);
}