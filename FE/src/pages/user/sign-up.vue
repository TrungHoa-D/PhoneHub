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
const signupImg = ref(null);
const formRef = ref(null);
onMounted(() => {
  gsap.from(signupImg.value, {
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
  <div class="signup-wrap">
    <div class="back-icon" @click="router.push({ name: 'Home' })">
      <img :src="leftArrow" alt="" />
      <span>Home</span>
    </div>
    <div class="signup-img-wrap" ref="signupImg">
      <img src="../../assets/login.png" alt="" />
    </div>
    <form ref="formRef">
      <div class="form-heading">
        <p class="title">Đăng ký</p>
        <p class="desc">Đăng lý tài khoản của bạn!</p>
      </div>
      <div class="form-wrap">
        <div>
          <input type="text" id="email" placeholder="Tài khoản" />
        </div>
        <div>
          <input type="password" id="password" placeholder="Mật khẩu" />
        </div>
        <div>
          <input type="password" id="password" placeholder="Nhập lại mật khẩu" />
        </div>
        <div class="remember-me">
          <div>
            <input type="checkbox" id="remember" />
            <label for="remember">Tôi đồng ý!</label>
          </div>
        </div>
        <p>
          Bạn đã có tài khoản? <router-link :to="{ name: 'Sign in' }">Đăng nhập ngay</router-link>
        </p>
        <AppButton>Đăng ký</AppButton>
      </div>
    </form>
  </div>
</template>

<style lang="scss" scoped>
.signup-wrap {
  display: flex;
  align-items: center;
  gap: 16px;
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
