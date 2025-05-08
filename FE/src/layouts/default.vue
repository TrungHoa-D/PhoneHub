<script setup>
import { useRoute } from 'vue-router';

import Header from '@/components/user/Header.vue';
import Footer from '@/components/user/Footer.vue';
import PhoneCompareProp from '@/components/user/PhoneCompareProp.vue';

import { usePhoneCompareStore } from '@/stores/phoneCompareStore';

const phoneCompareStore = usePhoneCompareStore();
const route = useRoute();
</script>

<template>
  <div id="root">
    <Header />
    <router-view></router-view>
  </div>
  <Footer />
  <PhoneCompareProp
    v-if="phoneCompareStore.phoneCompareList.length > 0 && route.name !== 'Compare'"
    :phoneList="phoneCompareStore.phoneCompareList"
  />
</template>

<style lang="scss" scoped></style>
