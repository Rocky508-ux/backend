package com.rctoyshop.backend.controller.admin;

import com.rctoyshop.backend.model.Order;
import com.rctoyshop.backend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AdminOrderController.class);

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * GET /api/admin/orders : 獲取所有訂單
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.findAllOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all orders: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT /api/admin/orders/{id} : 修改訂單狀態
     */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String id,
            @RequestBody java.util.Map<String, String> payload) {
        try {
            String newStatus = payload.get("status");
            if (newStatus == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return orderService.updateOrderStatus(id, newStatus)
                    .map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error updating order status for id {}: ", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
