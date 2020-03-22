<template>
  <cost-edit-form
    ref="editform"
    :title="title('Sonderkosten Kosten')"
    :successMsg="successMsg('Sonderkosten Kosten')"
    :changed="changed"
    :btn-text="btnText"
    :icon="icon"
    :name="form.name"
    @save="saveCost"
  >
    <v-row>
      <v-col>
        <name-text-field v-model="form.name" />
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <currency-input label="Betrag" v-model="form.amount" />
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <incoming-select v-model="form.incoming" />
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <month-date-picker v-model="form.dueYearMonth" label="Fällig am" />
      </v-col>
    </v-row>
  </cost-edit-form>
</template>
<script>
import CostEditForm from "./CostEditForm";
import NameTextField from "./NameTextField";
import CurrencyInput from "./CurrencyInput";
import IncomingSelect from "./IncomingSelect";
import MonthDatePicker from "./MonthDatePicker";
import { monthlyCostToForm, CommonForm, baseFormToCost } from "../Utils";

const costToForm = cost => {
  const form = monthlyCostToForm(cost);
  return !cost
    ? {
        ...form,
        dueYearMonth: null
      }
    : {
        ...form,
        dueYearMonth: cost.dueYearMonth
      };
};

const formToCost = form => ({
  ...baseFormToCost(form),
  dueYearMonth: form.dueYearMonth
})


export default {
  mixins: [CommonForm(costToForm, formToCost, '/api/specialcosts')],
  components: {
    CostEditForm,
    NameTextField,
    CurrencyInput,
    IncomingSelect,
    MonthDatePicker
  },
  props: ["btnText", "icon"]
};
</script>