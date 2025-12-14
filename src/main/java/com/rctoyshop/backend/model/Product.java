package com.rctoyshop.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column; 
import org.springframework.data.relational.core.mapping.MappedCollection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set; 

@Data
@Table("products")
public class Product {

    @Id
    private String id;

    private String name;
    private String description;
    private Integer price;
    private Integer stock;
    private String status;
    
    // 📢 修正點 1: 補上 Studio 屬性
    private String studio; 
    
    // 📢 修正點 2: 補上 EstimatedArrival 屬性 (注意駝峰命名)
    @Column("estimated_arrival")
    private String estimatedArrival;

    // 規格詳情 (AdminProduct.vue 中的欄位)
    private String scale;
    private String dimensions;
    private String material;
    
    // 欄位映射
    @Column("category_id")
    private String categoryId;
    
    private String tag;
    private String type;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    // 關聯性
    @MappedCollection(idColumn = "product_id") 
    private Set<ProductImage> images; 
}