import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
  state: () => ({
    token: '',
    username: 'Admin',
    password: 'Abc@12345'
  }),
  actions: {
    async login(body) {
      const { username, password } = body;
      if (username === this.username && password === this.password) {
        this.token = localStorage.setItem('token', 'group-1-ptpmhdv');
        return true;
      } else return false;
    },
    logout() {
      localStorage.removeItem('token');
      this.token = '';
    }
  }
});
