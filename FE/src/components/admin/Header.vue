<script setup>
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
import { useMessage } from 'naive-ui';

import AppButton from '../user/AppButton.vue';

const router = useRouter();
const userStore = useUserStore();
const message = useMessage();

const signOutHandler = () => {
  userStore.logout();
  router.push('/sign-in');
  message.success('Đăng xuất thành công!');
};
</script>

<template>
  <header>
    <div class="logo" @click="router.push('/')">
      <img src="../../assets/logo.png" alt="" />
    </div>
    <p class="title">Quản lý điện thoại</p>
    <AppButton class="sign-out-btn" @click="signOutHandler">Đăng xuất</AppButton>
  </header>
</template>

<style lang="scss" scoped>
header {
  display: flex;
  padding: 16px 32px;
  gap: 32px;
  align-items: center;
  border-bottom: 2px solid $color-primary;
  background: $color-primary;
  position: relative;
  z-index: 10;
  height: 95px;
  .logo {
    width: 60px;
    height: 60px;
    cursor: pointer;
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      border-radius: 50%;
    }
  }
  .title {
    font-size: 36px;
    font-weight: 700;
    margin-top: 0;
    margin-left: 0;
    margin-bottom: 0 !important;
    color: #fff;
  }
  .sign-out-btn {
    position: absolute;
    right: 32px;
    background-color: #fff;
    color: #000;
  }
}
</style>
