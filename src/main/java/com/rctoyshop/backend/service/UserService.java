package com.rctoyshop.backend.service;

import com.rctoyshop.backend.model.User;
import com.rctoyshop.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List; // 📢 新增：用於 findAllUsers
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder; // 📢 新增 Import

/**
 * 使用者服務：處理用戶註冊、登入、以及管理員 CRUD 邏輯
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    // Removed PasswordEncoder

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 獲取所有用戶列表 (AdminUser.vue: fetchUsers)
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Integer id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setStatus(updatedUser.getStatus());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                // Plain text update
                user.setPassword(updatedUser.getPassword());
            }

            return userRepository.save(user);
        }
        return null;
    }

    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User registerNewUser(User user) {
        // 簡單驗證 Email 是否重複
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already in use");
        }

        // Plain text password (No operation needed)

        // 設置預設值
        user.setRole("USER");
        user.setCreatedAt(java.time.LocalDateTime.now());

        return userRepository.save(user);
    }

    public User authenticate(String email, String rawPassword) {
        // Debug logs...
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }

        // Plain text comparison
        if (rawPassword.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * 根據 ID 查找使用者
     */
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    /**
     * 根據 Email 查找使用者 (SecurityContext 用)
     */
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}