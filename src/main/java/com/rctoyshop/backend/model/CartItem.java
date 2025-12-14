package com.rctoyshop.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("cart_items")
public class CartItem {
    
    // 購物車單品項目的唯一 ID (Primary Key)
    @Id
    private Integer id;
    
    // 外部鍵 (Foreign Key)：連接到 Cart 表格
    @Column("cart_id")
    private Integer cartId; // 屬於哪個購物車
    
    // 外部鍵 (Foreign Key)：連接到 Product 表格
    @Column("product_id")
    private String productId; // 商品的 ID
    
    private Integer quantity; // 購買數量
    
    // 價格 (通常從 Product Model 取得，這裡可儲存當時的價格作為快照)
    private Integer price; 
}