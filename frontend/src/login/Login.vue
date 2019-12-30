<template>
  <layout>
    <v-container fluid>
      <v-row align="center" justify="center">
        <v-col sm="10" md="6">
          <v-card>
            <v-toolbar flat>
              <v-toolbar-title>Login form</v-toolbar-title>
            </v-toolbar>
            <v-form action="/login" method="post" v-model="valid">
              <v-card-text>
                <v-text-field
                  label="Name"
                  name="username"
                  v-model="username"
                  prepend-icon="fa-user"
                  type="text"
                  :rules="requiredRule('Name')"
                />

                <v-text-field
                  id="password"
                  label="Passwort"
                  name="password"
                  v-model="password"
                  prepend-icon="fa-lock"
                  type="password"
                  :rules="requiredRule('Passwort')"
                />
              </v-card-text>
              <v-card-actions>
                <v-spacer />
                <v-btn text type="submit" :disabled="!valid || !username || !password">Login</v-btn>
              </v-card-actions>
            </v-form>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </layout>
</template>

<script>
import Layout from "../Layout";
import VueRouter from "vue-router";

const router = new VueRouter({});

export default {
  router,
  components: {
    Layout
  },
  data() {
    return {
      valid: false,
      username: "",
      password: "",
    };
  },
  methods: {
    requiredRule: (field) => [
        v => !!v || `${field} darf nicht leer sein`],
  }
};
</script>