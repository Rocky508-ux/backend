package com.rctoyshop.backend.repository;

import com.rctoyshop.backend.model.CartItem;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends ListCrudRepository<CartItem, Integer> {
    
    /**
     * 根據購物車 ID 查找所有單品
     * (用於精確查詢購物車內容，但通常由 CartRepository 隱式處理)
     */
    List<CartItem> findByCartId(Integer cartId);
}