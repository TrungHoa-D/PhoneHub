import { defineStore } from 'pinia';
import phoneList from './phoneList';
import { api } from '@api/axios';

export const usePhoneStore = defineStore('phone', {
  state: () => ({
    pagination: {},
    allPhones: [],
    allBrands: [],
    filterPhones: [],
    phonesByBrand: [],
    phonesByPriceRange: [],
    phonesByYear: [],
    detailPhone: {}
  }),
  actions: {
    async getPhone() {
      try {
        const response = await api.get('/phone');

        this.allPhones = response.data.data;
        this.pagination = response.data.data.meta;
      } catch (error) {
        return error;
      }
    },
    getFilterPhonesHandler(phoneName) {
      const name = phoneName.toLocaleLowerCase();
      this.allPhones = [];
      this.filterPhones.forEach((phone) => {
        if (phone.name.toLocaleLowerCase().includes(name)) {
          this.allPhones.push(phone);
        }
      });
    },
    async getFilterPhones(brands) {
      const brandsStr = brands.join(' ').toLocaleLowerCase();
      this.filterPhones = [];
      this.allPhones.forEach((phone) => {
        if (brandsStr.includes(phone.brand.toLocaleLowerCase())) {
          this.filterPhones.push(phone);
        }
      });
    },
    async getPhonesByName(phoneName) {
      try {
        const response = await api.get('/phone/name', { params: { name: phoneName } });

        this.allPhones = response.data.data;
      } catch (error) {
        return error;
      }
    },
    async getPhonesByBrand(phoneBrand) {
      try {
        const response = await api.get('/phone/brand', { params: { brand: phoneBrand } });

        this.allPhones = response.data.data;
      } catch (error) {
        return error;
      }
    },
    async getPhonesByPriceRage(lowestPrice, highestPrice) {
      try {
        const response = await api.get('/phone/priceRange', {
          params: { lowestPrice: lowestPrice, highestPrice: highestPrice }
        });

        this.allPhones = response.data.data;
      } catch (error) {
        return error;
      }
    },
    async getDetailPhone(phoneName) {
      try {
        const response = await api.get('/phone/name', { params: { name: phoneName } });

        this.detailPhone = response.data.data[0];
      } catch (error) {
        return error;
      }
      return this.detailPhone;
    },
    async getDetailPhoneById(phoneId) {
      try {
        const response = await api.get('/phone/id', { params: { id: phoneId } });

        this.detailPhone = response.data.data;
      } catch (error) {
        return error;
      }
      return this.detailPhone;
    },
    async getAllBrand() {
      this.allBrands = [];
      this.allPhones.forEach((phone) => {
        if (phone.brand === '') return;
        const newBrand = {};
        newBrand.label = phone.brand;
        newBrand.value = phone.brand;

        if (this.allBrands.length === 0) this.allBrands.push(newBrand);
        else {
          const hasBrand = this.allBrands.some((brand) => brand.value === newBrand.value);
          if (!hasBrand) this.allBrands.push(newBrand);
        }
      });
    },
    async getTotalPhoneByBrand() {
      this.phonesByBrand = [];

      for (const brand of this.allBrands) {
        const response = await api.get('/phone/brand', { params: { brand: brand.value } });

        const total = {
          brand: brand.value,
          count: response.data.data.length
        };

        this.phonesByBrand.push(total);
      }
    },
    async getTotalPhoneByPriceRange() {
      this.phonesByPriceRange = [];

      const formatCurrency = (value) => {
        if (value === null) return '';
        return `${value.toLocaleString('VN')}đ`;
      };

      const priceRange = [
        {
          lowestPrice: -1,
          highestPrice: 0
        },
        {
          lowestPrice: 1000000,
          highestPrice: 5000000
        },
        {
          lowestPrice: 5000000,
          highestPrice: 15000000
        },
        {
          lowestPrice: 15000000,
          highestPrice: 30000000
        },
        {
          lowestPrice: 30000000,
          highestPrice: 150000000
        }
      ];

      for (const range of priceRange) {
        const response = await api.get('/phone/priceRange', {
          params: { lowestPrice: range.lowestPrice, highestPrice: range.highestPrice }
        });

        const total = {
          range: `${range.lowestPrice !== -1 ? formatCurrency(range.lowestPrice) + '-' + formatCurrency(range.highestPrice) : 'Hết hàng'}`,
          count: response.data.data.length
        };

        this.phonesByPriceRange.push(total);
      }
    },
    async getTotalPhoneByYear() {
      this.phonesByYear = [];

      const years = [
        { year: '2018' },
        { year: '2019' },
        { year: '2020' },
        { year: '2021' },
        { year: '2022' },
        { year: '2023' },
        { year: '2024' }
      ];

      years.forEach((y) => {
        let cnt = 0;
        this.allPhones.forEach((phone) => {
          if (
            phone.releaseDate !== null &&
            phone.releaseDate.split('/').join(' ').includes(y.year)
          ) {
            cnt++;
          }
        });
        const total = {
          year: y.year,
          count: cnt
        };
        this.phonesByYear.push(total);
      });
    },
    async updatePhone(editedPhone) {
      try {
        const response = await api.put('/admin/phone', editedPhone);

        return response;
      } catch (error) {
        return error;
      }
    },
    async addNewPhone(newPhone) {
      try {
        const response = await api.post('/admin/phone', newPhone);

        return response;
      } catch (error) {
        return error;
      }
    },
    async deletePhone(id) {
      const response = await api.delete('/admin/phone', { params: { phoneId: id } });
      return response;
    }
  }
});
