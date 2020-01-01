<template>
  <v-checkbox
    hint="Anhacken, wenn es sich bei dem Betrag um einen eingehenden (z.B. Gehalt) handelt."
    persistent-hint
    :value="value"
    @change="$emit('change', $event)"
    label="Eingehend"
    color="primary"
  />
</template>
<script>
export default {
  props: ['value']
}
</script>


    