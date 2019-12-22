<template>
  <CostTable :cols="cols" :entries="entries" />
</template>

<script>

import CostTable from './CostTable';
import { monthToString, toCurrency } from './Utils';

const defaultCols = [ 
  { name: "name", label: "Bezeichnung" },
  { name: "amount", label: "Betrag", transformer: toCurrency },
  { name: "from", label: "Gültig ab", transformer: monthToString },
  { name: "to", label: "Gültig bis", transformer: monthToString }
]

export default {
  props: ['entries', 'additionalCols'], 
  components: { CostTable },
  computed: {
    cols() {
      if (!this.additionalCols) {
        return defaultCols;
      }
      const cols = [...defaultCols];
      cols.splice(1, 0, ...this.additionalCols);
      return cols;      
    }
  }
}
</script>