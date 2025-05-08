<script setup>
import { NForm, NFormItem, NInput, NInputGroup, NImage, NButton, useMessage } from 'naive-ui';

import { usePhoneStore } from '@/stores/phoneStore';

import { ref, defineEmits, reactive } from 'vue';

const emits = defineEmits(['closeAddPhone', 'closeAddPhoneSuccess']);

const message = useMessage();

const phoneStore = usePhoneStore();

const phone = reactive({
  name: '',
  brand: '',
  releaseDate: '',
  cost: 0,
  img: '',
  color: '',
  screenRequestDto: {
    resolution: '',
    size: '',
    screen: '',
    features: '',
    scanFrequency: '',
    type: ''
  },
  cameraRequestDto: {
    mainCamera: '',
    selfieCamera: ''
  },
  processorRequestDto: {
    chipset: '',
    cpu: '',
    gpu: ''
  },
  connectionRequestDto: {
    mobile_nfc: '',
    sim: '',
    os: '',
    network: '',
    wlan: '',
    bluetooth: '',
    gps: ''
  },
  storageRequestDto: {
    ram: '',
    internalMemory: '',
    memoryCardSlot: ''
  },
  batteryRequestDto: {
    battery: '',
    charginTechnology: '',
    port: ''
  },
  designRequestDto: {
    size: '',
    weight: '',
    material: ''
  },
  otherInforRequestDto: {
    cooler: '',
    resistanceIndex: '',
    tech: '',
    soundTech: '',
    utilities: '',
    sensor: ''
  }
});

const closeAddPhoneHandler = () => {
  emits('closeAddPhone');
};

const closeAddPhoneSuccessHandler = () => {
  emits('closeAddPhoneSuccess');
};

function convertNullToString(obj) {
  for (const key in obj) {
    if (obj[key] === '') {
      obj[key] = 'Không có';
    } else if (typeof obj[key] === 'object' && obj[key] !== '') {
      convertNullToString(obj[key]); // Đệ quy xử lý các đối tượng con
    }
  }
  return obj;
}

const newPhone = ref({});
const addPhoneHandler = async () => {
  newPhone.value = convertNullToString({ ...phone });
  try {
    const res = await phoneStore.addNewPhone(newPhone.value);

    if (res.data.status === 'SUCCESS') {
      message.success('Thêm mới thành công!');
      closeAddPhoneSuccessHandler();
    }
  } catch (error) {
    message.error('Thêm mới thất bại!');
    closeAddPhoneHandler();
  }
};
</script>

<template>
  <p class="edit-phone-title">Thêm điện thoại</p>
  <n-form ref="formRef" :model="phone">
    <div class="edit-phone-input-wrap">
      <n-image class="edit-phone-input-wrap" width="200" :src="phone.img" v-model="phone.img" />
      <n-form-item path="img" label="Đường dẫn ảnh">
        <n-input v-model:value="phone.img" @keydown.enter.prevent />
      </n-form-item>
      <n-form-item path="name" label="Tên điện thoại">
        <n-input v-model:value="phone.name" @keydown.enter.prevent />
      </n-form-item>
      <n-form-item path="brand" label="Brand">
        <n-input v-model:value="phone.brand" type="text" @keydown.enter.prevent />
      </n-form-item>
      <n-form-item path="releaseDate" label="Ngày phát hành">
        <n-input v-model:value="phone.releaseDate" type="text" @keydown.enter.prevent />
      </n-form-item>
      <n-form-item path="cost" label="Giá tiền">
        <n-input v-model:value="phone.cost" type="text" @keydown.enter.prevent />
      </n-form-item>
    </div>
    <!-- Màn hình -->
    <div class="edit-phone-input-wrap">
      <p class="edit-phone-label">Màn hình</p>
      <div>
        <n-form-item path="size" label="Kích thước màn hình">
          <n-input v-model:value="phone.screenRequestDto.size" type="text" @keydown.enter.prevent />
        </n-form-item>
        <n-form-item path="screen" label="Công nghệ màn hình">
          <n-input
            v-model:value="phone.screenRequestDto.screen"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="resolution" label="Độ phân giải màn hình">
          <n-input
            v-model:value="phone.screenRequestDto.resolution"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="features" label="Tính năng màn hình">
          <n-input
            v-model:value="phone.screenRequestDto.features"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="scanFrequency" label="Tần số quét">
          <n-input
            v-model:value="phone.screenRequestDto.scanFrequency"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="type" label="Kiểu màn hình">
          <n-input v-model:value="phone.screenRequestDto.type" type="text" @keydown.enter.prevent />
        </n-form-item>
      </div>
    </div>
    <!-- Camera-->
    <div class="edit-phone-input-wrap">
      <p class="edit-phone-label">Camera</p>
      <div>
        <n-form-item path="mainCamera" label="Camera sau">
          <n-input
            v-model:value="phone.cameraRequestDto.mainCamera"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="selfieCamera" label="Camera trước">
          <n-input
            v-model:value="phone.cameraRequestDto.selfieCamera"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
      </div>
    </div>
    <!-- Vi xử lý & Đồ họa-->
    <div class="edit-phone-input-wrap">
      <p class="edit-phone-label">Vi xử lý & Đồ họa</p>
      <div>
        <n-form-item path="chipset" label="Chipset">
          <n-input
            v-model:value="phone.processorRequestDto.chipset"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="gpu" label="GPU">
          <n-input
            v-model:value="phone.processorRequestDto.gpu"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="cpu" label="CPU">
          <n-input
            v-model:value="phone.processorRequestDto.cpu"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
      </div>
    </div>
    <!-- Giao tiếp & Kết nối-->
    <div class="edit-phone-input-wrap">
      <p class="edit-phone-label">Giao tiếp & Kết nối</p>
      <div>
        <n-form-item path="mobile_nfc" label="Công nghệ NFC">
          <n-input
            v-model:value="phone.connectionRequestDto.mobile_nfc"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="sim" label="Thẻ SIM">
          <n-input
            v-model:value="phone.connectionRequestDto.sim"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="os" label="Hệ điều hành">
          <n-input
            v-model:value="phone.connectionRequestDto.os"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="wlan" label="WLAN">
          <n-input
            v-model:value="phone.connectionRequestDto.wlan"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="network" label="Hỗ trợ mạng">
          <n-input
            v-model:value="phone.connectionRequestDto.network"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="bluetooth" label="Hỗ trợ Bluetooth">
          <n-input
            v-model:value="phone.connectionRequestDto.bluetooth"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="gps" label="GPS">
          <n-input
            v-model:value="phone.connectionRequestDto.gps"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
      </div>
    </div>
    <!-- Lưu trữ -->
    <div class="edit-phone-input-wrap">
      <p class="edit-phone-label">Lưu trữ</p>
      <div>
        <n-form-item path="ram" label="RAM">
          <n-input v-model:value="phone.storageRequestDto.ram" type="text" @keydown.enter.prevent />
        </n-form-item>
        <n-form-item path="internalMemory" label="Bộ nhớ trong">
          <n-input
            v-model:value="phone.storageRequestDto.internalMemory"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="memoryCardSlot" label="Khe cắm thẻ nhớ">
          <n-input
            v-model:value="phone.storageRequestDto.memoryCardSlot"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
      </div>
    </div>
    <!-- Pin & Công nghệ sạc -->
    <div class="edit-phone-input-wrap">
      <p class="edit-phone-label">Pin & Công nghệ sạc</p>
      <div>
        <n-form-item path="battery" label="Pin">
          <n-input
            v-model:value="phone.batteryRequestDto.battery"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="charginTechnology" label="Công nghệ sạc">
          <n-input
            v-model:value="phone.batteryRequestDto.charginTechnology"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="port" label="Cổng sạc">
          <n-input
            v-model:value="phone.batteryRequestDto.port"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
      </div>
    </div>
    <!-- Thiết kế & Trọng lượng -->
    <div class="edit-phone-input-wrap">
      <p class="edit-phone-label">Thiết kế & Trọng lượng</p>
      <div>
        <n-form-item path="size" label="Kích thước">
          <n-input v-model:value="phone.designRequestDto.size" type="text" @keydown.enter.prevent />
        </n-form-item>
        <n-form-item path="weight" label="Trọng lượng">
          <n-input
            v-model:value="phone.designRequestDto.weight"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="material" label="Chất liệu">
          <n-input
            v-model:value="phone.designRequestDto.material"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
      </div>
    </div>
    <!-- Thông số khác -->
    <div class="edit-phone-input-wrap">
      <p class="edit-phone-label">Thông số khác</p>
      <div>
        <n-form-item path="cooler" label="Làm mát">
          <n-input
            v-model:value="phone.otherInforRequestDto.cooler"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="resistanceIndex" label="Chỉ số kháng nước">
          <n-input
            v-model:value="phone.otherInforRequestDto.resistanceIndex"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="tech" label="Công nghệ tiện ích">
          <n-input
            v-model:value="phone.otherInforRequestDto.tech"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="soundTech" label="Công nghệ âm thanh">
          <n-input
            v-model:value="phone.otherInforRequestDto.soundTech"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="utilities" label="Các loại tiện ích">
          <n-input
            v-model:value="phone.otherInforRequestDto.utilities"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
        <n-form-item path="sensor" label="Các loại cảm biến">
          <n-input
            v-model:value="phone.otherInforRequestDto.sensor"
            type="text"
            @keydown.enter.prevent
          />
        </n-form-item>
      </div>
    </div>
    <div class="edit-phone-footer">
      <n-button @click="addPhoneHandler">Thêm</n-button>
      <n-button @click="closeAddPhoneHandler">Hủy</n-button>
    </div>
  </n-form>
</template>

<style lang="scss" scoped>
.edit-phone-title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 16px;
}
.edit-phone-input-wrap {
  border: 1px solid;
  padding: 8px;
  &:not(:last-child) {
    margin-bottom: 16px;
  }
  .edit-phone-label {
    font-size: 20px;
    font-weight: 700;
    margin-bottom: 8px;
  }
  > div {
    margin-left: 8px;
  }
}
.edit-phone-footer {
  display: flex;
  gap: 16px;
  justify-content: center;
}
</style>
