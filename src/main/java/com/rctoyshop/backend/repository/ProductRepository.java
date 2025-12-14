package com.rctoyshop.backend.repository;

import com.rctoyshop.backend.model.Product;
// 📢 關鍵修正：繼承 ListCrudRepository
import org.springframework.data.repository.ListCrudRepository; 
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 商品 Repository (DAO) - 升級為 ListCrudRepository
 */
@Repository
public interface ProductRepository extends ListCrudRepository<Product, String> {
    
    // ListCrudRepository.findAll() 會直接回傳 List<Product>
    
    // --- 其他自定義查詢方法不變 ---
    
    List<Product> findByCategoryId(String categoryId);

    List<Product> findByStockLessThanEqual(Integer stock);
    
    List<Product> findByTagAndStatus(String tag, String status);

    List<Product> findByType(String type);
}