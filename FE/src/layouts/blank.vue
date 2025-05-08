<script setup></script>

<template>
  <div class="blank-wrap">
    <router-view></router-view>
  </div>
</template>

<style lang="scss" scoped>
.blank-wrap {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}
</style>
