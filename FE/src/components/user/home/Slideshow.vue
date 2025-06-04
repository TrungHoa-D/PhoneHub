<script setup>
import { NCarousel, NImage } from 'naive-ui';
import { useRouter } from 'vue-router';
import { gsap } from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';
import { ref, onMounted, onBeforeMount } from 'vue';

// import phoneList from '@/stores/phoneList';
import AppButton from '../AppButton.vue';

import leftArrow from '@assets/icons/leftArrow.svg';
import rightArrow from '@assets/icons/rightArrow.svg';

import { usePhoneStore } from '@/stores/phoneStore';

// Đăng ký ScrollTrigger
gsap.registerPlugin(ScrollTrigger);

const phoneStore = usePhoneStore();

// Mock dữ liệu
const phoneList = ref([]);
const randomIndex = ref(0);
const randomPhoneList = ref([]);
const cellphone_url = import.meta.env.VITE_CELLPHONES_URL;

// Router
const router = useRouter();
const viewDetailPhone = (phoneName, phoneId) => {
  router.push({
    name: 'Phone',
    params: { name: phoneName },
    query: {
      id: phoneId
    }
  });
};

const formatTooltip = (value) => {
  let number = parseInt(value, 10);
  return new Intl.NumberFormat('vi-VN').format(number) + 'đ';
};

// Slide
const slides = ref(null);
onMounted(async () => {
  await phoneStore.getPhone();
  phoneList.value = phoneStore.allPhones;

  randomIndex.value = Math.floor(Math.random() * 50);
  randomPhoneList.value = phoneList.value.slice(randomIndex.value, randomIndex.value + 7);

  gsap.from(slides.value, {
    opacity: 0,
    duration: 2,
    ease: 'bounce.out'
  });
});
</script>

<template>
  <div class="slideshow-container" ref="slides">
    <n-carousel autoplay show-arrow>
      <div v-for="phone in randomPhoneList" :key="phone.id" class="slideshow-item">
        <div class="phone-infor">
          <p class="name">{{ phone.name }}</p>
          <p class="brand">Brand: {{ phone.brand }}</p>
          <p class="cost">
            Cost: <span v-if="phone.cost === -1">Hết hàng</span
            ><span v-else>{{ formatTooltip(phone.cost) }}</span>
          </p>
          <AppButton @click="viewDetailPhone(phone.name, phone.id)">Xem chi tiết</AppButton>
        </div>
        <div class="phone-img">
          <n-image class="n-image-custom" :src="`${cellphone_url}/${phone.img}`" />
        </div>
      </div>
      <template #arrow="{ prev, next }">
        <div class="custom-arrow">
          <button type="button" class="custom-arrow--left" @click="prev">
            <img :src="leftArrow" alt="Left Arrow" />
          </button>
          <button type="button" class="custom-arrow--right" @click="next">
            <img :src="rightArrow" alt="Right Arrow" />
          </button>
        </div>
      </template>
      <template #dots="{ total, currentIndex, to }">
        <ul class="custom-dots">
          <li
            v-for="index in total"
            :key="index"
            :class="{ 'is-active': currentIndex === index - 1 }"
            @click="to(index - 1)"
          />
        </ul>
      </template>
    </n-carousel>
  </div>
</template>

<style lang="scss" scoped>
.slideshow-container {
  //   background: #eff5f9;
  padding: 0 200px 96px;
  .slideshow-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 64px 0 96px;
    .phone-infor {
      margin-bottom: 48px;
      flex: 1;
      .name {
        font-size: 72px;
      }
      p {
        font-size: 28px;
        font-weight: 500;
      }
      p:not(.name) {
        color: rgb(27, 55, 100);
      }
      p.cost {
        margin-bottom: 32px;
      }
    }
    .phone-img {
      width: 500px;
      height: 500px;
      .n-image-custom {
        width: 100%;
        height: 100%;
      }
      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }
  }
}
.custom-arrow {
  display: flex;
  position: absolute;
  bottom: 0;
  right: -10px;
}
.custom-arrow button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 28px;
  height: 28px;
  margin-right: 12px;
  background-color: $color-primary;
  border-width: 0;
  border-radius: 8px;
  transition: background-color 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
}
.custom-arrow button:hover {
  opacity: 0.7;
}
.custom-dots {
  display: flex;
  margin: 0;
  padding: 0;
  position: absolute;
  bottom: 10px;
  left: 0;
}
.custom-dots li {
  display: inline-block;
  width: 12px;
  height: 4px;
  margin: 0 3px;
  border-radius: 4px;
  background-color: #000;
  transition:
    width 0.3s,
    background-color 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
}
.custom-dots li.is-active {
  width: 40px;
  background: $color-primary;
}
</style>
