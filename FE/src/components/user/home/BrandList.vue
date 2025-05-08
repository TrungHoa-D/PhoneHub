<script setup>
import { ref, onMounted } from 'vue';
import { gsap } from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';
import { useRouter } from 'vue-router';

import AppContentHeading from '../AppContentHeading.vue';
import AppBrand from '../AppBrand.vue';
import AppButton from '../AppButton.vue';

import { usePhoneStore } from '@/stores/phoneStore';

// Đăng ký ScrollTrigger
gsap.registerPlugin(ScrollTrigger);

const phoneStore = usePhoneStore();
const brandList = [
  {
    brand: 'Iphone',
    img: '/i/p/iphone-16-pro-max.png'
  },
  {
    brand: 'Samsung',
    img: '/g/a/galaxy-s24-ultra-vang_1.png'
  },
  {
    brand: 'Xiaomi',
    img: '/x/i/xiaomi_redmi_14c_5_.png'
  },
  {
    brand: 'OPPO',
    img: '/t/e/text_ng_n_7__2_102.png'
  },
  {
    brand: 'realme',
    img: '/r/e/realme-13-plus-5g_4_.jpg'
  }
];
const brandListRef = ref(null);
const router = useRouter();

const viewAllBrands = () => {
  router.push('brands');
};

const searchPhonesByBrand = async (brandName) => {
  await phoneStore.getPhonesByBrand(brandName);

  router.push({
    path: 'search',
    query: {
      brand: brandName
    }
  });
};

onMounted(() => {
  gsap.from(brandListRef.value, {
    opacity: 0,
    y: 400,
    duration: 1.5,
    ease: 'power2.out',
    scrollTrigger: {
      trigger: brandListRef.value,
      start: 'top 93%',
      toggleActions: 'play none none reverse'
    }
  });
});
</script>

<template>
  <div class="brand-list-wrap">
    <AppContentHeading :show-action="true">
      <template #title> Hãng điện thoại </template>
      <template #desc>
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Iure explicabo impedit
        voluptatibus, velit id consequuntur eaque ratione harum! Perferendis, quos.
      </template>
      <template #action>
        <AppButton :show-icon="true" @click="viewAllBrands"> Xem tất cả </AppButton>
      </template>
    </AppContentHeading>
    <div class="brand-list" ref="brandListRef">
      <AppBrand
        v-for="(brand, index) in brandList"
        :key="index"
        :brand="brand"
        :width="`24%`"
        @click="searchPhonesByBrand(brand.brand)"
      />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.brand-list-wrap {
  padding: 96px 200px;
  // margin-bottom: 100px;
  .brand-list {
    margin-top: 48px;
    display: flex;
    justify-content: space-between;
    gap: 20px;
  }
}
</style>
