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

// Hàm tạo hoặc cập nhật biểu đồ
const renderChart = () => {
  if (!chartCanvas.value || props.phones.length === 0) return;

  // Hủy biểu đồ cũ nếu đã tồn tại
  if (chartInstance) {
    chartInstance.destroy();
  }

  // Tạo biểu đồ mới
  chartInstance = new Chart(chartCanvas.value, {
    type: 'line', // Chuyển sang biểu đồ dạng đường
    data: {
      labels: props.phones.map((br) => br.year),
      datasets: [
        {
          label: 'Số lượng điện thoại theo năm',
          data: props.phones.map((br) => br.count),
          backgroundColor: '#37F79A',
          borderColor: '#37F79A',
          borderWidth: 2,
          fill: false,
          tension: 0.2
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        x: {
          title: {
            display: true,
            text: 'Khoảng giá' // Nhãn cho trục X
          }
        },
        y: {
          title: {
            display: true,
            text: 'Số lượng' // Nhãn cho trục Y
          },
          beginAtZero: true // Bắt đầu trục Y từ 0
        }
      }
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
