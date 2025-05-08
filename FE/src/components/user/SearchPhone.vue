<script setup>
import { NCheckbox, NSlider, NInputNumber, NPagination, NCheckboxGroup } from 'naive-ui';
import { ref, onMounted, computed, watch } from 'vue';
import { gsap } from 'gsap';
import { useRoute, useRouter } from 'vue-router';

import brandList from '@/stores/brandList';
import phoneList from '@/stores/phoneList';

import AppPhone from './AppPhone.vue';
import AppButton from './AppButton.vue';

import { usePhoneStore } from '@/stores/phoneStore';

const route = useRoute();
const router = useRouter();
const phoneStore = usePhoneStore();
const brands = brandList;
// const randomPhoneList = phoneList.slice(0, 8);

const brandsValue = ref([]);
const searchInputRef = ref(null);
const filtersRef = ref(null);
const phonesRef = ref(null);

const priceRange = ref([1000000, 10000000]);
const formatTooltip = (value) => {
  let number = parseInt(value, 10);
  return new Intl.NumberFormat('vi-VN').format(number) + 'đ';
};

const filteredData = ref([]);
// Phân trang
const filteredDataLength = computed(() => filteredData.value.length);
const currentPage = ref(1);
const pageSize = ref(20);
const pageSizes = [
  {
    label: '12 per page',
    value: 12
  },
  {
    label: '20 per page',
    value: 20
  },
  {
    label: '32 per page',
    value: 32
  },
  {
    label: '40 per page',
    value: 40
  }
];
const pageSlot = ref(7);
const pageCount = computed(() => Math.ceil(filteredDataLength.value / pageSize.value));

const paginatedData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;

  return filteredData.value.slice(start, end);
});

const handlePageChange = (page) => {
  currentPage.value = page;
};

const parseCurrency = (value) => {
  return parseInt(value.replace(/[^\d]/g, ''), 10) || 0;
};

const formatCurrency = (value) => {
  if (value === null) return '';
  return `${value.toLocaleString('VN')} đ`;
};

const searchValue = ref('');
const searchPhoneHandler = async () => {
  if (phoneStore.filterPhones.length > 0) {
    phoneStore.getFilterPhonesHandler(searchValue.value);
    filteredData.value = phoneStore.allPhones;
  } else {
    await phoneStore.getPhonesByName(searchValue.value);
    filteredData.value = phoneStore.allPhones;
  }
};

watch(searchValue, async () => {
  if (searchValue.value === '') {
    filteredData.value = phoneStore.filterPhones;
  }
});

const filterPhoneHandler = async () => {
  await phoneStore.getPhonesByPriceRage(priceRange.value[0], priceRange.value[1]);
  if (brandsValue.value.length > 0) {
    await phoneStore.getFilterPhones(brandsValue.value);

    filteredData.value = phoneStore.filterPhones;
    searchValue.value = '';
  } else {
    await phoneStore.getPhone();
    filteredData.value = phoneStore.allPhones;
    searchValue.value = '';
  }
};

const resetFilter = async () => {
  await phoneStore.getPhone();
  filteredData.value = phoneStore.allPhones;
  searchValue.value = '';
  brandsValue.value = [];
  router.push('/search');
};

onMounted(async () => {
  if (!route.query.brand) {
    await phoneStore.getPhone();
  } else {
    await phoneStore.getPhonesByBrand(route.query.brand);
  }
  filteredData.value = phoneStore.allPhones;

  gsap.from(searchInputRef.value, {
    opacity: 0,
    duration: 1.5,
    y: -50,
    ease: 'power2.out'
  });
  gsap.from(filtersRef.value, {
    opacity: 0,
    duration: 1.5,
    x: -400,
    ease: 'power2.out'
  });
  gsap.from(phonesRef.value, {
    opacity: 0,
    duration: 1.5,
    y: 400,
    ease: 'power2.out'
  });
});
</script>

<template>
  <div class="search">
    <div class="search-input" ref="searchInputRef">
      <input
        type="text"
        placeholder="Tìm kiếm tại đây"
        v-model="searchValue"
        @keypress.enter="searchPhoneHandler"
      />
      <AppButton class="custom-btn" @click="searchPhoneHandler">Tìm kiếm</AppButton>
    </div>
    <div class="filter-options" ref="filtersRef">
      <div>
        <p class="label">Hãng điện thoại</p>
        <n-checkbox-group v-model:value="brandsValue" class="brands">
          <n-checkbox
            v-for="(brand, index) in brands"
            :key="index"
            :value="brand.brand"
            :label="brand.brand"
          />
        </n-checkbox-group>
      </div>
      <div class="price">
        <p class="label">Giá</p>
        <n-slider
          v-model:value="priceRange"
          range
          :step="1"
          :max="100000000"
          :format-tooltip="formatTooltip"
          style="margin-bottom: 16px"
        />
        <label for="">Từ:</label>
        <n-input-number
          v-model:value="priceRange[0]"
          size="small"
          :parse="parseCurrency"
          :format="formatCurrency"
          style="margin-bottom: 16px"
        />
        <label for="">Đến: </label>
        <n-input-number
          v-model:value="priceRange[1]"
          size="small"
          :parse="parseCurrency"
          :format="formatCurrency"
        />
      </div>
      <div class="filters-footer">
        <AppButton class="custom-btn" @click="filterPhoneHandler">Lọc</AppButton>
        <AppButton class="custom-btn reset" @click="resetFilter"
          ><img src="../../assets/icons/reset.svg" alt=""
        /></AppButton>
      </div>
    </div>
    <div class="phones" ref="phonesRef">
      <AppPhone v-for="phone in paginatedData" :key="phone.id" :phone="phone" style="width: 100%" />
      <div v-if="filteredDataLength === 0" class="not-found">
        <p>Chúng tôi không có điện thoại này!</p>
      </div>
    </div>
    <div v-if="filteredDataLength > 0" class="phones-pagination">
      <n-pagination
        v-model:page="currentPage"
        :page-count="pageCount"
        :page-slot="pageSlot"
        v-model:page-size="pageSize"
        :page-sizes="pageSizes"
        show-quick-jumper
        show-size-picker
        @update:page="handlePageChange"
      />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.search {
  display: grid;
  grid-template-columns: 300px 1fr;
  grid-template-rows: 50px 1fr 50px;
  grid-row-gap: 36px;
  color: $text-color;
  .search-input {
    grid-column: span 2;
    display: flex;
    .custom-btn {
      width: max-content;
      border-radius: 0;
      flex: 1;
    }
    input {
      width: 88%;
      padding: 0 16px;
      border: 1px solid;
      border-right: none;
    }
  }
  .filter-options {
    .label {
      font-size: 20px;
      font-weight: 600;
    }
    .brands {
      display: flex;
      flex-direction: column;
      gap: 8px;
      font-size: 18px;
      margin-top: 8px;
    }
    .filters-footer {
      display: flex;
      gap: 8px;
    }
    .custom-btn {
      padding: 8px 32px;
      margin-top: 16px;
      font-size: 16px;
      &.reset {
        padding: 8px 12px;
        background-color: #999999;
        &:hover {
          background-color: #c5c5c5;
        }
      }
    }
    .price {
      margin-top: 16px;
      width: 70%;
      label {
        font-weight: 600;
      }
    }
  }
  .phones {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-gap: 36px;
    .phone-wrap {
      width: 200px;
    }
    .not-found {
      font-size: 24px;
      font-weight: 500;
      font-style: italic;
      color: #929292;
      position: absolute;
      top: 50%;
      left: 25%;
    }
  }
  .phones-pagination {
    grid-column: 1 / 3;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>
