package com.rctoyshop.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import lombok.Data;

@Data
@Table("product_images")
public class ProductImage {

    @Id
    private Integer id;

    // 將 Java 屬性設為駝峰命名 (imagePath)，並顯式映射到 DB 的 image_path
    @Column("image_path")
    private String imagePath;

    // 將 Java 屬性設為駝峰命名 (isMain)，並顯式映射到 DB 的 is_main
    @Column("is_main")
    private Boolean isMain;

    // 移除 productId，交由 Aggregate Root (Product) 透過 @MappedCollection 自動維護
}