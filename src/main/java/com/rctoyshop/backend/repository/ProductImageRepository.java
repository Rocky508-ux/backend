package com.rctoyshop.backend.repository;

import com.rctoyshop.backend.model.ProductImage;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional; // 📢 必須導入

import java.util.List;

@Repository
public interface ProductImageRepository extends ListCrudRepository<ProductImage, Integer> {
    
    /**
     * 📢 修正點：必須定義此方法。
     * Spring Data 會解析 "deleteByProductId"，並自動生成刪除 SQL。
     * @Transactional 確保整個刪除操作是一個原子單元。
     */
    @Transactional 
    void deleteByProductId(String productId); 
    
    // 根據 Product ID 查找所有圖片 (Service 依賴於此 Model，這裡提供完整 Repository)
    List<ProductImage> findByProductId(String productId);
}