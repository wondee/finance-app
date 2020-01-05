<template>
  <v-card>
    <v-simple-table fixed-header :class="{ 'tight-table': $vuetify.breakpoint.xs }">
      <template v-slot:default>
        <thead>
          <tr>
            <th>Monat</th>
            <th>Fixe Kosten</th>
            <th>Sonder Kosten</th>
            <th>Saldo</th>
            <th />
          </tr>
        </thead>
        <tbody>
          <tr :key="index" v-for="(entry, index) in entries">
            <td>{{ entry | displayMonth }}</td>
            <td>{{ entry.sumFixedCosts | currency | responsive }}</td>
            <td>{{ entry.sumSpecialCosts | currency | responsive }}</td>
            <td
              class="amount"
              :class="{ 'negative-amount': entry.currentAmount < 0}"
            >{{ entry.currentAmount | currency | responsive }}</td>
            <td align="right" class="action-cell">
              <overview-details v-if="!entry.empty" :detail="{...entry, index}" />
              <special-cost-form :cost="cost(index, entry.yearMonth)" icon="fa-plus-square"/>
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </v-card>
</template>
<script>
import OverviewDetails from "./OverviewDetails";
import { displayMonth } from "../Utils";
import SpecialCostForm from "../editform/SpecialCostForm";

export default {
  props: ["entries"],
  components: {
    OverviewDetails,
    SpecialCostForm
  },
  data() {
    return {
      month: "No Month"
    };
  },
  filters: {
    displayMonth: ({ yearMonth }) => displayMonth(yearMonth),

    responsive: function(value) {
      if (value.length > 5 && window.innerWidth < 768) {
        var tmp = value.split(" ");
        var rest = tmp[0].substring(0, tmp[0].length - 2);
        return rest + " T" + tmp[1];
      } else {
        return value;
      }
    }
  },
  methods: {
    cost(index, dueYearMonth) {
      return {
        index,
        name: "",
        amount: 0,
        dueYearMonth
      };
    }
  }
};
</script>