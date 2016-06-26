import 'bootstrap/dist/css/bootstrap.css';
import Vue from 'vue'
import App from './App.vue'
import Home from './components/Home.vue'
import Add from './components/phrase/Add.vue'
import Edit from './components/phrase/Edit.vue'

import VueRouter from 'vue-router'
import VueResource from 'vue-resource'

Vue.use(VueResource)
Vue.use(VueRouter)

/* eslint-disable no-new */
/*new Vue({
  el: 'body',
  components: { App }
})*/

const router = new VueRouter()

// Pointing routes to the components they should use
router.map({
  '/': {
    name: 'home',
    component: Home
  },
  '/add': {
    name: 'add',
    component: Add
  },
  '/edit/:phraseId': {
    name: 'edit',
    component: Edit
  }
})

// Any invalid route will redirect to home
router.redirect({
  '*': '/'
})

router.start(App, '#app')