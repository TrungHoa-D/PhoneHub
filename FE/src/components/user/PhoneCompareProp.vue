<script setup>
import AppButton from './AppButton.vue';
import { defineProps, ref } from 'vue';
import { useRouter } from 'vue-router';

import { usePhoneCompareStore } from '@/stores/phoneCompareStore';

const props = defineProps({
  phoneList: {
    type: Array
  }
});
const router = useRouter();
const phoneCompareStore = usePhoneCompareStore();
const cellphone_url = import.meta.env.VITE_CELLPHONES_URL;
const hidden = ref(false);

const delPhone = (name) => {
  phoneCompareStore.removePhoneCompare(name);
};

const hiddenCompareProp = () => {
  hidden.value = !hidden.value;
};
</script>

<template>
  <div class="phone-compare-prop" :class="{ hidden: hidden }">
    <div class="hidden-icon" :class="{ hidden: hidden }" @click="hiddenCompareProp">
      <img src="../../assets/icons/rightArrow.svg" alt="" />
    </div>
    <div class="item-wrap">
      <div class="item" v-for="(phone, index) in props.phoneList" :key="index">
        <div class="del-icon" @click="delPhone(phone.name)">
          <img src="../../assets/icons/del.svg" alt="" />
        </div>
        <div class="img">
          <img :src="`${cellphone_url}/${phone.img}`" alt="" />
        </div>
        <p>{{ phone.name }}</p>
      </div>
    </div>
    <AppButton class="custome-btn" @click="router.push('/compare')">So sánh</AppButton>
  </div>
</template>

<style lang="scss" scoped>
.phone-compare-prop {
  background-color: #fff;
  border: 2px solid $color-primary;
  border-bottom: none;
  border-top-left-radius: 16px;
  border-top-right-radius: 16px;
  position: fixed;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  align-items: center;
  gap: 64px;
  padding: 32px 64px 8px;
  transition: all 0.75s ease-in-out;
  &.hidden {
    transform: translate(-50%, 100%);
  }
  .hidden-icon {
    width: 32px;
    height: 32px;
    transform: translateX(-50%) rotate(90deg);
    position: absolute;
    top: -36px;
    left: 50%;
    cursor: pointer;
    transition: all 0.75s ease-in-out;
    &.hidden {
      transform: translateX(-50%) rotate(-90deg);
    }
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }
  .item-wrap {
    display: flex;
    gap: 148px;
    .item {
      flex: 1;
      display: flex;
      flex-direction: column;
      align-items: center;
      text-align: center;
      gap: 16px;
      font-size: 18px;
      position: relative;
      .del-icon {
        position: absolute;
        right: -6px;
        top: -6px;
        width: 20px;
        height: 20px;
        cursor: pointer;
        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }
      &.item-empty {
        width: 200px;
        height: 200px;
        border: 2px dashed #787878;
        border-radius: 16px;
        display: flex;
        align-items: center;
        justify-content: center;
        p {
          color: #787878;
        }
      }
      .img {
        width: 120px;
        height: 136px;
        background-color: blue;
        flex: 1;
        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }
      p {
        max-width: 200px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }
  .custome-btn {
    width: max-content;
  }
}
</style>
