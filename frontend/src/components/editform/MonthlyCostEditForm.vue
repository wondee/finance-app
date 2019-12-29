<template>
  <cost-edit-form :title="title('Monatliche Kosten')" ref="editForm" :changed="changed">
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
import { CommonForm, monthlyCostToForm } from "../Utils";
import CostEditForm from "./CostEditForm";
import NameTextField from "./NameTextField";
import FromToDateFields from './FromToDateFields';

export default {
  mixins: [CommonForm(monthlyCostToForm)],
  components: {
    CostEditForm,
    NameTextField,
    CurrencyInput,
    FromToDateFields
  }
};
</script>