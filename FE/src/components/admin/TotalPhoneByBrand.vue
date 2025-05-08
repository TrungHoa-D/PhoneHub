<script setup>
import { ref, watch, onUnmounted, onMounted } from 'vue';
import Chart from 'chart.js/auto';

// Định nghĩa props
const props = defineProps({
  phoneBrand: {
    type: Array,
    required: true
  }
});

const chartCanvas = ref(null); // Tham chiếu canvas
let chartInstance = null; // Lưu trữ instance của Chart.js

// Hàm tạo hoặc cập nhật biểu đồ
const renderChart = () => {
  if (!chartCanvas.value || props.phoneBrand.length === 0) return;

  // Hủy biểu đồ cũ nếu đã tồn tại
  if (chartInstance) {
    chartInstance.destroy();
    // chartInstance = null;
  }

  // Tạo biểu đồ mới
  chartInstance = new Chart(chartCanvas.value, {
    type: 'bar',
    data: {
      labels: props.phoneBrand.map((br) => br.brand),
      datasets: [
        {
          label: 'Tổng số điện thoại theo hãng',
          data: props.phoneBrand.map((br) => br.count),
          backgroundColor: 'rgba(20, 100, 244, 0.2)',
          borderColor: 'rgba(20, 100, 244, 1)',
          borderWidth: 1
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        x: {
          barPercentage: 1,
          categoryPercentage: 0.8
        },
        y: {
          min: 0
        }
      },
      transitions: {
        show: {
          animations: {
            y: {
              from: 0
            }
          }
        },
        hide: {
          animations: {
            y: {
              to: 0
            }
          }
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
  <div v-if="props.phoneBrand">
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
