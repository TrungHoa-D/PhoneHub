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
        <n-form-item label="Họ" path="firstName">
          <n-input v-model:value="newUser.firstName" />
        </n-form-item>
        <n-form-item label="Tên" path="lastName">
          <n-input v-model:value="newUser.lastName" />
        </n-form-item>
        <n-form-item label="Username" path="username" v-if="!isEditMode">
          <n-input v-model:value="newUser.username" />
        </n-form-item>
        <n-form-item label="Mật khẩu" path="password" v-if="!isEditMode">
          <n-input v-model:value="newUser.password" type="password" />
        </n-form-item>
      </n-form>
      <div class="modal-footer">
        <n-button @click="showModal = false">Hủy</n-button>
        <n-button type="primary" @click="handleSaveUser">
          {{ isEditMode ? 'Sửa' : 'Thêm' }}
        </n-button>
      </div>
    </n-modal>
  </div>
</template>

<script setup>
import { ref, computed, h, onMounted } from 'vue';
import { NDataTable, NButton, NSpace, NInput, NModal, NForm, NFormItem, useMessage } from 'naive-ui';
import { getUsers, createUser, getUserById, updateUser, deleteUser } from '@/api/user';

const message = useMessage();
const formRef = ref(null);
const users = ref([]);
const searchQuery = ref('');
const showModal = ref(false);
const isEditMode = ref(false);
const newUser = ref({ id: null, firstName: '', lastName: '', username: '', roleName: 'ROLE_USER', password: '' });

onMounted(async () => {
  await fetchUsers();
});

const fetchUsers = async () => {
  try {
    const data = await getUsers();
    users.value = data.map(user => ({
      id: user.id,
      firstName: user.firstName,
      lastName: user.lastName,
      username: user.username,
      roleName: user.roleName,
    }));
    console.log('Fetched users:', users.value);
  } catch (error) {
    message.error('Lỗi khi tải danh sách tài khoản');
    console.error('Error:', error);
  }
};

const rules = {
  firstName: { required: true, message: 'Vui lòng nhập Họ', trigger: 'blur' },
  lastName: { required: true, message: 'Vui lòng nhập Tên', trigger: 'blur' },
  username: [
    { required: true, message: 'Vui lòng nhập Username', trigger: 'blur' },
    {
      validator: (rule, value) => {
        return !users.value.some(user => user.username === value && user.id !== newUser.value.id);
      },
      message: 'Username đã tồn tại',
      trigger: 'blur',
    },
  ],
  password: [
    { required: true, message: 'Vui lòng nhập Mật khẩu', trigger: 'blur' },
    {
      validator: (rule, value) => {
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        return passwordRegex.test(value);
      },
      message: 'Mật khẩu phải có ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt',
      trigger: 'blur',
    },
  ],
};

const filteredUsers = computed(() => {
  const query = searchQuery.value.toLowerCase();
  return users.value.filter(
    (user) =>
      user.firstName.toLowerCase().includes(query) ||
      user.lastName.toLowerCase().includes(query) ||
      user.username.toLowerCase().includes(query) ||
      user.roleName.toLowerCase().includes(query)
  );
});

const columns = [
  { title: 'ID', key: 'id' },
  { title: 'Họ', key: 'firstName' },
  { title: 'Tên', key: 'lastName' },
  { title: 'Username', key: 'username' },
  { title: 'Vai trò', key: 'roleName', render(row) {
    return row.roleName === 'ROLE_ADMIN' ? 'Admin' : 'User';
  }},
  {
    title: 'Hành động',
    key: 'actions',
    render(row) {
      return h('div', [
        h(NButton, { size: 'small', type: 'primary', onClick: () => editUser(row.id) }, 'Sửa'),
        h(NButton, { size: 'small', type: 'error', style: 'margin-left: 8px', onClick: () => deleteUserApi(row.id) }, 'Xóa'),
      ]);
    },
  },
];

const pagination = { pageSize: 10 };

const openAddUserModal = () => {
  console.log('Opening Add User Modal, isEditMode:', isEditMode.value);
  isEditMode.value = false;
  newUser.value = { id: null, firstName: '', lastName: '', username: '', roleName: 'ROLE_USER', password: '' };
  showModal.value = true;
};

const editUser = async (userId) => {
  console.log('Opening Edit User Modal, isEditMode:', isEditMode.value);
  isEditMode.value = true;
  try {
    const user = await getUserById(userId);
    newUser.value = { ...user, password: '' };
    showModal.value = true;
  } catch (error) {
    message.error('Lỗi khi tải thông tin người dùng');
    console.error('Error:', error);
  }
};

const deleteUserApi = async (userId) => {
  const adminCount = users.value.filter(user => user.roleName === 'ROLE_ADMIN').length;
  if (adminCount === 1 && users.value.find(user => user.id === userId).roleName === 'ROLE_ADMIN') {
    message.error('Không thể xóa tài khoản admin cuối cùng!');
    return;
  }
  try {
    await deleteUser(userId);
    message.success('Xóa tài khoản thành công');
    await fetchUsers();
  } catch (error) {
    message.error('Lỗi khi xóa tài khoản');
    console.error('Error:', error);
  }
};

const handleSaveUser = async () => {
  console.log('handleSaveUser called, isEditMode:', isEditMode.value);
  formRef.value?.validate(async (errors) => {
    if (!errors) {
      const userData = { firstName: newUser.value.firstName, lastName: newUser.value.lastName };
      if (!isEditMode.value) {
        userData.username = newUser.value.username;
        userData.roleName = 'ROLE_USER'; // Mặc định là ROLE_USER khi thêm
        userData.password = newUser.value.password;
      }
      try {
        if (isEditMode.value) {
          await updateUser(newUser.value.id, userData);
          message.success('Cập nhật tài khoản thành công');
        } else {
          await createUser(userData);
          message.success('Thêm tài khoản thành công');
        }
        showModal.value = false;
        await fetchUsers();
      } catch (error) {
        if (error.response && error.response.status === 400) {
          message.error(error.response.data.message || 'Lỗi khi lưu tài khoản');
        } else {
          message.error('Lỗi khi lưu tài khoản');
        }
        console.error('Error:', error);
      }
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
  display: block;
  width: 100%;
}

.n-button {
  display: inline-block !important;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  padding: 10px;
  border-top: 1px solid #eee;
}
</style>