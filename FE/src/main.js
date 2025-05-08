import { createApp } from 'vue';
import '@styles/index.scss';
import App from './App.vue';
import { router } from '@router/index.route';
import { createPinia } from 'pinia';

createApp(App).use(createPinia()).use(router).mount('#app');
