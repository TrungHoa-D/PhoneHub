import { defineStore } from 'pinia';
import { usePhoneStore } from './phoneStore';

const phoneStore = usePhoneStore();

export const usePhoneCompareStore = defineStore('phoneCompare', {
  state: () => ({
    phoneCompareList: []
  }),
  actions: {
    async addPhoneCompare(phone) {
      const foundPhone = await phoneStore.getDetailPhoneById(phone.id);
      return this.phoneCompareList.push(foundPhone);
    },
    removePhoneCompare(name) {
      const index = this.phoneCompareList.findIndex((item) => item.name === name);
      return this.phoneCompareList.splice(index, 1);
    }
  }
});
