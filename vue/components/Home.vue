<template>
  <div>
    <div class="heading">
    <h1>¡Buenos días!</h1>
        <img class="logo" src="../images/whiteboard.png">
    </div>
        
    <div class="alert alert-info">
     <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
     <p>
      This application is used to showcase the Playframework as well as Vue.js
      while learning basic Spanish phrases. This application makes use of the following:
     </p> 
     <ul>
      <li><a href="http://www.playframework.com">Playframework 2.5</a></li>
      <ul>
       <li><a href="https://www.playframework.com/documentation/2.5.x/ScalaJavascriptRouting">Javascript Routing</a></li>
       <li><a href="https://www.playframework.com/documentation/2.5.x/ScalaJsonAutomated">JSON Automated Mapping</a></li>
       <li><a href="https://www.playframework.com/documentation/2.5.x/ScalaForms">Scala Forms</a></li>
      </ul>
      <li><a href="https://vuejs.org/">Vue.js</a></li>
      <ul>
       <li><a href="http://router.vuejs.org/en/index.html">Vue Router</a></li>
       <li><a href="https://github.com/vuejs/vue-resource">Vue Resource</a></li>
      </ul>
      <li><a href="https://nodejs.org/en">Node.js</a></li>
      <li><a href="https://webpack.github.io">Webpack</a></li>
      <li><a href="http://www.bootstrap.com">Bootstrap</a></li>
      <li><a href="http://www.slick.typesafe.com">Slick</a></li>
     </ul>
    </div>
    <h2>{{ phrases.length }} phrases found</h2>
    <div>
     <div class="panel panel-default">
      <div class="panel-heading">Frases</div>
       <table class="phrase-tbl table table-striped table-hover">
        <thead>
         <tr>
          <th>Spanish</th>
          <th>English</th>
         </tr>
        </thead>
        <tbody v-for="p in phrases">
           <tr @click="edit(p.id)">
            <td>{{ p.spanish }}</td>
            <td>{{ p.english }}</td>
           </tr>
        </tbody>
       </table>
     </div>
  </div>  
</template>

<script>
  export default {
    data () {
      return {
        phrases: []
      }
    },
    methods: {
      edit: function (id) {
        console.log("id:" + id);
        this.$router.go({ name: 'edit', params : { phraseId: id }} );
      },
    },
    ready () {
      var m = jsRoutes.controllers.HomeController.phrases();
      this.$http.get(m.url).then((response) => {

          /*
          response.status;
          response.headers();
          response.headers('expires');
          */
          
          this.$set('phrases', response.data)

      }, (response) => {
        console.log("error fetching phrases" + error);
      });
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .heading {
    overflow: auto;
    padding-bottom: 10px;
  }

  .heading h1 {
    float: left;
  }  

  .logo {
    width: 200px;
    height: 136px;
    float: right;
  }

  .phrase-tbl tr {
    cursor: pointer;
  }

</style>
