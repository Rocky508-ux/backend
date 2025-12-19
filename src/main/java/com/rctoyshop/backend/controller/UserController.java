package com.rctoyshop.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rctoyshop.backend.service.UserService;

@RestController
@RequestMapping("/api/users") // 恢復為一般 User API 路徑 (雖然目前可能沒用到)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 一般使用者的 API 方法 (例如查看個人資料) 可以放在這裡
    // 目前 Admin 相關功能已移至 admin.AdminUserController
}