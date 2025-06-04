package com.example.projectbase.service;

import com.example.projectbase.domain.dto.request.UserCreateDto;
import com.example.projectbase.domain.dto.request.UserUpdateDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.UserDto;
import com.example.projectbase.security.UserPrincipal;

import java.util.List;

public interface UserService {

  UserDto getUserById(String userId);

  UserDto getCurrentUser(UserPrincipal principal);

  List<UserDto> getAllUsers();

  UserDto createUser(UserCreateDto request);

  UserDto updateUser(UserUpdateDto request, String userId);

  CommonResponseDto deleteUser(String userId);

}
