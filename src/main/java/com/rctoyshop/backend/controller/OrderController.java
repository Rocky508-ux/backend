package com.rctoyshop.backend.controller;

import com.rctoyshop.backend.model.Order;
import com.rctoyshop.backend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * POST /api/orders : 建立新訂單 (結帳)
     * 請求體包含 Order 物件及其 OrderItems 清單。
     * 在真實專案中，用戶 ID 應從 JWT Token 中取得，而不是從請求體中傳入。
     */
    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        try {
            // Service 處理核心交易邏輯：儲存訂單、儲存明細、扣除庫存
            Order placedOrder = orderService.placeOrder(order);

            // HTTP 201 CREATED
            return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);

        } catch (IllegalStateException e) {
            // 處理庫存不足等業務異常
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 處理其他失敗情況
            return new ResponseEntity<>("Order placement failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/orders/user/{userId} : 查詢某用戶的歷史訂單
     * (同樣，在真實專案中，userId 應從認證上下文取得)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Integer userId) {
        List<Order> orders = orderService.findOrdersByUserId(userId);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Admin methods moved to AdminOrderController
}