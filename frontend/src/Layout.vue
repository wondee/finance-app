<template>
  <v-app id="inspire">
    <v-navigation-drawer v-model="drawer" app clipped>
      <v-list dense>
        <v-list-item link to="/">
          <v-list-item-action>
            <v-icon>fa-chart-line</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Überblick</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link to="/fixedcosts">
          <v-list-item-action>
            <v-icon>fa-money-check-alt</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Fixkosten</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link to="/specialcosts">
          <v-list-item-action>
            <v-icon>fa-money-bill-wave</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Sonderkosten</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-group value>
          <template v-slot:activator>
            <v-list-item-title>Sonstiges</v-list-item-title>
          </template>
          <v-list-item link>
            <v-list-item-content>
              <v-list-item-title>Einstellungen</v-list-item-title>
            </v-list-item-content>
            <v-list-item-action>
              <v-icon>fa-cog</v-icon>
            </v-list-item-action>
          </v-list-item>
          <v-divider />
          <v-list-item link>
            <v-list-item-content>
              <v-list-item-title>Impressum</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item link>
            <v-list-item-content>
              <v-list-item-title>Datenschutzerklärung</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-group>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app clipped-left>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-toolbar-title>Finanz-App</v-toolbar-title>
    </v-app-bar>

    <v-content>
      <slot />
    </v-content>

    <v-footer app>
      <span>
        &copy; 2019
        <a href="https://wondee.info">wondee.info</a>
      </span>
    </v-footer>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    drawer: null
  }),
  created() {
    this.$vuetify.theme.dark = true;
  }
}
</script>