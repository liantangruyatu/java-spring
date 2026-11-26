package com.nuc.secondhand.service;

import com.nuc.secondhand.dto.UserLoginDTO;
import com.nuc.secondhand.dto.UserRegisterDTO;
import com.nuc.secondhand.dto.UserResponseDTO;
import com.nuc.secondhand.entity.User;
import com.nuc.secondhand.repository.UserRepository;
import com.nuc.secondhand.util.PasswordUtil;
import com.nuc.secondhand.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO register(UserRegisterDTO registerDTO) {
        // 检查用户名是否已存在
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new RuntimeException("用户名已存在");
        }
        // 检查邮箱是否已存在
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("邮箱已存在");
        }
        // 检查电话是否已存在
        if (userRepository.findByPhoneNumber(registerDTO.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("电话号码已存在");
        }

        User user = User.builder()
                .username(registerDTO.getUsername())
                .password(PasswordUtil.encryptPassword(registerDTO.getPassword()))
                .realName(registerDTO.getRealName())
                .gender(registerDTO.getGender())
                .department(registerDTO.getDepartment())
                .phoneNumber(registerDTO.getPhoneNumber())
                .email(registerDTO.getEmail())
                .address(registerDTO.getAddress())
                .userType(registerDTO.getUserType())
                .userStatus("正常")
                .createTime(LocalDateTime.now())
                .creditScore(100)
                .build();

        User savedUser = userRepository.save(user);
        return convertToResponseDTO(savedUser);
    }

    public UserResponseDTO login(UserLoginDTO loginDTO) {
        Optional<User> userOptional = userRepository.findByUsername(loginDTO.getUsername());
        if (!userOptional.isPresent()) {
            throw new RuntimeException("用户名或密码错误");
        }

        User user = userOptional.get();
        if (!PasswordUtil.matchPassword(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 更新最后登录时间
        user.setLastLoginTime(LocalDateTime.now());
        userRepository.save(user);

        return convertToResponseDTO(user);
    }

    public UserResponseDTO getUserById(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("用户不存在");
        }
        return convertToResponseDTO(userOptional.get());
    }

    public UserResponseDTO updateUser(Integer userId, UserRegisterDTO updateDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("用户不存在");
        }

        User user = userOptional.get();
        user.setRealName(updateDTO.getRealName());
        user.setGender(updateDTO.getGender());
        user.setDepartment(updateDTO.getDepartment());
        user.setAddress(updateDTO.getAddress());

        User updatedUser = userRepository.save(user);
        return convertToResponseDTO(updatedUser);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    private UserResponseDTO convertToResponseDTO(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .realName(user.getRealName())
                .gender(user.getGender())
                .department(user.getDepartment())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .address(user.getAddress())
                .userType(user.getUserType())
                .avatarUrl(user.getAvatarUrl())
                .creditScore(user.getCreditScore())
                .createTime(DateUtil.formatDateTime(user.getCreateTime()))
                .lastLoginTime(DateUtil.formatDateTime(user.getLastLoginTime()))
                .build();
    }
}
