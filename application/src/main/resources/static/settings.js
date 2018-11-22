
//Vue.config.devtools = true;

function isValid(field) {
  return !field.error && field.text !== "";
}

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");


Vue.http.interceptors.push(function(request) {

  if (request.method == 'POST') {
    request.headers.set(header, token);
  }
  
});

var classMap = {
  'INFO': 'alert-success',
  'WARN': 'alert-warning',
  'ERROR': 'alert-danger'
}

Vue.component('message-panel', {
  template: `
  <div>
    <div v-for="(msg, index) in messages" 
        class="alert" role="alert" 
        v-bind:class="getClass(msg.severity)">
      <span>{{ msg.text }}</span>
      <button type="button" class="close" 
          @click="messages.splice(index, 1)" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
  </div>
  `,
  props: ['messages'],
  methods: {
    getClass(severity) {
      return classMap[severity];
    }
  }
    
});

Vue.component('validating-input', {
  template: `
  <div class="form-group row">
    <label for="password" class="col-sm-4 col-form-label">{{ label }}</label>
    <div class="col-sm-8">
      <input 
          v-bind:type="type" 
          class="form-control"
          :class="{ 'is-invalid': value.error }"
          :value="value.text"
          @input="inputChanged"
       ></input>
       <div class="invalid-feedback">{{ value.error }}</div>

    </div>
  </div>
   `, 
  props: ['label', 'type', 'min', 'max', 'value'],
  
  methods: {
    inputChanged(e) {
      this.value.text = e.target.value; 
      this.validate();
    },
    validate() {
      this.value.error = null;
      
      if (!this.value.text) {
        this.value.error = this.label + " darf nicht leer sein";
      } else if (this.value.text.length < this.min ||  this.value.text.length > this.max) {
        this.value.error = this.label + " muss mindestens " + this.min + 
              " und maximal " + this.max + " Zeichen lang sein";
      }
      
      this.$emit('input', this.value);
    }
  }
  
});

Vue.component('new-password-input',{
  template: `
    <div>
      <validating-input :label="label" type="password" min="8" max="40" v-model="value"></validating-input>
      <div class="form-group row">
      <label for="password" class="col-sm-4 col-form-label">{{ label }} Wiederholen</label>
      <div class="col-sm-8">
        <input 
            type="password" 
            class="form-control"
            :class="{ 'is-invalid': repeat.error }"
            v-model="repeat.text"
         ></input>
         <div class="invalid-feedback">{{ repeat.error }}</div>
  
      </div>
    </div>
    </div>
  `,
  props: ['value', 'label'],
  data() {
    return {
      repeat: {text: '', error: null}
    };
  },
  computed: {
    valid: function() {
      this.repeat.error = null;
      
      if (isValid(this.value)) {
        if (this.value.text !== this.repeat.text) {
          this.repeat.error = 'Die Passwörter stimmen nicht überein';
        } 
        
        return isValid(this.repeat);
      } 
      return false;
    }
  }

});


Vue.component('change-password-form', {
  data() {
    return {
        disabled: false,
        globalMessages: [],
        oldPassword: {text: '', error: null},
        newPassword: {text: '', error: null}
      };
  },
  computed: {
    valid: function() {
      return isValid(this.oldPassword)
           && this.$refs.passwordInput.valid;
    }
  },
  methods:{
    changePasswordSubmit(e) {
      e.preventDefault();
      
      let changePassword = {
          oldPassword: this.oldPassword.text,
          newPassword: this.newPassword.text
      };
      
      this.disabled = true;
      
      this.$http.post('/settings/password', changePassword).then(
          function(response) {
            this.globalMessages = response.data.messages;
            this.disabled = false;
          }, function(response) {
            this.globalMessages = response.data.messages;
            this.disabled = false;
          });
    }
  }
});

Vue.component('add-user-form', {
  data() {
    return {
        disabled: false,  
        globalMessages: [],
        name: {text: '', error: null},
        password: {text: '', error: null}
      };
  },
  computed: {
    valid: function() {
      
      return isValid(this.name)
           && this.$refs.passwordInput.valid;
      
    }
  },
  methods:{
    addUserSubmit(e) {
      
      e.preventDefault();
      
      let newUser = {
          name: this.name.text,
          password: this.password.text
      };
      
      this.disabled = true;
      
      this.$http.post('/settings/user', newUser).then(
          function(response) {
            this.globalMessages = response.data.messages;
            this.disabled = false;
          }, function(response) {
            this.globalMessages = response.data.messages;
            this.disabled = false;
          });
    },
    getClass(severity) {
      return classMap[severity];
    }
  }
});


var app = new Vue( { el: '#settings-app' } );

