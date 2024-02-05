import './assets/main.css';

import {createApp} from 'vue';
import * as VueRouter from 'vue-router';
import App from './App.vue';

const router = VueRouter.createRouter({
  history: VueRouter.createWebHistory(),
  routes: [{
    path: '/',
    component: () => import('./components/Main.vue')
  }]
});

const app = createApp(App);
app.use(router);

app.mount('#app');
