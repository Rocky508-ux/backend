package com.rctoyshop.backend.controller;

import com.rctoyshop.backend.model.Product;
import com.rctoyshop.backend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products") // Admin 專用路徑
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * GET /api/admin/products : 獲取所有產品清單 (AdminProduct.vue: fetchProducts)
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        // 假設 ProductService 中有 findAllProducts 方法
        List<Product> products = productService.findAllProducts(); 
        return ResponseEntity.ok(products);
    }

    /**
     * POST /api/admin/products : 創建新產品 (AdminProduct.vue: saveProduct - 新增)
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.createProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    /**
     * PUT /api/admin/products/{id} : 更新產品資料 (AdminProduct.vue: saveProduct - 編輯)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * DELETE /api/admin/products/{id} : 刪除產品 (AdminProduct.vue: deleteProduct)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id); 
        return ResponseEntity.noContent().build();
    }
}