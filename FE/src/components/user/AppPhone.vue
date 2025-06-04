<script setup>
import { useRouter } from 'vue-router';
import { defineProps } from 'vue';
import { useMessage } from 'naive-ui';

import AppButton from './AppButton.vue';

import { usePhoneCompareStore } from '@/stores/phoneCompareStore';

const message = useMessage();
const phoneCompareStore = usePhoneCompareStore();
const props = defineProps({
  phone: {
    type: Object
  }
});
const cellphone_url = import.meta.env.VITE_CELLPHONES_URL;

const formatTooltip = (value) => {
  let number = parseInt(value, 10);
  return new Intl.NumberFormat('vi-VN').format(number) + 'đ';
};

const router = useRouter();
const viewDetailPhone = () => {
  router.push({
    name: 'Phone',
    params: { name: props.phone.name },
    query: { id: props.phone.id }
  });
};
const addComparePhone = (phone) => {
  if (phoneCompareStore.phoneCompareList.length === 3) {
    message.info('Chỉ có thể so sánh tối đa 3 điện thoại');
    return;
  } else {
    phoneCompareStore.addPhoneCompare({
      img: phone.img,
      name: phone.name,
      id: phone.id
    });
    message.success('Thêm điện thoại thành công');
  }
};
</script>

<template>
  <div class="phone-wrap">
    <div class="cover-wrap" @click="viewDetailPhone">
      <img :src="`${cellphone_url}/${phone.img}`" alt="" />
    </div>
    <p class="brand">{{ phone.brand }}</p>
    <p class="name" @click="viewDetailPhone">{{ phone.name }}</p>
    <p class="price" :class="{ 'not-price': phone.cost === -1 }">
      <span v-if="phone.cost === -1">Hết hàng</span>
      <span v-else>{{ formatTooltip(phone.cost) }}</span>
    </p>
    <AppButton class="custome-btn" @click="addComparePhone(phone)">So sánh</AppButton>
  </div>
</template>

<style lang="scss" scoped>
.phone-wrap {
  // width: 22%;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  .cover-wrap {
    width: 100%;
    // height: 350px;
    overflow: hidden;
    transition: all 0.3s;
    cursor: pointer;
    &:hover {
      transform: scale(1.05);
    }
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }
  .brand {
    color: #5b5c60;
    font-size: 18px;
    font-weight: 400;
    margin-top: 16px;
    cursor: pointer;
    &:hover {
      color: #1b3764;
    }
  }
  .name {
    color: #1b3764;
    font-size: 24px;
    font-weight: 500;
    transition: all 0.3s;
    cursor: pointer;
    max-width: 200px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    &:hover {
      color: $color-primary;
    }
  }
  .price {
    font-weight: 500;
    color: #fff;
    font-size: 18px;
    display: inline-block;
    background: $color-primary;
    padding: 0 16px;
    border-radius: 6px;
  }
  .price.not-price {
    background: red;
  }
  .custome-btn {
    margin-top: 8px;
    padding: 8px 16px;
    font-size: 16px;
  }
}
</style>
