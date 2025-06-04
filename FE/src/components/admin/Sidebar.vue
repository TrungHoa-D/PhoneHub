<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();
const route = useRoute();
const role = ref('');

onMounted(async () => {
  await userStore.getProfile();
  role.value = userStore.role;
});
</script>

<template>
  <div class="sidebar">
    <div>
      <div class="icon" :class="{ active: route.path === '/admin/dashboard' }">
        <img src="../../assets/icons/rightArrow.svg" alt="" />
      </div>
      <router-link to="/admin/dashboard" :class="{ active: route.path === '/admin/dashboard' }">
        Trang tổng quan
      </router-link>
    </div>
    <div>
      <div class="icon" :class="{ active: route.path === '/admin/phones-list' }">
        <img src="../../assets/icons/rightArrow.svg" alt="" />
      </div>
      <router-link to="/admin/phones-list" :class="{ active: route.path === '/admin/phones-list' }">
        Danh sách điện thoại
      </router-link>
    </div>
    <div v-if="role == 'ROLE_ADMIN'">
      <div class="icon" :class="{ active: route.path === '/admin/user-management' }">
        <img src="../../assets/icons/rightArrow.svg" alt="" />
      </div>
      <router-link
        to="/admin/user-management"
        :class="{ active: route.path === '/admin/user-management' }"
      >
        Quản lý tài khoản
      </router-link>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.sidebar {
  display: flex;
  flex-direction: column;
  gap: 16px;
  min-height: 100vh;
  > div {
    font-size: 20px;
    display: flex;
    align-items: center;
    // justify-content: center;
    position: relative;
    line-height: 32px;
    .icon {
      position: absolute;
      left: -24px;
      display: none;
      &.active {
        display: block;
      }
    }
    a {
      position: relative;
      &.active {
        color: $color-primary;
        font-weight: 600;
      }
      &:hover {
        color: $color-primary;
        font-weight: 600;
      }
      &::before {
        height: 2px;
        bottom: -6px;
        content: ' ';
        width: 0;
        left: 50%;
        position: absolute;
        background-color: $color-primary;
        transform: translateX(-50%);
      }
    }
    &:hover {
      .icon {
        display: block;
      }
      a:hover:before {
        width: 100%;
        transition: all 0.5s;
      }
    }
  }
}
</style>
