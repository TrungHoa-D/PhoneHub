<script setup>
import { useRoute, useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import { gsap } from 'gsap';
import ScrollTrigger from 'gsap/ScrollTrigger';
import { useMessage } from 'naive-ui';

import AppButton from '@/components/user/AppButton.vue';

// import phoneList from '@/stores/phoneList';
import leftArrow from '@assets/icons/leftArrow.svg';

import { usePhoneCompareStore } from '@/stores/phoneCompareStore';
import { usePhoneStore } from '@/stores/phoneStore';

// Đăng ký ScrollTrigger
gsap.registerPlugin(ScrollTrigger);

const router = useRouter();
const route = useRoute();
const phoneCompareStore = usePhoneCompareStore();
const phoneStore = usePhoneStore();
const detailPhone = ref({});
const message = useMessage();
const cellphone_url = import.meta.env.VITE_CELLPHONES_URL;

const phoneCover = ref(null);
const phoneInfor = ref(null);

const addComparePhone = (phone) => {
  if (phoneCompareStore.phoneCompareList.length === 3) {
    message.info('Chỉ có thể so sánh tối đa 3 điện thoại');
    return;
  } else {
    phoneCompareStore.addPhoneCompare({
      img: phone.img,
      name: phone.name,
      id: phone.id
    });
    message.success('Thêm điện thoại thành công');
  }
};

const formatTooltip = (value) => {
  let number = parseInt(value, 10);
  return new Intl.NumberFormat('vi-VN').format(number) + 'đ';
};

onMounted(async () => {
  await phoneStore.getDetailPhoneById(route.query.id);
  detailPhone.value = phoneStore.detailPhone;

  if (phoneCover.value) {
    gsap.from(phoneCover.value, {
      opacity: 0,
      x: -400,
      duration: 1.4,
      ease: 'power2.out'
    });
  }
  if (phoneInfor.value) {
    gsap.from(phoneInfor.value, {
      opacity: 0,
      x: 400,
      duration: 1.4,
      ease: 'power2.out'
    });
  }
});
</script>

<template>
  <div v-if="detailPhone" class="detail-phone-wrap">
    <div class="back-icon" @click="router.back()">
      <img :src="leftArrow" alt="" />
      <span>Back</span>
    </div>
    <div class="cover-wrap" ref="phoneCover">
      <img :src="`${cellphone_url}/${detailPhone.img}`" alt="" />
    </div>
    <div class="infor" ref="phoneInfor">
      <p class="brand">{{ detailPhone?.brand }}</p>
      <p class="name">{{ detailPhone?.name }}</p>
      <p class="price" :class="{ 'not-price': detailPhone?.cost === -1 }">
        <span v-if="detailPhone?.cost === -1">Không có giá</span>
        <span v-else>{{ formatTooltip(detailPhone?.cost) }}</span>
      </p>
      <div>
        <p><span>Kích thước màn hình: </span>{{ detailPhone?.screen?.size || 'Không có' }}</p>
        <p><span>Công nghệ màn hình: </span>{{ detailPhone?.screen?.screen || 'Không có' }}</p>
        <div>
          <span>Camera sau: </span>
          <p v-html="detailPhone?.camera?.mainCamera || 'Không có'"></p>
        </div>
        <p><span>Camera trước: </span>{{ detailPhone?.camera?.selfieCamera || 'Không có' }}</p>
        <p><span>Chipset: </span>{{ detailPhone?.processor?.chipset || 'Không có' }}</p>
        <p><span>Dung lượng RAM: </span>{{ detailPhone?.storage?.ram || 'Không có' }}</p>
        <p><span>Bộ nhớ trong: </span>{{ detailPhone?.storage?.internalMemory || 'Không có' }}</p>
        <p><span>Pin: </span>{{ detailPhone?.battery?.battery || 'Không có' }}</p>
        <p><span>Hệ điều hành: </span>{{ detailPhone?.connection?.os || 'Không có' }}</p>
        <p>
          <span>Độ phân giải màn hình: </span>{{ detailPhone?.screen?.resolution || 'Không có' }}
        </p>
        <p><span>Ngày phát hành: </span>{{ detailPhone?.releaseDate || 'Không có' }}</p>
      </div>
      <AppButton class="custome-btn" @click="addComparePhone(detailPhone)">So sánh</AppButton>
    </div>
  </div>
  <div class="phone-specifications">
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
          <td v-html="detailPhone?.otherInfor?.soundTech || 'Không có'"></td>
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
.detail-phone-wrap {
  padding: 48px 200px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 48px;
  position: relative;
  overflow: hidden;
  .back-icon {
    position: absolute;
    top: 0;
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
  .cover-wrap {
    border: 2px solid $color-primary;
    border-radius: 12px;
    width: 630px;
    height: 670px;
    img {
      padding: 72px;
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: all 0.3s;
      &:hover {
        transform: scale(1.1);
      }
    }
  }
  .infor {
    flex: 1;
    .brand {
      color: #5b5c60;
      font-size: 24px;
      font-weight: 400;
      cursor: pointer;
      &:hover {
        color: #1b3764;
      }
    }
    .name {
      color: #1b3764;
      font-size: 48px;
      font-weight: 500;
      transition: all 0.3s;
      cursor: pointer;
      &:hover {
        color: $color-primary;
      }
    }
    .price {
      font-weight: 500;
      color: #fff;
      font-size: 20px;
      display: inline-block;
      background: $color-primary;
      padding: 0 16px;
      border-radius: 6px;
      &.not-price {
        background: red;
      }
    }
    > div {
      font-size: 20px;
      margin-top: 32px;
      p,
      div {
        color: #5b5c60;
        span {
          color: rgb(27, 55, 100);
          font-weight: 500;
          margin-right: 8px;
        }
        &:not(:last-child) {
          margin-bottom: 8px;
        }
      }
    }
    .custome-btn {
      margin-top: 16px;
      padding: 8px 32px;
    }
  }
}
.phone-specifications {
  margin: 32px 0 64px;
  padding: 0 200px;
  .detail-infor {
    margin-bottom: 16px;
    .title {
      font-size: 22px;
      color: rgb(27, 55, 100);
      font-weight: 600;
      margin-left: 0;
      margin-top: 0;
      margin-bottom: 8px;
    }
    table {
      width: 100%;
      border: 1px solid;
      border-collapse: collapse;
      font-size: 18px;
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
}
</style>
