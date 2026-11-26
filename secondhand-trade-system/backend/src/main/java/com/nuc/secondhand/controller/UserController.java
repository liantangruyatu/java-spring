package com.nuc.secondhand.controller;

import com.nuc.secondhand.dto.ApiResponse;
import com.nuc.secondhand.dto.UserLoginDTO;
import com.nuc.secondhand.dto.UserRegisterDTO;
import com.nuc.secondhand.dto.UserResponseDTO;
import com.nuc.secondhand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000", "http://localhost:5173"})
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponseDTO> register(@Valid @RequestBody UserRegisterDTO registerDTO) {
        try {
            UserResponseDTO userResponseDTO = userService.register(registerDTO);
            return ApiResponse.success("注册成功", userResponseDTO);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @PostMapping("/login")
    public ApiResponse<UserResponseDTO> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        try {
            UserResponseDTO userResponseDTO = userService.login(loginDTO);
            return ApiResponse.success("登录成功", userResponseDTO);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDTO> getUserById(@PathVariable Integer userId) {
        try {
            UserResponseDTO userResponseDTO = userService.getUserById(userId);
            return ApiResponse.success(userResponseDTO);
        } catch (Exception e) {
            return ApiResponse.error(404, e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponseDTO> updateUser(@PathVariable Integer userId, 
                                                     @Valid @RequestBody UserRegisterDTO updateDTO) {
        try {
            UserResponseDTO userResponseDTO = userService.updateUser(userId, updateDTO);
            return ApiResponse.success("更新成功", userResponseDTO);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @GetMapping
    public ApiResponse<List<UserResponseDTO>> getAllUsers() {
        try {
            List<UserResponseDTO> users = userService.getAllUsers();
            return ApiResponse.success(users);
        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable Integer userId) {
        try {
            userService.deleteUser(userId);
            return ApiResponse.success("删除成功");
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }
}
