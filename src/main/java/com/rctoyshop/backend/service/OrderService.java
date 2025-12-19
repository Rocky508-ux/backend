package com.rctoyshop.backend.service;

import com.rctoyshop.backend.model.Order;
import com.rctoyshop.backend.model.OrderItem;
import com.rctoyshop.backend.model.Product;
import com.rctoyshop.backend.repository.OrderRepository;
import com.rctoyshop.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    /**
     * 處理結帳與下單的核心業務邏輯
     */
    /**
     * 處理結帳與下單的核心業務邏輯
     */
    @Transactional
    public Order placeOrder(Order order) {

        // 0. 補上後端生成的時間
        if (order.getOrderDate() == null) {
            order.setOrderDate(java.time.LocalDateTime.now());
        }

        // 1. 處理庫存扣除 (業務檢查)
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {

                // 檢查商品存在與庫存
                Product product = productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new IllegalStateException("商品 " + item.getProductId() + " 不存在"));

                if (product.getStock() < item.getQuantity()) {
                    throw new IllegalStateException("商品 " + product.getName() + " 庫存不足。");
                }

                // 扣除庫存
                product.setStock(product.getStock() - item.getQuantity());
                productRepository.save(product);
            }

            // 1.5 計算訂單總件數
            int totalItems = order.getItems().stream().mapToInt(OrderItem::getQuantity).sum();
            order.setTotalItems(totalItems);
        }

        // 2. 儲存 Order 主表 (因為是 Aggregate Root，它會自動 Cascade Save 所有的 Items)
        // 由於我們在 Order Class 實作了 Persistable 並且 isNew = true，
        // 這裡會強制執行 INSERT INTO orders ... 接著 INSERT INTO order_items ...
        return orderRepository.save(order);
    }

    /**
     * 根據使用者 ID 查詢歷史訂單 (支援 AdminUser.vue)
     */
    public List<Order> findOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId); // 📢 修正：移除多餘的強制轉型
    }

    /**
     * 獲取所有訂單 (支援 AdminOrder.vue)
     */
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * 更新訂單狀態 (支援 AdminOrder.vue)
     */
    @Transactional
    public Optional<Order> updateOrderStatus(String id, String newStatus) {
        // 使用直接更新 SQL，避免載入整個 Aggregate Root 導致的 Persistable 問題
        int rows = orderRepository.updateAttributes(id, newStatus);

        if (rows > 0) {
            // 更新成功後，重新查詢以返回最新狀態 (雖非必要，但保持 API 行為一致)
            return orderRepository.findById(id);
        }
        return Optional.empty();
    }

    // ... 這裡可以添加 findOrderById() 等方法
}