import { defineStore } from 'pinia';
import { api } from '@api/axios';

export const useUserStore = defineStore('user', {
  state: () => ({
    token: '',
    username: '',
    role: ''
  }),
  actions: {
    async login(body) {
      const { username, password } = body;
      try {
        const res = await api.post('/auth/login', { emailOrPhone: username, password });

        const data = res.data.data;
        this.token = data.accessToken;
        localStorage.setItem('token', this.token);

        return data;
      } catch (error) {
        return error;
      }
    },
    async getProfile() {
      try {
        const res = await api.get('/user/current');
        this.username = res.data.data.username;
        this.role = res.data.data.roleName;
        return res.data.data;
      } catch (error) {
        return error;
      }
    },
    logout() {
      localStorage.removeItem('token');
      this.token = '';
    }
  }
});
