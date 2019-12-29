export const toCurrency = (number) =>
  number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " €";


export const monthMap = [
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


const delimiter = (list) => list.length > 2 ? ', ' : ' und ';

const createMonthStrings = p =>
  [...Array(12 / p).keys()]
    .map(i => [...Array(p).keys()].map(n => n * (12 / p) + i + 1))
    .map(list => list.map(month => monthMap[month - 1]).join(delimiter(list)));


export const quaterlyStrings = createMonthStrings(4);
export const healfyearlyStrings = createMonthStrings(2);

export const toQuaterlyDueDate = dueDate => quaterlyStrings[dueDate - 1];
export const toHalfyearlyDueDate = dueDate => healfyearlyStrings[dueDate - 1];
export const toMonth = month => monthMap[month - 1];

export const toSelectItems = (list) => list.map((text, value) => ({ text, value }))

// works only for "flat" objects (no arrays or objects as properties)
export const equals = (o1, o2) => {
  if (o1 === o2) return true;
  if (o1 && !o2 || !o1 && o2) return false;
  const keys1 = Object.keys(o1)
  const diff = Object.keys(o2).filter(key => !keys1.includes(key));
  return diff.length === 0 && !keys1.some(key => o1[key] !== o2[key]);
}

export const monthlyCostToForm = cost =>
  cost
    ? {
      name: cost.name,
      amount: Math.abs(cost.amount),
      incoming: cost.amount > 0,
      fromTo: {
        from: cost.from,
        to: cost.to
      }
    }
    : {
      name: "",
      amount: 0,
      incoming: false,
      fromTo: {
        from: null,
        to: null
      }
    };



export const CommonForm = (costToForm) => ({

  data() {
    return {
      form: {},
      cost: null
    }
  },

  computed: {
    changed() {
      return !equals(this.form, this.costToForm(this.cost));
    }
  },

  methods: {
    costToForm,
    openEdit(cost) {
      this.cost = cost;
      this.form = this.costToForm(cost)
      this.$refs["editForm"].openEdit();
    },
    title(name) {
      return `${name} ${this.cost ? "ändern" : "hinzufügen"}`
    }
  }
});