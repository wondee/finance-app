<template>
  <b-modal ref="editModal" size="lg" :title="`${title} ${actionLabel}`">
    <b-form @submit="onSubmit" @reset="onReset">
      <b-form-group
        id="input-name-group"
        label="Bezeichnung:"
        label-for="input-name"
        description="Der Name der zu erfassenden Kosten."
      >
        <b-form-input
          id="input-name"
          v-model="form.name"
          type="text"
          required
          placeholder="z.B. Miete"
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-amount-group" label="Betrag:" label-for="input-amount">
        <currency-input id="input-amount" v-model="form.amount" />
      </b-form-group>

      <b-form-group id="input-incoming-group" 
        description="Anhacken, wenn es sich bei dem Betrag um einen eingehenden (z.B. Gehalt) handelt.">
        <b-form-checkbox id="input-incoming" v-modal="incoming">Eingehend</b-form-checkbox>
      </b-form-group>

      <b-button type="submit" variant="primary">Speichern</b-button>
    </b-form>
  </b-modal>
</template>
<script>
import CurrencyInput from './CurrencyInput';


export default {
  props: ["title", "cost"],
  components: {
    CurrencyInput
  },
  data() {
    return {
      form: {
        name: "",
        amount: 0,
        incoming: false
      },
    };
  },
  computed: {
    actionLabel() {
      return !!this.cost ? "ändern" : "hinzufügen";
    }
  },
  methods: {
    showModal() {
      this.$refs["editModal"].show();
    },
    onSubmit() {

    },
    onReset() {

    }
  }
};
</script>