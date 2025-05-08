<script setup>
import { gsap } from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';
import { ref, onMounted, onBeforeMount } from 'vue';

// import phoneList from '@/stores/phoneList';
import AppContentHeading from '../AppContentHeading.vue';
import AppPhone from '../AppPhone.vue';

import { usePhoneStore } from '@/stores/phoneStore';

// Đăng ký ScrollTrigger
gsap.registerPlugin(ScrollTrigger);

const phoneStore = usePhoneStore();

const phoneList = ref([]);
const randomIndex = ref(0);
const randomPhoneList = ref([]);
const bestSellerListRef = ref(null);

onMounted(async () => {
  await phoneStore.getPhone();
  phoneList.value = phoneStore.allPhones;

  randomIndex.value = Math.floor(Math.random() * phoneList.value.length);
  randomPhoneList.value = phoneList.value.slice(randomIndex.value, randomIndex.value + 8);

  gsap.from(bestSellerListRef.value, {
    opacity: 0,
    y: 400,
    duration: 1.5,
    ease: 'power2.out',
    scrollTrigger: {
      trigger: bestSellerListRef.value,
      start: 'top 80%',
      toggleActions: 'play none none reverse'
    }
  });
});
</script>

<template>
  <div class="best-seller-wrap">
    <AppContentHeading :show-action="false" class="content-heading">
      <template #title> Sản phẩm nổi bật </template>
      <template #desc> Lorem ipsum dolor sit amet consectetur adipisicing elit. </template>
    </AppContentHeading>
    <div class="best-seller-list" ref="bestSellerListRef">
      <AppPhone
        v-for="phone in randomPhoneList"
        :key="phone.id"
        :phone="phone"
        style="width: 22%; height: 500px"
      />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.best-seller-wrap {
  padding: 96px 200px;
  .content-heading {
    justify-content: center;
    text-align: center;
  }
  .best-seller-list {
    margin-top: 48px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    gap: 32px 16px;
  }
}
</style>
