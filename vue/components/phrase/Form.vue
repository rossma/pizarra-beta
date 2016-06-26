<template>
  <div class="form-group">
    <label for="spanish-txt">Spanish</label>
    <input id="spanish-txt" name="spanish" type="text" class="form-control" v-model="phrase.spanish" placeholder="spanish"/>
  </div>
  <div class="form-group">
    <label for="english-txt">English</label>
    <input id="english-txt" name="english" type="text" class="form-control" v-model="phrase.english" placeholder="english"/>
  </div>
  <div class="form-group">
    <input type="submit" class="btn btn-primary" @click="save()" />
    <input type="submit" value="Delete phrase" class="btn btn-danger" v-if="phrase.hasOwnProperty('id')" @click="deletePhrase(phrase.id)" />
  </div>
</template>

<script>
  
  export default {
    ready() {
      console.log("hello boss:" + this.$route.params.phraseId);
      if (typeof this.$route.params.phraseId !== 'undefined') {
        var m = jsRoutes.controllers.HomeController.edit(this.$route.params.phraseId);
        console.log("hello lady:" + m.url)
        this.$http.get(m.url).then(function(response) {
          this.$set('phrase', response.data)        
        }).catch(function(error) {
          console.log("got err:" + error);
        });
      }  
    }, 
    data () {
      return {
        phrase: { spanish: '', english: '' }
      }  
    },
    methods: {
      save () {
        console.log("in save:" + JSON.stringify(this.phrase));
        
        function m(phrase) {
          if (phrase["id"]) {
            return jsRoutes.controllers.HomeController.editPhrase(phrase.id);
          } else { 
            return jsRoutes.controllers.HomeController.addPhrase();
          }
        }

        //console.log("url:" + m(this.phrase).url);

        this.$http.post(m(this.phrase).url, this.phrase).then(function(response) {
          console.log("Phrase saved!");
          this.$router.go('/home');
        }).catch(function(error) {
          console.log(error);
        });
      }, 
      deletePhrase (id) {
        console.log("in delete:" + id);
        var m = jsRoutes.controllers.HomeController.deletePhrase(id);
        console.log("url:" + m.url);

        this.$http.delete(m.url).then(function(response) {
          console.log("Phrase deleted!");
          this.$router.go('/home');
        }).catch(function(error) {
          console.log("has error" + error);
        });
      } 
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1 {
    color: #42b983;
  }
</style>
