package com.rctoyshop.backend.repository;

import com.rctoyshop.backend.model.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 訂單明細 Repository (DAO) - 基於 Spring Data JDBC
 * 繼承 CrudRepository<Entity 類別, Primary Key 類型 (Integer)>
 */
@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
    
    // --- 根據您的欄位定義的自定義查詢方法範例 ---
    
    // 1. 根據 order_id 查找該訂單的所有明細
    // SQL: SELECT * FROM order_items WHERE order_id = ?
    List<OrderItem> findByOrderId(String orderId);

    // 2. 根據 product_id 查找所有購買過該商品的記錄 (用於銷售統計)
    // SQL: SELECT * FROM order_items WHERE product_id = ?
    List<OrderItem> findByProductId(String productId);
    
    // 3. 根據訂單ID和商品ID查找特定明細 (如果需要，雖然 id 已經是唯一主鍵)
    // SQL: SELECT * FROM order_items WHERE order_id = ? AND product_id = ?
    List<OrderItem> findByOrderIdAndProductId(String orderId, String productId);

    // 其他自動獲得的方法 (無需手動撰寫):
    // Optional<OrderItem> findById(Integer id);
    // <S extends OrderItem> S save(S entity);
}