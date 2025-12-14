package com.rctoyshop.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rctoyshop.backend.model.User;
import com.rctoyshop.backend.service.UserService;

@RestController
@RequestMapping("/api/auth") // 沿用 Auth 路徑，但需確保 AdminUser.vue 使用的 API 存在
public class UserController { 

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ... (保留 /register 和 /login 方法) ...

    /**
     * GET /api/auth/users : 獲取所有用戶 (AdminUser.vue: fetchUsers)
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        // 假設 UserService 中有 findAllUsers 方法
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * PUT /api/auth/users/{id} : 更新用戶資料/狀態 (AdminUser.vue: saveUser/toggleStatus)
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        // 假設 UserService 中有 updateUser 方法
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * DELETE /api/auth/users/{id} : 刪除用戶 (AdminUser.vue: deleteUser)
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        // 假設 UserService 中有 deleteUser 方法
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}