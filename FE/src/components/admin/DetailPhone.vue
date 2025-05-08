<script setup>
import { defineProps, onMounted, ref } from 'vue';

import { usePhoneStore } from '@/stores/phoneStore';

const phoneStore = usePhoneStore();
const props = defineProps({
  detailPhone: {}
});

const detailPhone = ref({});

onMounted(async () => {
  await phoneStore.getDetailPhoneById(props.detailPhone.id);

  detailPhone.value = phoneStore.detailPhone;
});
</script>

<template>
  <div class="detail-phone-wrap">
    <p class="detail-phone-title">{{ detailPhone.name }}</p>
    <!-- Màn hình -->
    <div class="detail-infor">
      <p class="title">Màn hình</p>
      <table>
        <tr>
          <td>Kích thước màn hình</td>
          <td>{{ detailPhone?.screen?.size || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Công nghệ màn hình</td>
          <td>{{ detailPhone?.screen?.screen || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Độ phân giải màn hình</td>
          <td>{{ detailPhone?.screen?.resolution || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Tính năng màn hình</td>
          <td v-html="detailPhone?.screen?.features || 'Không có'"></td>
        </tr>
        <tr>
          <td>Tần số quét</td>
          <td>{{ detailPhone?.screen?.scanFrequency || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Kiểu màn hình</td>
          <td>{{ detailPhone?.screen?.type || 'Không có' }}</td>
        </tr>
      </table>
    </div>
    <!-- Camera -->
    <div class="detail-infor">
      <p class="title">Camera</p>
      <table>
        <tr>
          <td>Camera sau</td>
          <td v-html="detailPhone?.camera?.mainCamera || 'Không có'"></td>
        </tr>
        <tr>
          <td>Camera trước</td>
          <td>{{ detailPhone?.camera?.selfieCamera || 'Không có' }}</td>
        </tr>
      </table>
    </div>
    <!-- Vi xử lý và đồ họa -->
    <div class="detail-infor">
      <p class="title">Vi xử lý & đồ họa</p>
      <table>
        <tr>
          <td>Chipset</td>
          <td>{{ detailPhone?.processor?.chipset || 'Không có' }}</td>
        </tr>
        <tr>
          <td>GPU</td>
          <td>{{ detailPhone?.processor?.gpu || 'Không có' }}</td>
        </tr>
        <tr>
          <td>CPU</td>
          <td>{{ detailPhone?.processor?.cpu || 'Không có' }}</td>
        </tr>
      </table>
    </div>
    <!-- Giao tiếp & kết nối -->
    <div class="detail-infor">
      <p class="title">Giao tiếp & kết nối</p>
      <table>
        <tr>
          <td>Công nghệ NFC</td>
          <td>{{ detailPhone?.connection?.mobile_nfc || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Thẻ SIM</td>
          <td>{{ detailPhone?.connection?.sim || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Hệ điều hành</td>
          <td>{{ detailPhone?.connection?.os || 'Không có' }}</td>
        </tr>
        <tr>
          <td>WLAN</td>
          <td>{{ detailPhone?.connection?.wlan || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Hỗ trợ mạng</td>
          <td>{{ detailPhone?.connection?.network || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Hỗ trợ Bluetooth</td>
          <td>{{ detailPhone?.connection?.bluetooth || 'Không có' }}</td>
        </tr>
        <tr>
          <td>GPS</td>
          <td>{{ detailPhone?.connection?.gps || 'Không có' }}</td>
        </tr>
      </table>
    </div>
    <!-- Lưu trữ -->
    <div class="detail-infor">
      <p class="title">Lưu trữ</p>
      <table>
        <tr>
          <td>RAM</td>
          <td>{{ detailPhone?.storage?.ram || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Bộ nhớ trong</td>
          <td>{{ detailPhone?.storage?.internalMemory || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Khe cắm thẻ nhớ</td>
          <td>{{ detailPhone?.storage?.memoryCardSlot || 'Không có' }}</td>
        </tr>
      </table>
    </div>
    -
    <!-- Pin & công nghệ sạc -->
    <div class="detail-infor">
      <p class="title">Pin & công nghệ sạc</p>
      <table>
        <tr>
          <td>Pin</td>
          <td>{{ detailPhone?.battery?.battery || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Công nghệ sạc</td>
          <td v-html="detailPhone?.battery?.charginTechnology || 'Không có'"></td>
        </tr>
        <tr>
          <td>Cổng sạc</td>
          <td>{{ detailPhone?.battery?.port || 'Không có' }}</td>
        </tr>
      </table>
    </div>
    <!-- Thiết kế và trọng lượng -->
    <div class="detail-infor">
      <p class="title">Thiết kế và trọng lượng</p>
      <table>
        <tr>
          <td>Kích thước</td>
          <td>{{ detailPhone?.design?.size || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Trọng lượng</td>
          <td>{{ detailPhone?.design?.weight || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Chất liệu</td>
          <td>{{ detailPhone?.design?.material || 'Không có' }}</td>
        </tr>
      </table>
    </div>
    <!-- Thông số khác -->
    <div class="detail-infor">
      <p class="title">Thông số khác</p>
      <table>
        <tr>
          <td>Làm mát</td>
          <td>{{ detailPhone?.otherInfor?.cooler || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Chỉ số kháng nước, bụi</td>
          <td>{{ detailPhone?.otherInfor?.resistanceIndex || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Công nghệ tiện ích</td>
          <td>{{ detailPhone?.otherInfor?.tech || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Công nghệ âm thanh</td>
          <td>{{ detailPhone?.otherInfor?.soundTech || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Các loại tiện ích</td>
          <td v-html="detailPhone?.otherInfor?.utilities || 'Không có'"></td>
        </tr>
        <tr>
          <td>Các loại cảm biến</td>
          <td>{{ detailPhone?.otherInfor?.sensor || 'Không có' }}</td>
        </tr>
        <tr>
          <td>Ngày phát hành</td>
          <td>{{ detailPhone?.releaseDate || 'Không có' }}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.detail-phone-title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 18px;
}
.detail-infor {
  margin-bottom: 16px;
  .title {
    font-size: 20px;
    margin-left: 0;
    margin-top: 0;
    margin-bottom: 8px;
  }
  table {
    width: 100%;
    border: 1px solid;
    border-collapse: collapse;
    tr {
      td {
        padding: 16px;
        &:first-child {
          width: 30%;
        }
      }
      &:nth-child(odd) {
        background: #efefef;
      }
    }
  }
  &:last-child {
    margin-bottom: 0;
  }
}
</style>
