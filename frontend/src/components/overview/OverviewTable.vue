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
            <td>{{ entry.displayFixedAmount | responsive }}</td>
            <td>{{ entry.displaySpecialAmount | responsive }}</td>
            <td
              class="amount"
              :class="{ 'negative-amount': entry.currentAmount < 0}"
            >{{ entry.displayCurrentAmount | responsive }}</td>
            <td align="right" class="action-cell">
              <v-btn icon @click="showModal($event, index)" v-if="!entry.empty">
                <v-icon small>fa-file-alt</v-icon>
              </v-btn>
              <v-btn
                icon
                :to="'/specialcosts/add?target=overview&month=' + entry.yearMonth[1] + '&year=' + entry.yearMonth[0]"
              >
                <v-icon small>fa-plus-square</v-icon>
              </v-btn>
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <overview-details :detail="detail" @close="detail = null" />
  </v-card>
</template>
<script>
import OverviewDetails from './OverviewDetails';
import { displayMonth } from '../Utils';

export default {
  props: ["entries"],
  components: {
    OverviewDetails
  },
  data() {
    return {
      month: "No Month",
      detail: null
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
    showModal: async function(event, index) {
      this.detail = { index, month: this.entries[index].displayMonth };
    },
    close() {
      this.detail = null;
    }
  }
};
</script>