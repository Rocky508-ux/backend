package com.rctoyshop.backend.repository;

import com.rctoyshop.backend.model.User;
import org.springframework.data.repository.ListCrudRepository; // 📢 替換為 ListCrudRepository
import org.springframework.stereotype.Repository;

/**
 * 使用者 Repository (DAO) - 基於 Spring Data JDBC
 * 繼承 ListCrudRepository<Entity 類別, Primary Key 類型>
 */
@Repository
public interface UserRepository extends ListCrudRepository<User, Integer> {
    
    // 根據您資料庫中 email 欄位是 UNIQUE 的特性，我們可以定義一個自定義查詢方法：
    // Spring Data 會自動解析這個方法名稱，並產生 SQL: SELECT * FROM users WHERE email = ?
    User findByEmail(String email);

    // ListCrudRepository 自動獲得的方法 (無需手動撰寫):
    // List<User> findAll(); // 📢 現在返回 List<User>
    // Optional<User> findById(Integer id);
    // <S extends User> S save(S entity);
    // void deleteById(Integer id);
    
}