package com.rctoyshop.backend.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rctoyshop.backend.model.User;
import com.rctoyshop.backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 在我們的系統中，username 就是 email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // 檢查用戶狀態：如果不是 "active" 或 "ACTIVE"，則禁止登入
        String status = user.getStatus() != null ? user.getStatus().toLowerCase() : "active";
        if (!"active".equals(status)) {
            // 拋出異常，Spring Security 會捕捉並視為驗證失敗
            throw new UsernameNotFoundException("Account is disabled: " + email);
        }

        // 將我們的 User 轉換為 Spring Security 的 UserDetails
        // 注意：這裡假設 User 實體有 getRole() 返回 "ADMIN" 或 "USER"
        // Spring Security 通常需要 "ROLE_" 前綴，我們在這裡手動加上
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(), // 這裡應該是加密後的密碼
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }
}
