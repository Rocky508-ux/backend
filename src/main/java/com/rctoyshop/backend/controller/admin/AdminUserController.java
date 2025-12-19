package com.rctoyshop.backend.controller.admin;

import com.rctoyshop.backend.model.User;
import com.rctoyshop.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 專門處理管理者對用戶的操作 (Admin Panel)
 * Base Path: /api/admin/users
 */
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /api/admin/users : 獲取所有用戶
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println("[AdminUserController] Fetching all users...");
        try {
            List<User> users = userService.findAllUsers();
            System.out.println("[AdminUserController] Found " + users.size() + " users.");
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * POST /api/admin/users : 新增用戶
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User newUser = userService.registerNewUser(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/admin/users/{id} : 更新用戶
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * DELETE /api/admin/users/{id} : 刪除用戶
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        // 安全檢查：防止刪除自己 (可選)
        org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();

        System.out.println("Processing deleteUser request for ID: " + id);
        if (auth != null) {
            System.out.println("Auth Name: " + auth.getName());

            String currentEmail = auth.getName();
            // 注意：如果 Auth Name 是 "anonymousUser"，這裡會找不到 User
            User currentUser = userService.findUserByEmail(currentEmail);

            if (currentUser != null) {
                System.out.println("Current User ID: " + currentUser.getId());
                if (currentUser.getId().equals(id)) {
                    System.out.println("Attempt to delete self detected!");
                    return ResponseEntity.badRequest().body("無法刪除自己的帳號");
                }
            } else {
                System.out.println("Current User not found in DB (or anonymous).");
            }
        } else {
            System.out.println("Auth object is null.");
        }

        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
