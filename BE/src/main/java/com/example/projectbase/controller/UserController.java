package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.request.UserCreateDto;
import com.example.projectbase.domain.dto.request.UserUpdateDto;
import com.example.projectbase.security.CurrentUser;
import com.example.projectbase.security.UserPrincipal;
import com.example.projectbase.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class UserController {

  private final UserService userService;

  @Tag(name = "user-controller-admin")
  @Operation(summary = "API get user")
  @GetMapping(UrlConstant.User.GET_USER)
  public ResponseEntity<?> getUserById(@PathVariable String userId) {
    return VsResponseUtil.success(userService.getUserById(userId));
  }

  @Tags({@Tag(name = "user-controller-admin"), @Tag(name = "user-controller")})
  @Operation(summary = "API get current user login")
  @GetMapping(UrlConstant.User.GET_CURRENT_USER)
  public ResponseEntity<?> getCurrentUser(@Parameter(name = "principal", hidden = true)
                                          @CurrentUser UserPrincipal principal) {
    return VsResponseUtil.success(userService.getCurrentUser(principal));
  }

  @Tag(name = "user-controller-admin")
  @Operation(summary = "API get all user")
  @GetMapping(UrlConstant.User.GET_USERS)
  public ResponseEntity<?> getCustomers() {
    return VsResponseUtil.success(userService.getAllUsers());
  }

  @Tag(name = "user-controller-admin")
  @Operation(summary = "API create new user")
  @PostMapping(UrlConstant.User.CREATE_USER)
  public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
    return VsResponseUtil.success(userService.createUser(userCreateDto));
  }

  @Tag(name = "user-controller-admin")
  @Operation(summary = "API update user")
  @PatchMapping(UrlConstant.User.UPDATE_USER)
  public ResponseEntity<?> updateUser(@PathVariable String userId,@Valid @RequestBody UserUpdateDto userCreateDto) {
    return VsResponseUtil.success(userService.updateUser(userCreateDto, userId));
  }

  @Tag(name = "user-controller-admin")
  @Operation(summary = "API delete user")
  @DeleteMapping(UrlConstant.User.DELETE_USER)
  public ResponseEntity<?> deleteUser(@PathVariable String userId) {
    return VsResponseUtil.success(userService.deleteUser(userId));
  }

}
