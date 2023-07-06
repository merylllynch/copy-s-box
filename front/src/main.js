import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import VueRouter from 'vue-router'

Vue.config.productionTip = false
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min.js'
import router from './router/index.js'
import VueParticles from 'vue-particles'

Vue.use(VueParticles)
Vue.use(ElementUI)
Vue.use(VueRouter)
new Vue({
  ElementUI,
  router,
  render: h => h(App),
  
  
}).$mount('#app')
