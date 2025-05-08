<script setup>
import { NModal, NCard } from 'naive-ui';
import { ref, watch, onMounted } from 'vue';
import { gsap } from 'gsap';

import { usePhoneCompareStore } from '@/stores/phoneCompareStore';

import AppContentHeading from '@/components/user/AppContentHeading.vue';
import SearchPhone from '@/components/user/SearchPhone.vue';

import upArrow from '@assets/icons/up-arrow.svg';
import downArrow from '@assets/icons/down-arrow.svg';

// import phoneList from '@/stores/phoneList';

const phoneCompareStore = usePhoneCompareStore();
const cellphone_url = import.meta.env.VITE_CELLPHONES_URL;
const showModal = ref(false);
const phoneCompareList = ref([]);
const phoneListEmpty = ref(0);
const phonesPickerRef = ref(null);
const phoneList = ref([]);

phoneCompareList.value = phoneCompareStore.phoneCompareList;

const formatTooltip = (value) => {
  let number = parseInt(value, 10);
  return new Intl.NumberFormat('vi-VN').format(number) + 'đ';
};

const delPhone = (name) => {
  phoneCompareStore.removePhoneCompare(name);
};

if (phoneCompareList.value.length === 0) phoneListEmpty.value = 3;
watch(phoneCompareList.value, () => {
  phoneListEmpty.value = 3 - phoneCompareList.value.length;
  phoneList.value = phoneCompareList.value;
});

function extractNumericValue(value) {
  if (typeof value === 'string') {
    const match = value.match(/[\d.]+/); // Trích xuất số (bao gồm số thập phân)
    return match ? parseFloat(match[0]) : 0; // Chuyển thành số thực
  }
  return typeof value === 'number' ? value : 0; // Nếu đã là số, trả về trực tiếp
}

// Tìm giá trị lớn nhất của thuộc tính được chọn
const maxValue = (prop) => {
  console.log(
    Math.max(
      ...phoneList.value.map((product) => {
        const keys = prop.split('.'); // Tách chuỗi thuộc tính lồng nhau
        const value = keys.reduce((acc, key) => acc?.[key], product); // Lấy giá trị
        return extractNumericValue(value); // Chuyển đổi giá trị sang số trước khi so sánh
      })
    )
  );
  return Math.max(
    ...phoneList.value.map((product) => {
      const keys = prop.split('.'); // Tách chuỗi thuộc tính lồng nhau
      const value = keys.reduce((acc, key) => acc?.[key], product); // Lấy giá trị
      return extractNumericValue(value); // Chuyển đổi giá trị sang số trước khi so sánh
    })
  );
};

onMounted(() => {
  phoneListEmpty.value = 3 - phoneCompareList.value.length;
  phoneList.value = phoneCompareList.value;

  gsap.from(phonesPickerRef.value, {
    opacity: 0,
    y: 400,
    duration: 1.5,
    ease: 'power2.out'
  });
});
</script>

<template>
  <div class="compare-wrap">
    <AppContentHeading :show-action="false" class="content-heading">
      <template #title> So sánh điện thoại </template>
    </AppContentHeading>
    <div class="phones-picker" ref="phonesPickerRef">
      <div class="phone-picker" v-for="(phone, index) in phoneCompareList" :key="index">
        <div class="del-icon" @click="delPhone(phone.name)">
          <img src="../../assets/icons/del.svg" alt="" />
        </div>
        <div v-if="phone" class="phone-wrap">
          <div class="phone-img">
            <img :src="`${cellphone_url}${phone.img}`" alt="" />
          </div>
          <p>{{ phone.name }}</p>
        </div>
      </div>
      <div class="phone-empty" v-for="n in phoneListEmpty" :key="n" @click="showModal = true">
        <p>Empty</p>
      </div>
    </div>
    <div class="phone-specifications">
      <div class="detail-infor">
        <p class="title">Tên điện thoại</p>
        <table>
          <tr>
            <td>Tên điện thoại</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.name || 'Không có' }}
            </td>
          </tr>
          <tr>
            <td>Giá</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              <span v-if="phone.cost === -1">Không có giá</span>
              <span v-else>{{ formatTooltip(phone.cost) }}</span>
              <span class="arrow">
                <img v-if="extractNumericValue(phone.cost) === maxValue('cost')" :src="upArrow" />
                <img v-else :src="downArrow" />
              </span>
            </td>
          </tr>
          <tr>
            <td>Ngày phát hành</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.releaseDate || 'Không có' }}
            </td>
          </tr>
        </table>
      </div>
      <!-- Màn hình -->
      <div class="detail-infor">
        <p class="title">Màn hình</p>
        <table>
          <tr>
            <td>Kích thước màn hình</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.screen.size || 'Không có' }}
              <span class="arrow">
                <img
                  v-if="extractNumericValue(phone.screen.size) === maxValue('screen.size')"
                  :src="upArrow"
                />
                <img v-else :src="downArrow" />
              </span>
            </td>
          </tr>
          <tr>
            <td>Công nghệ màn hình</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.screen.screen || 'Không có' }}
            </td>
          </tr>
          <tr>
            <td>Độ phân giải màn hình</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.screen.resolution || 'Không có' }}
              <span class="arrow">
                <img
                  v-if="
                    extractNumericValue(phone.screen.resolution) === maxValue('screen.resolution')
                  "
                  :src="upArrow"
                />
                <img v-else :src="downArrow" />
              </span>
            </td>
          </tr>
          <tr>
            <td>Tính năng màn hình</td>
            <td
              v-for="(phone, index) in phoneList"
              :key="index"
              v-html="phone.screen.features || 'Không có'"
            ></td>
          </tr>
          <tr>
            <td>Tần số quét</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.screen.scanFrequency || 'Không có' }}
              <span class="arrow">
                <img
                  v-if="
                    extractNumericValue(phone.screen.scanFrequency) ===
                    maxValue('screen.scanFrequency')
                  "
                  :src="upArrow"
                />
                <img v-else :src="downArrow" />
              </span>
            </td>
          </tr>
          <tr>
            <td>Kiểu màn hình</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.screen.type || 'Không có' }}
            </td>
          </tr>
        </table>
      </div>
      <!-- Camera -->
      <div class="detail-infor">
        <p class="title">Camera</p>
        <table>
          <tr>
            <td>Camera sau</td>
            <td
              v-for="(phone, index) in phoneList"
              :key="index"
              v-html="phone.camera.mainCamera || 'Không có'"
            ></td>
          </tr>
          <tr>
            <td>Camera trước</td>
            <td
              v-for="(phone, index) in phoneList"
              :key="index"
              v-html="phone.camera.selfieCamera || 'Không có'"
            ></td>
          </tr>
        </table>
      </div>
      <!-- Vi xử lý và đồ họa -->
      <div class="detail-infor">
        <p class="title">Vi xử lý & đồ họa</p>
        <table>
          <tr>
            <td>Chipset</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.processor.chipset || 'Không có' }}
            </td>
          </tr>
          <tr>
            <td>GPU</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.processor.gpu || 'Không có' }}
            </td>
          </tr>
          <tr>
            <td>CPU</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.processor.cpu || 'Không có' }}
            </td>
          </tr>
        </table>
      </div>
      <!-- Giao tiếp & kết nối -->
      <div class="detail-infor">
        <p class="title">Giao tiếp & kết nối</p>
        <table>
          <tr>
            <td>Công nghệ NFC</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.connection.mobile_nfc || 'Không có' }}
            </td>
          </tr>
          <tr>
            <td>Thẻ SIM</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.connection.sim || 'Không có' }}
            </td>
          </tr>
          <tr>
            <td>Hệ điều hành</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.connection.os || 'Không có' }}
            </td>
          </tr>
          <tr>
            <td>WLAN</td>
            <td
              v-for="(phone, index) in phoneList"
              :key="index"
              v-html="phone.connection.wlan || 'Không có'"
            ></td>
          </tr>
          <tr>
            <td>Hỗ trợ mạng</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.connection.network || 'Không có' }}
            </td>
          </tr>
          <tr>
            <td>Hỗ trợ Bluetooth</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.connection.bluetooth || 'Không có' }}
            </td>
          </tr>
          <tr>
            <td>GPS</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.connection.gps || 'Không có' }}
            </td>
          </tr>
        </table>
      </div>
      <!-- Lưu trữ -->
      <div class="detail-infor">
        <p class="title">Lưu trữ</p>
        <table>
          <tr>
            <td>RAM</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.storage.ram || 'Không có' }}
              <span class="arrow">
                <img
                  v-if="extractNumericValue(phone.storage.ram) === maxValue('storage.ram')"
                  :src="upArrow"
                />
                <img v-else :src="downArrow" />
              </span>
            </td>
          </tr>
          <tr>
            <td>Bộ nhớ trong</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.storage.internalMemory || 'Không có' }}
              <span class="arrow">
                <img
                  v-if="
                    extractNumericValue(phone.storage.internalMemory) ===
                    maxValue('storage.internalMemory')
                  "
                  :src="upArrow"
                />
                <img v-else :src="downArrow" />
              </span>
            </td>
          </tr>
          <tr>
            <td>Khe cắm thẻ nhớ</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.storage.memoryCardSlot || 'Không có' }}
            </td>
          </tr>
        </table>
      </div>
      <!-- Pin & công nghệ sạc -->
      <div class="detail-infor">
        <p class="title">Pin & công nghệ sạc</p>
        <table>
          <tr>
            <td>Pin</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.battery.battery || 'Không có' }}
              <span class="arrow">
                <img
                  v-if="extractNumericValue(phone.battery.battery) === maxValue('battery.battery')"
                  :src="upArrow"
                />
                <img v-else :src="downArrow" />
              </span>
            </td>
          </tr>
          <tr>
            <td>Công nghệ sạc</td>
            <td
              v-for="(phone, index) in phoneList"
              :key="index"
              v-html="phone.battery.charginTechnology || 'Không có'"
            ></td>
          </tr>
          <tr>
            <td>Cổng sạc</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.battery.port || 'Không có' }}
            </td>
          </tr>
        </table>
      </div>
      <!-- Thiết kế và trọng lượng -->
      <div class="detail-infor">
        <p class="title">Thiết kế và trọng lượng</p>
        <table>
          <tr>
            <td>Kích thước</td>
            <td
              v-for="(phone, index) in phoneList"
              :key="index"
              v-html="phone.design.size || 'Không có'"
            ></td>
          </tr>
          <tr>
            <td>Trọng lượng</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.design.weight || 'Không có' }}
              <span class="arrow">
                <img
                  v-if="extractNumericValue(phone.design.weight) === maxValue('design.weight')"
                  :src="upArrow"
                />
                <img v-else :src="downArrow" />
              </span>
            </td>
          </tr>
          <tr>
            <td>Chất liệu</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.design.material || 'Không có' }}
            </td>
          </tr>
        </table>
      </div>
      <!-- Thông số khác -->
      <div class="detail-infor">
        <p class="title">Thông số khác</p>
        <table>
          <tr>
            <td>Làm mát</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.otherInfor.cooler || 'Không có' }}
            </td>
          </tr>
          <tr>
            <td>Chỉ số kháng nước, bụi</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.otherInfor.resistanceIndex || 'Không có' }}
            </td>
          </tr>
          <tr>
            <td>Công nghệ tiện ích</td>
            <td
              v-for="(phone, index) in phoneList"
              :key="index"
              v-html="phone.otherInfor.tech || 'Không có'"
            ></td>
          </tr>
          <tr>
            <td>Công nghệ âm thanh</td>
            <td
              v-for="(phone, index) in phoneList"
              :key="index"
              v-html="phone.otherInfor.soundTech || 'Không có'"
            ></td>
          </tr>
          <tr>
            <td>Các loại tiện ích</td>
            <td
              v-for="(phone, index) in phoneList"
              :key="index"
              v-html="phone.otherInfor.utilities || 'Không có'"
            ></td>
          </tr>
          <tr>
            <td>Các loại cảm biến</td>
            <td v-for="(phone, index) in phoneList" :key="index">
              {{ phone.otherInfor.sensor || 'Không có' }}
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <n-modal v-model:show="showModal" style="margin-top: 64px; margin-bottom: 64px; overflow: hidden">
    <n-card
      style="width: 85%"
      title="Tìm kiếm điện thoại"
      :bordered="false"
      size="huge"
      role="dialog"
      aria-modal="true"
    >
      <SearchPhone />
    </n-card>
  </n-modal>
</template>

<style lang="scss" scoped>
.compare-wrap {
  padding: 48px 200px;
  .content-heading {
    justify-content: center;
    text-align: center;
    margin-bottom: 64px;
  }
  .phones-picker {
    display: flex;
    justify-content: space-evenly;
    .phone-picker {
      width: 200px;
      cursor: pointer;
      font-size: 18px;
      position: relative;
      .del-icon {
        position: absolute;
        right: -12px;
        top: -12px;
        width: 24px;
        height: 24px;
        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }
      .phone-wrap {
        display: flex;
        flex-direction: column;
        align-items: center;
        border: 2px solid $color-primary;
        border-radius: 16px;
        padding: 16px;
        min-height: 232px;
        .phone-img {
          width: 100%;
          img {
            width: 100%;
            height: 100%;
            object-fit: cover;
          }
        }
        p {
          max-width: 100%;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }
      }
    }
    .phone-empty {
      width: 200px;
      height: 230px;
      border: 2px dashed #787878;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 16px;
      padding: 16px;
      cursor: pointer;
      &:hover {
        border-color: $color-primary;
        color: $color-primary;
      }
    }
  }
  .phone-specifications {
    // margin: 32px 0 64px;
    margin: 64px 0;
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
            width: 26%;
            &:first-child {
              width: 24%;
              font-weight: 600;
            }
            &:not(:first-child) {
            }
            .arrow {
              position: relative;
              top: 2px;
              left: 4px;
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
}
</style>
