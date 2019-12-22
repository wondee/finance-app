<template>
  <div>
    <table class="table table-striped" v-if="!empty">
      <thead>
        <tr>
          <th 
            :key="col.name" 
            v-for="col in cols" 
            :class="col.styleClass"
          >
            {{ col.label }}
          </th>
          <th></th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr :key="index" v-for="(entry, index) in entries">
          <td 
            :key="col.name" 
            v-for="col in cols" 
            :class="col.styleClass"
          >
            {{ transform(col.transformer, entry[col.name]) }}
          </td>
          <td>
            <router-link
              :to="'/fixedcosts/edit?target=overview&id=' + entry.id + '&type=special'"
              class="icon-link"
            >
              <font-awesome-icon icon="edit" />
            </router-link>
          </td>
          <td>
            <router-link
              :to="'/specialcosts/delete?target=overview&id=' + entry.id"
              class="icon-link"
            >
              <font-awesome-icon icon="trash-alt" />
            </router-link>
          </td>
        </tr>
      </tbody>
    </table>
    <p v-if="empty">Keine Einträge bisher</p>
  </div>
</template>

<script>
export default {
  props: ["cols", "entries"],
  methods: {
    transform: (f, v) => !!f ? f(v) : v
  },
  computed: {
    empty() {
      return !this.entries || this.entries.length == 0;
    } 
  }

};
</script>