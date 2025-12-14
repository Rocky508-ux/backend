package com.rctoyshop.backend.controller;

import com.rctoyshop.backend.dto.LoginRequest; // 確保這個 DTO 存在且路徑正確
import com.rctoyshop.backend.model.User;
import com.rctoyshop.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") 
public class AuthController {
    
    // 📢 修正點 1：宣告 UserService 變數
    private final UserService userService; 

    // 📢 修正點 2：實作建構子注入
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 註冊 API (如果註冊成功，請保留此方法)
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // ... (您的註冊邏輯)
        return new ResponseEntity<>(userService.registerNewUser(user), HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        
        // ✅ 修正後，這行程式碼現在可以呼叫被注入的 userService 實例了
        User authenticatedUser = userService.authenticate(
            loginRequest.getEmail(), 
            loginRequest.getPassword()
        );

        if (authenticatedUser != null) {
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK); 
        } else {
            // 登入失敗：Email 或密碼錯誤
            return new ResponseEntity<>("Invalid credentials (Email or password error).", HttpStatus.UNAUTHORIZED); // HTTP 401
        }
    }
}