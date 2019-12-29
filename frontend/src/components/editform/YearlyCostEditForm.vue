<template>
  <cost-edit-form :title="title('Jährliche Kosten')" ref="editForm" :changed="changed">
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
        <v-select :items="items" v-model="form.month" label="Fällig im" />
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-checkbox v-model="form.incoming" label="Eingehend" color="primary" />
      </v-col>
    </v-row>
    <v-row>
      <from-to-date-fields v-model="form.fromTo" />
    </v-row>
  </cost-edit-form>
</template>
<script>
import CurrencyInput from "./CurrencyInput";
import { CommonForm, monthlyCostToForm, toSelectItems, monthMap } from "../Utils";
import CostEditForm from "./CostEditForm";
import NameTextField from "./NameTextField";
import FromToDateFields from "./FromToDateFields";

const costToForm = cost => {
  const form = monthlyCostToForm(cost);
  return !cost
    ? {
        ...form,
        month: null
      }
    : {
        ...form,
        month: cost.dueMonth - 1
      };
};

export default {
  mixins: [CommonForm(costToForm)],
  components: {
    CostEditForm,
    NameTextField,
    CurrencyInput,
    FromToDateFields
  },
  data() {
    return {
      items: toSelectItems(monthMap)
    };
  }
};
</script>