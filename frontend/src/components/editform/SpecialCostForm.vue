<template>
  <cost-edit-form
    :name="title('Sonderkosten Kosten')"
    :changed="changed"
    :btn-text="btnText"
    :icon="icon"
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
        <month-date-picker v-model="form.dueDate" label="Fällig am" />
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
import { monthlyCostToForm, toClientDate, CommonForm } from "../Utils";

const costToForm = cost => {
  const form = monthlyCostToForm(cost);

  return !cost
    ? {
        ...form,
        dueDate: null
      }
    : {
        ...form,
        dueDate: toClientDate(cost.dueDate)
      };
};

export default {
  mixins: [CommonForm(costToForm)],
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