<script setup>
import { ref, onMounted } from 'vue';
import { gsap } from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';
import { useRouter } from 'vue-router';

import brandList from '@/stores/brandList';

import AppContentHeading from '@/components/user/AppContentHeading.vue';
import AppBrand from '@/components/user/AppBrand.vue';

import leftArrow from '@assets/icons/leftArrow.svg';

import { usePhoneStore } from '@/stores/phoneStore';

// Đăng ký ScrollTrigger
gsap.registerPlugin(ScrollTrigger);

const phoneStore = usePhoneStore();
const brands = brandList;
const brandListRef = ref(null);
const router = useRouter();

const searchPhonesByBrand = async (brandName) => {
  await phoneStore.getPhonesByBrand(brandName);

  router.push({
    path: 'search',
    query: {
      brand: brandName
    }
  });
};

onMounted(async () => {
  await phoneStore.getAllBrand();
  gsap.from(brandListRef.value, {
    opacity: 0,
    y: 400,
    duration: 1.5,
    ease: 'power2.out'
  });
});
</script>

<template>
  <div class="brand-list-wrap">
    <div class="back-icon" @click="router.back()">
      <img :src="leftArrow" alt="" />
      <span>Back</span>
    </div>
    <AppContentHeading :show-action="false" class="content-heading">
      <template #title> Hãng điện thoại </template>
      <template #desc>
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Iure explicabo impedit
        voluptatibus, velit id consequuntur eaque ratione harum! Perferendis, quos.
      </template>
    </AppContentHeading>
    <div class="brand-list" ref="brandListRef">
      <AppBrand
        v-for="(brand, index) in brands"
        :key="index"
        :brand="brand"
        @click="searchPhonesByBrand(brand.brand)"
      />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.brand-list-wrap {
  padding: 96px 200px;
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  .back-icon {
    position: absolute;
    top: 32px;
    left: 200px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 18px;
    font-weight: 500;
    cursor: pointer;
    img {
      width: 24px;
      height: 24px;
    }
  }
  .content-heading {
    justify-content: center;
    text-align: center;
  }
  .brand-list {
    margin-top: 48px;
    width: 100%;
    // display: flex;
    // gap: 20px;
    // flex-wrap: wrap;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 32px;
  }
}
</style>
