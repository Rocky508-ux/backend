package com.rctoyshop.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column; // 引入 Column 註解
import lombok.Data;

@Data
@Table("order_items")
public class OrderItem {

    @Id
    private Integer id;

    // *** 修正點 1: 改為駝峰命名 orderId ***
    @Column("order_id") // 顯式告訴 Spring Data JDBC，Java屬性對應到 DB的 order_id 欄位
    private String orderId;

    // *** 修正點 2: 改為駝峰命名 productId ***
    @Column("product_id")
    private String productId;
    
    @Column("product_name")
    private String productName;

    private Integer quantity;
    private Integer price;
}