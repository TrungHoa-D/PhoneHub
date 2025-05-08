<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import Chart from 'chart.js/auto';

// Định nghĩa props
const props = defineProps({
  phones: {
    type: Array,
    required: true
  }
});

const chartCanvas = ref(null); // Tham chiếu canvas
let chartInstance = null; // Lưu trữ instance của Chart.js

const colors = ['#059BFF', '#FF6384', '#FF9F40', '#FFCD56', '#4BC0C0'];

// Hàm tạo hoặc cập nhật biểu đồ
const renderChart = () => {
  if (!chartCanvas.value || props.phones.length === 0) return;

  // Hủy biểu đồ cũ nếu đã tồn tại
  if (chartInstance) {
    chartInstance.destroy();
  }

  // Tạo biểu đồ mới
  chartInstance = new Chart(chartCanvas.value, {
    type: 'doughnut',
    data: {
      labels: props.phones.map((br) => br.range),
      datasets: [
        {
          label: 'Số lượng điện thoại theo giá',
          data: props.phones.map((br) => br.count),
          backgroundColor: colors, // Áp dụng màu ngẫu nhiên
          borderColor: colors, // Đổi độ trong suốt cho viền
          borderWidth: 2
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false
    }
  });
};

onMounted(() => renderChart());

// Hủy biểu đồ khi component bị tháo
onUnmounted(() => {
  if (chartInstance) {
    chartInstance.destroy();
  }
});
</script>

<template>
  <div v-if="props.phones.length > 0">
    <!-- Canvas để Chart.js render biểu đồ -->
    <canvas id="acquisitions" ref="chartCanvas"></canvas>
  </div>
</template>

<style scoped>
/* Tùy chỉnh kích thước canvas nếu cần */
canvas {
  width: 100%;
  height: 100%;
  max-width: 100%;
}
</style>
