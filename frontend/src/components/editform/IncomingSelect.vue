<template>
  <v-checkbox
    :input-value="value"
    @input="$emit('change', $event)"
    hint="Anhacken, wenn es sich bei dem Betrag um einen eingehenden (z.B. Gehalt) handelt."
    persistent-hint
    label="Eingehend"
    color="primary"
  />
</template>
<script>
export default {
  props: ["value"]
};
</script>


    