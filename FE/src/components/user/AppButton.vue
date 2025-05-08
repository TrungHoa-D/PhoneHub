<script setup>
import { defineProps } from 'vue';
import rightArrowLine from '@assets/icons/rightArrowLine.svg';

const props = defineProps({
  showIcon: {
    type: Boolean,
    default: false
  }
});
</script>

<template>
  <button>
    <slot>Button</slot>
    <slot name="icon" v-if="props.showIcon">
      <img :src="rightArrowLine" alt="" />
    </slot>
  </button>
</template>

<style lang="scss" scoped>
button {
  font-size: 20px;
  font-weight: 600;
  color: #fff;
  background: $color-primary;
  padding: 12px 32px;
  border-radius: 6px;
  transition: all 0.3s;
  display: flex;
  align-items: center;
  gap: 16px;
  &:hover {
    background: $text-color;
    color: #fff;
  }
}
</style>
