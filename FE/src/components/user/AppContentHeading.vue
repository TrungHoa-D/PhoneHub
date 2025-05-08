<script setup>
import { ref, defineProps, onMounted } from 'vue';
import { gsap } from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';

import AppButton from './AppButton.vue';

// Đăng ký ScrollTrigger
gsap.registerPlugin(ScrollTrigger);

const props = defineProps({
  showAction: {
    type: Boolean,
    default: false
  }
});
const titleRef = ref(null);
const descRef = ref(null);
const actionRef = ref(null);

// Áp dụng hiệu ứng khi cuộn
onMounted(() => {
  // Hiệu ứng cho title
  gsap.from(titleRef.value, {
    opacity: 0,
    y: 200,
    duration: 1.2,
    ease: 'power2.out',
    scrollTrigger: {
      trigger: titleRef.value,
      start: 'top 80%',
      toggleActions: 'play none none reverse'
    }
  });

  // Hiệu ứng cho desc
  gsap.from(descRef.value, {
    opacity: 0,
    y: 200,
    duration: 1.3,
    ease: 'power2.out',
    scrollTrigger: {
      trigger: descRef.value,
      start: 'top 80%',
      toggleActions: 'play none none reverse'
    }
  });

  // Hiệu ứng cho action
  gsap.from(actionRef.value, {
    opacity: 0,
    y: 250,
    duration: 1.5,
    ease: 'power2.out',
    scrollTrigger: {
      trigger: actionRef.value,
      start: 'top 80%',
      toggleActions: 'play none none reverse'
    }
  });
});
</script>

<template>
  <div class="heading-wrap">
    <div class="heading">
      <p class="title" ref="titleRef">
        <slot name="title"> Tiêu đề </slot>
      </p>
      <p class="desc" ref="descRef">
        <slot name="desc"> </slot>
      </p>
    </div>
    <div v-if="showAction" ref="actionRef">
      <slot name="action">
        <AppButton show-icon="true"> Xem tất cả </AppButton>
      </slot>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.heading-wrap {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  .heading {
    max-width: 600px;
    .title {
      font-size: 40px;
      font-weight: 500;
      position: relative;
      display: inline-block;
      &:after {
        content: '';
        display: block;
        position: absolute;
        width: 100%;
        height: 5px;
        background: linear-gradient(
          135deg,
          rgba(255, 255, 255, 1) 0%,
          rgba(60, 133, 254, 1) 40%,
          rgba(60, 133, 254, 1) 60%,
          rgba(255, 255, 255, 1) 100%
        );
        bottom: 15px;
        z-index: -1;
      }
    }
    .desc {
      color: rgb(90, 90, 90);
      font-size: 18px;
    }
  }
}
</style>
