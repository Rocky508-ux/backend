package com.rctoyshop.backend.repository;

import com.rctoyshop.backend.model.Order;
import org.springframework.data.repository.ListCrudRepository; // 📢 替換為 ListCrudRepository
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 訂單主表 Repository (DAO) - 基於 Spring Data JDBC
 * 繼承 ListCrudRepository<Entity 類別, Primary Key 類型 (String)>
 */
@Repository
public interface OrderRepository extends ListCrudRepository<Order, String> {

    // --- 支援 Admin User/Order 模組的關鍵方法 ---

    /**
     * 1. 根據 user_id 查找該用戶的所有訂單 (AdminUser.vue 購買紀錄)
     * SQL: SELECT * FROM orders WHERE user_id = ?
     */
    List<Order> findByUserId(Integer userId);

    // 2. 根據訂單狀態查找所有訂單
    // SQL: SELECT * FROM orders WHERE status = ?
    List<Order> findByStatus(String status);

    // 3. 查找總金額大於等於某值的訂單
    // SQL: SELECT * FROM orders WHERE total_amount >= ?
    List<Order> findByTotalAmountGreaterThanEqual(Integer totalAmount);

    // 4. (可選) 查找特定日期之後的訂單
    // List<Order> findByOrderDateAfter(LocalDateTime orderDate);

    // 5. 直接更新狀態 (避開 Persistable save() 的複雜性)
    @org.springframework.data.jdbc.repository.query.Modifying
    @org.springframework.data.jdbc.repository.query.Query("UPDATE orders SET status = :status WHERE id = :id")
    int updateAttributes(@org.springframework.data.repository.query.Param("id") String id,
            @org.springframework.data.repository.query.Param("status") String status);

    // ListCrudRepository 自動獲得的方法:
    // List<Order> findAll(); // 📢 用於 AdminOrder.vue: getAllOrdersForAdmin
    // Optional<Order> findById(String id);
    // <S extends Order> S save(S entity);
    // void deleteById(String id);
}