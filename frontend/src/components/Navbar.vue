<template>
  <b-navbar toggleable="lg" type="dark" variant="dark">
    <b-navbar-brand href="#">Finanz-App</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="#">Übersicht</b-nav-item>
        <b-nav-item href="#">Fixkosten</b-nav-item>
				<b-nav-item href="#">Sonderkosten</b-nav-item>
				
        <b-nav-item-dropdown text="Sonstiges">
          <b-dropdown-item href="#"><font-awesome-icon icon="cog"/> Einstellungen</b-dropdown-item>
					<b-dropdown-divider></b-dropdown-divider>
          <b-dropdown-item href="#">Impressum</b-dropdown-item>
          <b-dropdown-item href="#">Datenschutzerklärung</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-nav-item href="#"><font-awesome-icon icon="door-open"/> Logout</b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>