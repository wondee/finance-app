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

export const displayMonth = (yearMonth, responsive = true, empty = '-') => {
  if (!yearMonth) return empty;

  const {year, month} = yearMonth;

  const displayMonth = responsive && window.innerWidth < 768 ?
    month : monthMap[month - 1];

  return `${displayMonth} / ${year}`;
} 

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
      id: cost.id,
      name: cost.name,
      amount: Math.abs(cost.amount),
      incoming: cost.amount > 0,
      fromTo: {
        from: cost.from,
        to: cost.to
      }
    }
    : {
      id: null,
      name: "",
      amount: 0,
      incoming: false,
      fromTo: {
        from: null,
        to: null
      }
    };

export const baseFormToCost = form => ({
  id: form.id,
  name: form.name,
  amount: form.amount * (form.incoming ? 1 : -1),
})



export const CommonForm = (costToForm, formToCost, endpoint) => ({

  props: ['cost', 'add'],
  data() {
    return {
      form: costToForm(this.cost)
    }
  },

  computed: {
    changed() {
      return !equals(this.form, this.costToForm(this.cost));
    }
  },

  methods: {
    costToForm,
    formToCost,
    title(name) {
      return `${name} ${this.cost && this.cost.name ? "ändern" : "hinzufügen"}`
    },
    successMsg(name) {
      return costName => `${name} '${costName}' erfolgreich ${this.cost && this.cost.name ? "geändert" : "hinzugefügt"}`
    },
    saveCost: async function () {
      
      const cost = formToCost(this.form)
      
      await fetch(endpoint, {
        method: 'post', body: JSON.stringify(cost), headers: {
          'Content-Type': 'application/json'
        }
      })

      this.$refs.editform.success();
    }

  }
});