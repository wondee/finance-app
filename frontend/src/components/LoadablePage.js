
export default {
  data() {
    return {
      loaded: false
    }
  },
  methods: {
    fetchData: async function(url) {
      const response = await fetch(url);
      const result = await response.json();
      this.loaded = true;
      return result;
    }
  }

}