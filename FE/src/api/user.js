import { api } from '@/api/axios'; // Đường dẫn đến file Axios của bạn

export const getUsers = async () => {
  try {
    const response = await api.get('/admin/user');
    if (response.data.status === 'SUCCESS') {
      return response.data.data; // Mảng người dùng
    }
    throw new Error('API returned non-success status');
  } catch (error) {
    console.error('Error fetching users:', error);
    throw error;
  }
};

export const createUser = async (userData) => {
  try {
    const response = await api.post('/admin/user', userData);
    if (response.data.status === 'SUCCESS') {
      return response.data.data; // Người dùng mới
    }
    throw new Error('API returned non-success status');
  } catch (error) {
    console.error('Error creating user:', error);
    throw error;
  }
};

export const getUserById = async (userId) => {
  try {
    const response = await api.get(`/admin/user/${userId}`);
    if (response.data.status === 'SUCCESS') {
      return response.data.data; // Chi tiết người dùng
    }
    throw new Error('API returned non-success status');
  } catch (error) {
    console.error('Error fetching user by id:', error);
    throw error;
  }
};

export const updateUser = async (userId, userData) => {
  try {
    const response = await api.patch(`/admin/user/${userId}`, userData);
    if (response.data.status === 'SUCCESS') {
      return response.data.data;
    }
    throw new Error('API returned non-success status');
  } catch (error) {
    console.error('Error updating user:', error);
    throw error;
  }
};

export const deleteUser = async (userId) => {
  try {
    const response = await api.delete(`/admin/user/${userId}`);
    if (response.data.status === 'SUCCESS') {
      return response.data.data;
    }
    throw new Error('API returned non-success status');
  } catch (error) {
    console.error('Error deleting user:', error);
    throw error;
  }
};