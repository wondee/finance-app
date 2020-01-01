<template>
  <v-container>
    <v-row>
      <v-col>
        <v-skeleton-loader
          type="table-tbody"
          :loading="!loaded"
          transition="scale-transition"
          class="mx-auto"
        >
          <v-card>
            <v-card-text>
              <fixed-cost-table
                :entries="entries"
                :cols="cols"
                @edit-clicked="openEdit($event)"
              />
            </v-card-text>
            <v-card-actions>
              <v-btn small text @click="openEdit()">Neue Sonderkosten Hinzufügen</v-btn>
              <cost-edit-form :name="title('Sonderkosten Kosten')" ref="editForm" :changed="changed">
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
            </v-card-actions>
          </v-card>
        </v-skeleton-loader>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import LoadablePage from "./LoadablePage";
import FixedCostTable from "./FixedCostTable";

import {
  CommonForm,
  toCurrency,
  monthlyCostToForm,
  monthToString,
  toClientDate
} from "./Utils";

import CurrencyInput from "./editform/CurrencyInput";
import CostEditForm from "./editform/CostEditForm";
import NameTextField from "./editform/NameTextField";
import MonthDatePicker from './editform/MonthDatePicker';
import IncomingSelect from './editform/IncomingSelect';

const cols = [
  { name: "name", label: "Bezeichnung" },
  { name: "amount", label: "Betrag", transformer: toCurrency },
  { name: "dueDate", label: "Fällig am", transformer: monthToString }
];

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
  mixins: [LoadablePage, CommonForm(costToForm)],
  components: {
    FixedCostTable,
    CostEditForm,
    NameTextField,
    CurrencyInput,
    MonthDatePicker,
    IncomingSelect
  },
  data() {
    return {
      entries: [],
      cols
    };
  },
  created: async function() {
    const data = await this.fetchData("/api/specialcosts");
    this.entries = data;
  }
};
</script>