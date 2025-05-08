<script setup>
import { defineProps } from 'vue';
const props = defineProps({
  brand: {
    type: Object
  },
  width: {
    type: String
  }
});
const cellphone_url = import.meta.env.VITE_CELLPHONES_URL;
</script>

<template>
  <div class="brand" :style="{ width: `${props.width}` }">
    <div class="img-wrap">
      <img :src="`${cellphone_url}/${props.brand.img}`" alt="" />
    </div>
    <router-link to="">{{ props.brand.brand }}</router-link>
  </div>
</template>

<style lang="scss" scoped>
.brand {
  font-size: 24px;
  font-weight: 500;
  height: 186px;
  //border: 2px solid $color-primary;
  border-radius: 10px;
  padding: 16px 0;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  transition: all 0.3s;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
  .img-wrap {
    width: 100px;
    height: 100px;
    margin-bottom: 8px;
    transition: all 0.4s;
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }
  &:hover {
    box-shadow: 0 6px 24px rgba(0, 0, 0, 0.3);
    transform: scale(1.02);
    .img-wrap {
      transform: scale(1.15);
    }
  }
}
</style>
