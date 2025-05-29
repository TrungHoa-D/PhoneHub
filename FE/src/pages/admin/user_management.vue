<template>
  <div class="user-management-container">
    <h2>Quản lý tài khoản</h2>
    <n-space class="action-bar">
      <n-input v-model:value="searchQuery" placeholder="Tìm kiếm tài khoản..." />
      <n-button type="primary" @click="openAddUserModal">Thêm tài khoản</n-button>
    </n-space>
    <n-data-table :columns="columns" :data="filteredUsers" :pagination="pagination" />
    <n-modal v-model:show="showModal" :title="isEditMode ? 'Sửa tài khoản' : 'Thêm tài khoản'" preset="dialog">
      <n-form :model="newUser" :rules="rules" ref="formRef">
        <n-form-item label="Tên người dùng" path="username">
          <n-input v-model:value="newUser.username" />
        </n-form-item>
        <n-form-item label="Email" path="email">
          <n-input v-model:value="newUser.email" />
        </n-form-item>
        <n-form-item label="Mật khẩu" path="password" v-if="!isEditMode">
          <n-input v-model:value="newUser.password" type="password" />
        </n-form-item>
      </n-form>
      <template #footer>
        <n-button @click="showModal = false">Hủy</n-button>
        <n-button type="primary" @click="handleSaveUser">Lưu</n-button>
      </template>
    </n-modal>
  </div>
</template>

<script setup>
import { ref, computed, h } from 'vue';
import { NDataTable, NButton, NSpace, NInput, NModal, NForm, NFormItem, useMessage } from 'naive-ui';

const message = useMessage();
const formRef = ref(null);
const users = ref([
  { id: 1, username: 'admin1', email: 'admin1@example.com' },
  { id: 2, username: 'user1', email: 'user1@example.com' },
  { id: 3, username: 'user2', email: 'user2@example.com' },
]);
const searchQuery = ref('');
const showModal = ref(false);
const isEditMode = ref(false);
const newUser = ref({ id: null, username: '', email: '', password: '' });

// Kiểm tra dữ liệu
console.log('Users:', users.value);

const rules = {
  username: { required: true, message: 'Vui lòng nhập tên người dùng', trigger: 'blur' },
  email: { required: true, message: 'Vui lòng nhập email', trigger: 'blur' },
  password: { required: true, message: 'Vui lòng nhập mật khẩu', trigger: 'blur' },
};

// Tìm kiếm người dùng
const filteredUsers = computed(() => {
  const query = searchQuery.value.toLowerCase();
  const filtered = users.value.filter(
    (user) =>
      user.username.toLowerCase().includes(query) || user.email.toLowerCase().includes(query)
  );
  console.log('Filtered Users:', filtered); // Kiểm tra dữ liệu filtered
  return filtered;
});

// Cấu hình bảng
const columns = [
  { title: 'ID', key: 'id' },
  { title: 'Tên người dùng', key: 'username' },
  { title: 'Email', key: 'email' },
  {
    title: 'Hành động',
    key: 'actions', // Đổi key thành 'actions' để tránh xung đột
    render(row) {
      return h('div', [
        h(NButton, { size: 'small', type: 'primary', onClick: () => editUser(row) }, 'Sửa'),
        h(NButton, { size: 'small', type: 'error', style: 'margin-left: 8px', onClick: () => deleteUser(row.id) }, 'Xóa'),
      ]);
    },
  },
];

const pagination = { pageSize: 10 };

// Mở modal thêm người dùng
const openAddUserModal = () => {
  isEditMode.value = false;
  newUser.value = { id: null, username: '', email: '', password: '' };
  showModal.value = true;
};

// Mở modal sửa người dùng
const editUser = (user) => {
  isEditMode.value = true;
  newUser.value = { ...user, password: '' };
  showModal.value = true;
};

// Xóa người dùng (dữ liệu tĩnh)
const deleteUser = (id) => {
  users.value = users.value.filter((user) => user.id !== id);
  message.success('Xóa tài khoản thành công');
};

// Lưu người dùng (dữ liệu tĩnh)
const handleSaveUser = () => {
  formRef.value?.validate((errors) => {
    if (!errors) {
      if (isEditMode.value) {
        const index = users.value.findIndex((user) => user.id === newUser.value.id);
        if (index !== -1) {
          users.value[index] = { ...newUser.value, id: newUser.value.id };
          message.success('Cập nhật tài khoản thành công');
        }
      } else {
        newUser.value.id = users.value.length + 1;
        users.value.push({ ...newUser.value });
        message.success('Thêm tài khoản thành công');
      }
      showModal.value = false;
    } else {
      message.error('Vui lòng kiểm tra các trường nhập liệu');
    }
  });
};
</script>

<style scoped>
.user-management-container {
  padding: 20px;
}

h2 {
  margin-bottom: 16px;
  font-size: 24px;
  font-weight: 600;
}

.action-bar {
  margin-bottom: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.action-bar .n-input {
  width: 300px;
}

.n-data-table {
  margin-top: 16px;
  display: block; /* Đảm bảo bảng hiển thị */
  width: 100%;
}
</style>