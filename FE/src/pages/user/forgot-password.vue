<script setup>
import { ref, onMounted } from 'vue';
import { gsap } from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';
import { useRouter } from 'vue-router';

import AppButton from '@/components/user/AppButton.vue';
import leftArrow from '@assets/icons/leftArrow.svg';

// Đăng ký ScrollTrigger
gsap.registerPlugin(ScrollTrigger);

const router = useRouter();
const forgotPassword = ref(null);
const formRef = ref(null);
onMounted(() => {
  gsap.from(forgotPassword.value, {
    opacity: 0,
    x: -400,
    duration: 1.4,
    ease: 'power2.out'
  });
  gsap.from(formRef.value, {
    opacity: 0,
    x: 400,
    duration: 1.4,
    ease: 'power2.out'
  });
});
</script>

<template>
  <div class="forgot-password-wrap">
    <div class="back-icon" @click="router.push({ name: 'Home' })">
      <img :src="leftArrow" alt="" />
      <span>Back</span>
    </div>
    <div class="forgot-password-img-wrap" ref="forgotPassword">
      <img src="../../assets/forgot-password.png" alt="" />
    </div>
    <form ref="formRef">
      <div class="form-heading">
        <p class="title">Quên mật khẩu</p>
        <p class="desc">Tìm lại mật khẩu đánh rơi của bạn!</p>
      </div>
      <div class="form-wrap">
        <div>
          <input type="text" id="email" placeholder="Tài khoản" />
        </div>
        <p>Bạn đã nhớ lại? <router-link :to="{ name: 'Sign in' }">Đăng nhập</router-link></p>
        <AppButton>Đăng nhập</AppButton>
      </div>
    </form>
  </div>
</template>

<style lang="scss" scoped>
.forgot-password-wrap {
  display: flex;
  align-items: center;
  gap: 32px;
  .back-icon {
    position: absolute;
    top: 50px;
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
  form {
    width: 600px;
    .form-heading {
      font-weight: 500;
      text-align: center;
      margin-bottom: 32px;
      .title {
        font-size: 70px;
        color: $text-color;
      }
      .desc {
        font-size: 22px;
        color: rgb(90, 90, 90);
      }
    }
    .form-wrap {
      display: flex;
      flex-direction: column;
      gap: 32px;
      input[type='password'],
      input[type='text'] {
        width: 100%;
        padding: 20px 32px;
        border-radius: 8px;
        border: 2px solid #ebeaea;
        background: #f9f8f8;
        font-size: 20px;
        &:focus {
          border-color: $color-primary;
        }
      }
      .remember-me {
        font-size: 20px;
        display: flex;
        justify-content: space-between;
        label {
          margin-left: 8px;
          color: rgb(90, 90, 90);
        }
        > a {
          font-size: 18px;
          color: rgb(90, 90, 90);
          transition: all 0.3s;
          &:hover {
            color: $color-primary;
          }
        }
      }
      > p {
        font-size: 20px;
        color: rgb(90, 90, 90);
        > a {
          text-decoration: underline;
          transition: all 0.3s;
          &:hover {
            color: $color-primary;
          }
        }
      }
      > button {
        justify-content: center;
        padding: 20px;
      }
    }
  }
}
</style>
