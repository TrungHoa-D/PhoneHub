<script setup>
import {
  NDataTable,
  NButton,
  useMessage,
  useDialog,
  NModal,
  NCard,
  NInput,
  NInputGroup,
  NImage
} from 'naive-ui';
import { h, ref, computed, reactive, onMounted } from 'vue';

import DetailPhone from '@/components/admin/DetailPhone.vue';
import EditPhone from '@/components/admin/EditPhone.vue';
import AddPhone from '@/components/admin/AddNewPhone.vue';
import TotalPhoneByPhone from '@/components/admin/TotalPhoneByBrand.vue';

import { usePhoneStore } from '@/stores/phoneStore';

import editIcon from '@assets/icons/edit.svg';
import delIcon from '@assets/icons/del.svg';
import eyeIcon from '@assets/icons/eye.svg';

const dialog = useDialog();
const message = useMessage();
const phoneStore = usePhoneStore();

const phoneList = ref([]);
const brandList = ref([]);
const phoneBrand = ref();

const paginationReactive = reactive({
  page: 1,
  pageSize: 10,
  showSizePicker: true,
  pageSizes: [10, 20, 30],
  onChange: (page) => {
    paginationReactive.page = page;
  },
  onUpdatePageSize: (pageSize) => {
    paginationReactive.pageSize = pageSize;
    paginationReactive.page = 1;
  }
});

const phoneColumns = [
  {
    type: 'selection',
    options: ['all', 'none']
  },
  {
    title: 'STT',
    key: 'order',
    render: (_, index) => {
      return index + 1;
    }
  },
  {
    title: 'Tên',
    key: 'name',
    sorter: 'default'
  },
  {
    title: 'Hình ảnh',
    key: 'image',
    render(value) {
      return h('div', { class: 'phone-image-wrap' }, [
        h(NImage, {
          src: value.img.includes('http')
            ? `${value.img}`
            : `${import.meta.env.VITE_CELLPHONES_URL}/${value.img}`,
          class: 'phone-image'
        })
      ]);
    }
  },
  {
    title: 'Hãng',
    key: 'brand',
    sorter: 'default'
    // filterOptions: [
    //   {
    //     label: 'Tất cả',
    //     value: 'all'
    //   }
    // ],
    // filter(value, row) {
    //   return !!~row.brand.indexOf(value.toString());
    // }
  },
  {
    title: 'Giá',
    key: 'cost',
    // filterOptions: [
    //   {
    //     label: 'Not price yet',
    //     value: 'Not price yet'
    //   },
    //   {
    //     label: 'Has price',
    //     value: 'Has price'
    //   }
    // ],
    // filter(value, row) {
    //   if (value.toString() === '-1') {
    //     return !!~row.cost.indexOf(value.toString());
    //   } else {
    //     return !~row.cost.indexOf('Not price yet');
    //   }
    // },
    sorter(rowA, rowB) {
      return rowA.cost - rowB.cost;
    }
  },
  {
    title: 'Tác vụ',
    key: 'action',
    width: '200px',
    render(row) {
      return h('div', [
        h(
          NButton,
          {
            onClick: () => viewInforPhone(row),
            type: 'primary'
          },
          {
            icon: () => h('img', { title: 'Phone Infor', src: eyeIcon, alt: 'Edit', class: 'icon' })
          }
        ),
        h(
          NButton,
          {
            onClick: () => editPhone(row),
            style: { marginLeft: '8px' },
            type: 'warning'
          },
          {
            icon: () => h('img', { title: 'Edit Phone', src: editIcon, alt: 'Edit', class: 'icon' })
          }
        ),
        h(
          NButton,
          {
            onClick: () => deletePhoneHandler(row),
            style: { marginLeft: '8px' },
            type: 'error'
          },
          {
            default: () =>
              h('img', { title: 'Delete Phone', src: delIcon, alt: 'Close', class: 'icon' })
          }
        )
      ]);
    }
  }
];

const searchValue = ref('');
const filteredData = computed(() => {
  const query = searchValue.value.toLowerCase();
  if (!query) {
    return phoneList.value;
  } else {
    return phoneList.value.filter((phone) => phone.name.toLowerCase().includes(query));
  }
});

const checkedRowKeysRef = ref([]);

const rowKey = (row) => {
  return row.id;
};

const handleCheck = (rowKeys) => {
  checkedRowKeysRef.value = rowKeys;
};

const detailPhone = ref({});
const showDetailPhone = ref(false);
const viewInforPhone = async (row) => {
  await phoneStore.getDetailPhoneById(row.id);
  detailPhone.value = phoneStore.detailPhone;
  showDetailPhone.value = true;
};

const showEditPhone = ref(false);
const editPhone = async (row) => {
  await phoneStore.getDetailPhoneById(row.id);
  detailPhone.value = phoneStore.detailPhone;
  showEditPhone.value = true;
};

const closeEditPhoneHandler = () => {
  showEditPhone.value = false;
};

const closeEditPhoneSuccessHandler = async () => {
  fetchData();
  showEditPhone.value = false;
};

const showAddPhone = ref(false);
const showPhone = () => {
  showAddPhone.value = true;
};

const closeAddPhoneHandler = () => {
  showAddPhone.value = false;
};

const closeAddPhoneSuccessHandler = () => {
  fetchData();
  showAddPhone.value = false;
};

const deletePhoneHandler = async (row) => {
  dialog.warning({
    title: 'Delete phone',
    content: `Do you want to delete this phone?`,
    positiveText: 'Yes',
    negativeText: 'Cancel',
    onPositiveClick: async () => {
      try {
        const res = await phoneStore.deletePhone(row.id);

        if (res) {
          fetchData();
          message.success('Delete phone successfully');
        }
      } catch (error) {
        message.error('Delete phone fail');
      }
    },
    onNegativeClick: () => {
      message.info('Cancel delete phone');
    }
  });
};

const deleteManyPhoneHandler = () => {
  if (checkedRowKeysRef.value.length == 0) message.info('Please select one phone to delete!');
  else {
    dialog.warning({
      title: 'Delete phones',
      content: `Do you want to delete this phones?`,
      positiveText: 'Yes',
      negativeText: 'Cancel',
      onPositiveClick: async () => {
        message.success('Delete phones successfully');
        checkedRowKeysRef.value.forEach(async (key) => {
          try {
            const res = await phoneStore.deletePhone(key);

            if (res) {
              fetchData();
              message.success('Delete phone successfully');
            }
          } catch (error) {
            message.error('Delete phone fail');
          }
        });
      },
      onNegativeClick: () => {
        message.info('Cancel delete phones');
      }
    });
  }
};

const fetchData = async () => {
  // Cập nhật dữ liệu bảng
  await phoneStore.getPhone();
  phoneList.value = phoneStore.allPhones;

  await phoneStore.getAllBrand();
  brandList.value = phoneStore.allBrands;
  const brandColumn = phoneColumns.find((col) => col.key === 'brand');
  brandColumn.filterOptions = brandList.value;
};

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="phone-list-wrap">
    <div class="phone-list-header">
      <n-input-group>
        <n-input
          v-model:value="searchValue"
          type="text"
          placeholder="Tìm kiếm ở đây..."
          style="width: 400px; margin-bottom: 16px; margin-right: 16px"
        />
        <n-button @click="deleteManyPhoneHandler" type="error">Xóa nhiều</n-button>
      </n-input-group>
      <n-button @click="showPhone" type="primary">Thêm điện thoại</n-button>
    </div>
    <n-data-table
      v-model:checked-row-keys="checkedRowKeysRef"
      size="large"
      :columns="phoneColumns"
      :data="filteredData"
      :row-key="rowKey"
      :pagination="paginationReactive"
      @update:checked-row-keys="handleCheck"
      striped
    />
  </div>
  <n-modal v-model:show="showDetailPhone" style="margin-top: 50px; margin-bottom: 50px">
    <n-card style="width: 800px" :bordered="true" size="huge" role="dialog" aria-modal="true">
      <DetailPhone v-if="detailPhone" :detail-phone="detailPhone" />
    </n-card>
  </n-modal>
  <n-modal v-model:show="showEditPhone" style="margin-top: 50px; margin-bottom: 50px">
    <n-card style="width: 90%" :bordered="true" size="huge" role="dialog" aria-modal="true">
      <EditPhone
        :detail-phone="detailPhone"
        @close-edit-phone="closeEditPhoneHandler"
        @close-edit-phone-success="closeEditPhoneSuccessHandler"
      />
    </n-card>
  </n-modal>
  <n-modal v-model:show="showAddPhone" style="margin-top: 50px; margin-bottom: 50px">
    <n-card style="width: 90%" :bordered="true" size="huge" role="dialog" aria-modal="true">
      <AddPhone
        @close-add-phone="closeAddPhoneHandler"
        @close-add-phone-success="closeAddPhoneSuccessHandler"
      />
    </n-card>
  </n-modal>
</template>

<style lang="scss">
.title {
  font-size: 28px;
  font-weight: 700;
  margin-left: 0;
  margin-bottom: 16px;
}
.analyze-phone {
  display: flex;
  justify-content: space-between;
  padding: 32px;
  > div {
    width: 45%;
  }
  .total-phone-wrap {
    display: flex;
    gap: 32px;
    justify-content: center;
    align-items: center;
    .total-phone {
      border: 1px solid;
      border-radius: 16px;
      text-align: center;
      height: 150px;
      p.total-phone-title {
        padding: 16px;
        font-size: 28px;
        font-weight: 700;
        border-top-left-radius: 16px;
        border-top-right-radius: 16px;
        color: #fff;
        &.blue {
          background: rgba(20, 100, 244, 0.7);
        }
        &.yellow {
          background: rgba(219, 116, 5, 0.7);
        }
      }
      p:not(.total-phone-title) {
        font-size: 20px;
        margin: 10% auto;
      }
    }
  }
}

.phone-image-wrap {
  width: 100px;
  height: 100px;
  .phone-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}
.phone-list-header {
  display: flex;
}
.phone-pagination {
  display: flex;
  justify-content: center;
  margin-top: 16px;
}
</style>
