package com.rctoyshop.backend.service;

import com.rctoyshop.backend.model.User;
import com.rctoyshop.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List; // 📢 新增：用於 findAllUsers
import java.util.Optional;

/**
 * 使用者服務：處理用戶註冊、登入、以及管理員 CRUD 邏輯
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ----------------------------------------------------
    // 📢 AdminUser.vue 必備功能
    // ----------------------------------------------------

    /**
     * 獲取所有用戶列表 (AdminUser.vue: fetchUsers)
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 更新用戶資料/狀態 (AdminUser.vue: saveUser, toggleStatus)
     */
    @Transactional
    public User updateUser(Integer id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            // 由於沒有加密，這裡直接更新欄位
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setStatus(updatedUser.getStatus()); // 支援停用/啟用

            // 僅在有傳入新密碼時才更新
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(updatedUser.getPassword());
            }

            return userRepository.save(user);
        }
        return null; // 如果用戶不存在，返回 null
    }

    /**
     * 刪除用戶 (AdminUser.vue: deleteUser)
     */
    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // ----------------------------------------------------
    // 基礎功能
    // ----------------------------------------------------

    /**
     * 處理使用者註冊邏輯
     */
    @Transactional
    public User registerNewUser(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email address is already in use.");
        }

        user.setCreatedAt(LocalDateTime.now());
        user.setStatus("active"); // 📢 預設狀態為啟用

        return userRepository.save(user);
    }

    /**
     * 驗證使用者憑證 (登入)
     */
    public User authenticate(String email, String rawPassword) {
        System.out.println(">>> UserService.authenticate called for: " + email);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            System.out.println(">>> User NOT FOUND for email: " + email);
            return null;
        }

        System.out.println(">>> User Found: " + user.getName() + ", Role: " + user.getRole());
        System.out.println(">>> DB Password: [" + user.getPassword() + "]");
        System.out.println(">>> Input Password: [" + rawPassword + "]");

        if (rawPassword.equals(user.getPassword())) {
            System.out.println(">>> Password MATCH! Login success.");
            return user;
        } else {
            System.out.println(">>> Password MISMATCH!");
        }
        return null;
    }

    /**
     * 根據 ID 查找使用者
     */
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }
}