<script setup>
import { NTag } from 'naive-ui';
import { usePhoneStore } from '@/stores/phoneStore';
import { onMounted, ref } from 'vue';

import TotalPhoneByBrand from '@/components/admin/TotalPhoneByBrand.vue';
import TotalPhoneByPriceRange from '@/components/admin/TotalPhoneByPriceRange.vue';
import ToTalPhoneByYear from '@/components/admin/ToTalPhoneByYear.vue';

const phoneStore = usePhoneStore();
const totalPhones = ref(0);
const totalBrands = ref(0);
const phoneBrands = ref([]);
const phonePriceRange = ref([]);
const phoneYear = ref([]);

onMounted(async () => {
  await phoneStore.getPhone();
  totalPhones.value = phoneStore.allPhones.length;

  await phoneStore.getAllBrand();
  totalBrands.value = phoneStore.allBrands.length;

  await phoneStore.getTotalPhoneByBrand();
  phoneBrands.value = phoneStore.phonesByBrand;

  await phoneStore.getTotalPhoneByPriceRange();
  phonePriceRange.value = phoneStore.phonesByPriceRange;

  await phoneStore.getTotalPhoneByYear();
  phoneYear.value = phoneStore.phonesByYear;
});
</script>

<template>
  <div class="dashboard-wrap">
    <div class="section">
      <div>
        {{ totalPhones }}
      </div>
      <p>
        <n-tag :bordered="false" type="warning"> Tổng số điện thoại </n-tag>
      </p>
    </div>
    <div class="section">
      <div>{{ totalBrands }}</div>
      <p>
        <n-tag :bordered="false" type="success"> Tổng hãng điện thoại </n-tag>
      </p>
    </div>
    <div class="section empty">Chưa có</div>
    <div class="section empty">Chưa có</div>
    <div class="section">
      <TotalPhoneByBrand
        v-if="phoneBrands.length > 0"
        :phone-brand="phoneBrands"
        class="custom-chart"
      />
    </div>
    <div class="section">
      <TotalPhoneByPriceRange
        v-if="phonePriceRange.length > 0"
        :phones="phonePriceRange"
        class="custom-chart"
      />
    </div>
    <div class="section">
      <ToTalPhoneByYear v-if="phoneYear.length > 0" :phones="phoneYear" class="custom-chart" />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.dashboard-wrap {
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-template-rows: repeat(4, 200px);
  grid-gap: 28px;
  div.section {
    background-color: #fff;
    -webkit-box-shadow: 0px 0px 10px 0px rgba(166, 166, 166, 1);
    -moz-box-shadow: 0px 0px 10px 0px rgba(166, 166, 166, 1);
    box-shadow: 0px 0px 10px 0px rgba(166, 166, 166, 1);
    padding: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    > div {
      font-size: 48px;
      font-weight: 600;
    }
    .custom-chart {
      width: 100%;
      height: 100%;
    }
    &.empty {
      font-size: 18px;
      color: #b6b6b6;
    }
  }
  div.section:nth-child(1) {
    grid-column: 1 / 2;
  }
  div.section:nth-child(2) {
    grid-column: 2 / 3;
  }
  div.section:nth-child(3) {
    grid-column: 1 / 2;
    grid-row: 2 / 3;
  }
  div.section:nth-child(4) {
    grid-column: 2 / 3;
    grid-row: 2 / 3;
  }
  div.section:nth-child(5) {
    grid-column: 3 / 5;
    grid-row: 1 / 3;
  }
  div.section:nth-child(6) {
    grid-column: 1 / 3;
    grid-row: 3 / 5;
  }
  div.section:nth-child(7) {
    grid-column: 3 / 5;
    grid-row: 3 / 5;
  }
}
</style>
