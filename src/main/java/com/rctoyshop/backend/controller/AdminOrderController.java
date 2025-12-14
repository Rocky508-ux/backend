package com.rctoyshop.backend.controller;

import com.rctoyshop.backend.model.Order;
import com.rctoyshop.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/orders") // Admin 專用路徑
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * GET /api/admin/orders : 獲取所有訂單 (AdminOrder.vue: fetchOrders)
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersForAdmin() {
        // 假設 OrderService 中有 findAllOrders 方法
        List<Order> orders = orderService.findAllOrders(); 
        return ResponseEntity.ok(orders);
    }

    /**
     * PUT /api/admin/orders/{id} : 更新訂單狀態 (AdminOrder.vue: updateStatus)
     * 請求體: { "status": "已出貨" }
     */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable String id, 
            @RequestBody Map<String, String> updateRequest) {
        
        String newStatus = updateRequest.get("status");
        if (newStatus == null) {
            return ResponseEntity.badRequest().build();
        }

        // 假設 OrderService 中有 updateOrderStatus 方法
        Optional<Order> updatedOrder = orderService.updateOrderStatus(id, newStatus);
        
        return updatedOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}