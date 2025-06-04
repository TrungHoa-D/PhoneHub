<script setup>
import { NCheckbox, NSlider, NInputNumber, NPagination, NCheckboxGroup, NSpin } from 'naive-ui';
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

// Refs
const brandsValue = ref([]);
const searchInputRef = ref(null);
const filtersRef = ref(null);
const phonesRef = ref(null);

// Default values
const DEFAULT_PRICE_RANGE = [1000000, 10000000];
const priceRange = ref([...DEFAULT_PRICE_RANGE]);

// Loading state
const isLoading = ref(false);
const isSearching = ref(false);

// Price formatting functions
const formatTooltip = (value) => {
  let number = parseInt(value, 10);
  return new Intl.NumberFormat('vi-VN').format(number) + 'đ';
};

const parseCurrency = (value) => {
  return parseInt(value.replace(/[^\d]/g, ''), 10) || 0;
};

const formatCurrency = (value) => {
  if (value === null) return '';
  return `${value.toLocaleString('VN')} đ`;
};

// Data management
const filteredData = ref([]);
const searchValue = ref('');

// Function to filter out phones with cost = -1
const filterValidPricePhones = (phones) => {
  return phones.filter((phone) => phone.cost !== -1 && phone.cost !== '-1');
};

// Pagination
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

// Check if any filters are active
const hasActiveFilters = computed(() => {
  return (
    brandsValue.value.length > 0 ||
    priceRange.value[0] !== DEFAULT_PRICE_RANGE[0] ||
    priceRange.value[1] !== DEFAULT_PRICE_RANGE[1] ||
    searchValue.value.trim() !== ''
  );
});

// Unified filter application function
const applyAllFilters = async () => {
  if (isLoading.value) return;

  isLoading.value = true;
  currentPage.value = 1;

  try {
    // If no filters are active, get all phones
    if (!hasActiveFilters.value) {
      await phoneStore.getPhone();
      filteredData.value = filterValidPricePhones(phoneStore.allPhones);
      return;
    }

    // Apply search first if exists
    if (searchValue.value.trim()) {
      await phoneStore.getPhonesByName(searchValue.value.trim());
      filteredData.value = filterValidPricePhones(phoneStore.allPhones);
    } else {
      // Start with all phones
      await phoneStore.getPhone();
      filteredData.value = filterValidPricePhones(phoneStore.allPhones);
    }

    // Apply price filter
    if (
      priceRange.value[0] !== DEFAULT_PRICE_RANGE[0] ||
      priceRange.value[1] !== DEFAULT_PRICE_RANGE[1]
    ) {
      await phoneStore.getPhonesByPriceRage(priceRange.value[0], priceRange.value[1]);

      // If we have search results, filter those by price
      if (searchValue.value.trim()) {
        const priceFilteredPhones =
          phoneStore.filterPhones.length > 0 ? phoneStore.filterPhones : phoneStore.allPhones;
        filteredData.value = filterValidPricePhones(priceFilteredPhones);
      } else {
        filteredData.value = filterValidPricePhones(phoneStore.filterPhones);
      }
    }

    // Apply brand filter
    if (brandsValue.value.length > 0) {
      await phoneStore.getFilterPhones(brandsValue.value);
      filteredData.value = filterValidPricePhones(phoneStore.filterPhones);
    }
  } catch (error) {
    console.error('Error applying filters:', error);
    // Fallback to all phones on error
    await phoneStore.getPhone();
    filteredData.value = filterValidPricePhones(phoneStore.allPhones);
  } finally {
    isLoading.value = false;
  }
};

// Search handler - only triggered by button click or Enter key
const searchPhoneHandler = async () => {
  if (!searchValue.value.trim()) {
    // If search is empty, apply other filters
    await applyAllFilters();
    return;
  }

  isSearching.value = true;
  try {
    await applyAllFilters();
  } finally {
    isSearching.value = false;
  }
};

// Watch search value - only clear when empty
watch(searchValue, async (newValue, oldValue) => {
  // Only react to actual changes
  if (newValue === oldValue) return;

  if (newValue === '') {
    // When search is cleared, apply remaining filters immediately
    await applyAllFilters();
  }
  // Note: Don't search automatically while typing, only when button is clicked
});

// Filter handler
const filterPhoneHandler = async () => {
  await applyAllFilters();
};

// Reset function
const resetFilter = async () => {
  if (isLoading.value) return;

  isLoading.value = true;

  try {
    // Reset all filter values
    searchValue.value = '';
    brandsValue.value = [];
    priceRange.value = [...DEFAULT_PRICE_RANGE];
    currentPage.value = 1;

    // Get all phones and filter out invalid prices
    await phoneStore.getPhone();
    filteredData.value = filterValidPricePhones(phoneStore.allPhones);

    // Only navigate if not already on search page
    if (route.path !== '/search' || Object.keys(route.query).length > 0) {
      router.push('/search');
    }
  } catch (error) {
    console.error('Error resetting filters:', error);
  } finally {
    isLoading.value = false;
  }
};

// Initialize component
onMounted(async () => {
  isLoading.value = true;

  try {
    // Handle initial route query
    if (route.query.brand) {
      brandsValue.value = [route.query.brand];
      await phoneStore.getPhonesByBrand(route.query.brand);
    } else {
      await phoneStore.getPhone();
    }

    filteredData.value = filterValidPricePhones(phoneStore.allPhones);

    // GSAP animations
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
  } catch (error) {
    console.error('Error initializing component:', error);
  } finally {
    isLoading.value = false;
  }
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
        :disabled="isLoading"
      />
      <AppButton class="custom-btn" @click="searchPhoneHandler" :disabled="isLoading">
        <n-spin v-if="isSearching" size="small" />
        <span v-else>Tìm kiếm</span>
      </AppButton>
    </div>

    <div class="filter-options" ref="filtersRef">
      <div>
        <p class="label">Hãng điện thoại</p>
        <n-checkbox-group v-model:value="brandsValue" class="brands" :disabled="isLoading">
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
          :disabled="isLoading"
        />
        <label for="">Từ:</label>
        <n-input-number
          v-model:value="priceRange[0]"
          size="small"
          :parse="parseCurrency"
          :format="formatCurrency"
          style="margin-bottom: 16px"
          :disabled="isLoading"
        />
        <label for="">Đến: </label>
        <n-input-number
          v-model:value="priceRange[1]"
          size="small"
          :parse="parseCurrency"
          :format="formatCurrency"
          :disabled="isLoading"
        />
      </div>

      <div class="filters-footer">
        <AppButton class="custom-btn" @click="filterPhoneHandler" :disabled="isLoading">
          <n-spin v-if="isLoading" size="small" />
          <span v-else>Lọc</span>
        </AppButton>
        <AppButton class="custom-btn reset" @click="resetFilter" :disabled="isLoading">
          <img src="../../assets/icons/reset.svg" alt="Reset" />
        </AppButton>
      </div>
    </div>

    <div class="phones" ref="phonesRef">
      <div v-if="isLoading && filteredDataLength === 0" class="loading-container">
        <n-spin size="large" />
        <p>Đang tải...</p>
      </div>

      <template v-else-if="filteredDataLength > 0">
        <AppPhone
          v-for="phone in paginatedData"
          :key="phone.id"
          :phone="phone"
          style="width: 100%"
        />
      </template>

      <div v-else class="not-found">
        <p>Chúng tôi không có điện thoại này!</p>
        <AppButton class="custom-btn" @click="resetFilter" style="margin-top: 16px">
          Xem tất cả sản phẩm
        </AppButton>
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
        :disabled="isLoading"
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
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 8px;

      &:disabled {
        opacity: 0.6;
        cursor: not-allowed;
      }
    }

    input {
      width: 88%;
      padding: 0 16px;
      border: 1px solid;
      border-right: none;

      &:disabled {
        opacity: 0.6;
        cursor: not-allowed;
      }
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
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 8px;

      &:disabled {
        opacity: 0.6;
        cursor: not-allowed;
      }

      &.reset {
        padding: 8px 12px;
        background-color: #999999;

        &:hover:not(:disabled) {
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

    .loading-container {
      grid-column: 1 / -1;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 60px 0;
      gap: 16px;

      p {
        font-size: 18px;
        color: #666;
      }
    }

    .not-found {
      grid-column: 1 / -1;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 60px 0;

      p {
        font-size: 24px;
        font-weight: 500;
        font-style: italic;
        color: #929292;
        margin-bottom: 16px;
        text-align: center;
      }
    }
  }

  .phones-pagination {
    grid-column: 1 / 3;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}

@media (max-width: 1200px) {
  .search {
    .phones {
      grid-template-columns: repeat(3, 1fr);
    }
  }
}

@media (max-width: 900px) {
  .search {
    grid-template-columns: 1fr;
    grid-template-rows: auto auto 1fr auto;

    .search-input {
      grid-column: 1;
    }

    .phones {
      grid-template-columns: repeat(2, 1fr);
    }

    .phones-pagination {
      grid-column: 1;
    }
  }
}

@media (max-width: 600px) {
  .search {
    .phones {
      grid-template-columns: 1fr;
    }

    .filter-options {
      .price {
        width: 100%;
      }
    }
  }
}
</style>
