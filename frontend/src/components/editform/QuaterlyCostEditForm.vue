<template>
  <cost-edit-form
    :successMsg="successMsg('Vierteljährliche Kosten')"
    :tiele="title('Vierteljährliche Kosten')"
    :changed="changed"
    :btn-text="btnText"
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
        <v-select :items="items" v-model="form.dueMonth" label="Fällig in" />
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <incoming-select v-model="form.incoming" />
      </v-col>
    </v-row>
    <v-row>
      <from-to-date-fields v-model="form.fromTo" />
    </v-row>
  </cost-edit-form>
</template>
<script>
import CurrencyInput from "./CurrencyInput";
import {
  CommonForm,
  monthlyCostToForm,
  quaterlyStrings,
  toSelectItems
} from "../Utils";
import CostEditForm from "./CostEditForm";
import NameTextField from "./NameTextField";
import FromToDateFields from "./FromToDateFields";
import IncomingSelect from "./IncomingSelect";

const costToForm = cost => {
  const form = monthlyCostToForm(cost);
  return !cost
    ? {
        ...form,
        dueMonth: null
      }
    : {
        ...form,
        dueMonth: cost.dueMonth - 1
      };
};

export default {
  mixins: [CommonForm(costToForm)],
  components: {
    CostEditForm,
    NameTextField,
    CurrencyInput,
    FromToDateFields,
    IncomingSelect
  },
  props: ["btnText"],
  data() {
    return {
      items: toSelectItems(quaterlyStrings)
    };
  }
};
</script>