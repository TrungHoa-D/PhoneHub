<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';

import heartIcon from '@assets/icons/heart.svg';
import logOutIcon from '@assets/icons/logout.svg';

import AppButton from './AppButton.vue';

const userStore = useUserStore();
const router = useRouter();
const toggle = ref(false);
const settingRef = ref(null);
const profile = ref(null);

const toggleSetting = () => {
  toggle.value = !toggle.value;
};

// Xử lý khi chọn một lựa chọn trong setting
const selectOption = (option) => {
  if (option === 'log-out') {
    router.push({ name: 'Sign in' });
    toggle.value = false;
  }
  toggle.value = false;
};

// Đóng setting khi bấm ra ngoài
const handleClickOutside = (event) => {
  if (settingRef.value && !settingRef.value.contains(event.target)) {
    toggle.value = false;
  }
};

onMounted(async () => {
  document.addEventListener('click', handleClickOutside);
  await userStore.getProfile();
  profile.value = userStore.username;
});

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside);
});
</script>

<template>
  <header>
    <div class="header--left">
      <div class="logo" @click="router.push('/')">
        <img src="../../assets/logo.png" alt="" />
      </div>
      <nav>
        <router-link to="/">Trang chủ</router-link>
        <router-link to="/brands">Hãng điện thoại</router-link>
        <router-link to="/search">Tìm kiếm</router-link>
        <router-link to="/compare">So sánh</router-link>
      </nav>
    </div>
    <div class="header--right">
      <AppButton @click="router.push('/admin/dashboard')" v-if="!profile">Đăng nhập</AppButton>

      <div class="profile-infor" v-else>
        <div class="profile">
          <img src="../../assets/icons/user.svg" alt="" />
        </div>
        <span>{{ profile }}</span>
      </div>
      <!-- <div class="profile-settings" v-if="toggle" ref="settingRef">
        <p @click="selectOption"><img :src="heartIcon" alt="" /><a>Danh sách yêu thích</a></p>
        <p @click="selectOption('log-out')"><img :src="logOutIcon" alt="" /><a>Đăng xuất</a></p>
      </div> -->
    </div>
  </header>
</template>

<style lang="scss" scoped>
header {
  display: flex;
  align-items: center;
  padding: 36px 200px;
  font-size: 18px;
  justify-content: space-between;
  .header--left {
    display: flex;
    align-items: center;
    gap: 64px;
    .logo {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      -webkit-box-shadow: 0px 7px 15px -3px rgba(156, 156, 156, 1);
      -moz-box-shadow: 0px 7px 15px -3px rgba(156, 156, 156, 1);
      box-shadow: 0px 7px 15px -3px rgba(156, 156, 156, 1);
      cursor: pointer;
      transition: all 0.3s ease-in-out;
      &:hover {
        transform: scale(1.1);
      }
      img {
        border-radius: 50%;
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }
    nav {
      display: flex;
      gap: 32px;
      a {
        font-weight: 500;
        color: rgb(27, 55, 100);
        transition: all 0.3s;
        &:hover {
          color: $color-primary;
        }
      }
    }
  }
  .header--right {
    position: relative;
    .profile-infor {
      display: flex;
      gap: 4px;
      align-items: center;
    }
    .profile {
      width: 24px;
      height: 24px;
      cursor: pointer;
      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }
    .profile-settings {
      position: absolute;
      right: 0;
      top: 135%;
      z-index: 10;
      width: max-content;
      border: 2px solid;
      padding: 16px;
      border-radius: 8px;
      display: flex;
      flex-direction: column;
      gap: 8px;
      p {
        display: flex;
        align-items: center;
        gap: 8px;
        cursor: pointer;
        a {
          transition: all 0.3s;
          &:hover {
            color: $color-primary;
          }
        }
      }
    }
  }
}
</style>
