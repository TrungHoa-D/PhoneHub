package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.RoleConstant;
import com.example.projectbase.domain.dto.request.UserCreateDto;
import com.example.projectbase.domain.dto.request.UserUpdateDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.UserDto;
import com.example.projectbase.domain.entity.Role;
import com.example.projectbase.domain.entity.User;
import com.example.projectbase.domain.mapper.UserMapper;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.exception.UsernameExistedException;
import com.example.projectbase.repository.RoleRepository;
import com.example.projectbase.repository.UserRepository;
import com.example.projectbase.security.UserPrincipal;
import com.example.projectbase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getCurrentUser(UserPrincipal principal) {
        User user = userRepository.getUser(principal);
        return userMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toUserDtos(userRepository.findAll());
    }

    @Override
    public UserDto createUser(UserCreateDto request) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null) {
            User newUser = userMapper.toUser(request);
            Role userRole = roleRepository.findByRoleName(RoleConstant.USER);
            newUser.setRole(userRole);
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            return userMapper.toUserDto(userRepository.save(newUser));
        } else throw new UsernameExistedException();
    }

    @Override
    public UserDto updateUser(UserUpdateDto request, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public CommonResponseDto deleteUser(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return new CommonResponseDto(true, "User deleted successfully");
        } else throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId});
    }

}
