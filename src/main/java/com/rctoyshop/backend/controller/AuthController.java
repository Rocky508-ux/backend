package com.rctoyshop.backend.controller;

import com.rctoyshop.backend.dto.LoginRequest;
import com.rctoyshop.backend.model.User;
import com.rctoyshop.backend.security.JwtUtil;
import com.rctoyshop.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.registerNewUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. 交給 AuthenticationManager 驗證身分 (會呼叫 CustomUserDetailsService)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            // 2. 驗證成功，取得 UserDetails
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 3. 產生 JWT Token
            String token = jwtUtil.generateToken(userDetails);

            // 4. 取得完整 User 資訊 (回傳給前端顯示用)
            User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword()); // 這裡稍微冗餘但為了拿完整物件方便

            // 5. 建構回傳 Map
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user); // 前端需要 role, name, id 等資訊

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }
}