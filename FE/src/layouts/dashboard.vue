<script setup>
import Header from '@/components/admin/Header.vue';
import Sidebar from '@/components/admin/Sidebar.vue';
</script>

<template>
  <div class="dashboard-container">
    <Header />
    <div>
      <div class="sidebar-wrap">
        <Sidebar />
      </div>
      <div class="main-content">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.dashboard-container > div {
  display: grid;
  grid-template-columns: 300px 1fr;
  height: fit-content;
  overflow: hidden;
  .sidebar-wrap {
    grid-column: 1 / 2;
    padding: 24px 8px 24px 48px;
    // border-right: 1px solid;
    position: relative;
    -webkit-box-shadow: 0px 0px 34px -8px rgba(122, 122, 122, 1);
    -moz-box-shadow: 0px 0px 34px -8px rgba(122, 122, 122, 1);
    box-shadow: 0px 0px 34px -8px rgba(122, 122, 122, 1);
  }
  .main-content {
    grid-column: 2 / 3;
    padding: 32px;
    background-color: #e7eaf0;
  }
}
</style>
