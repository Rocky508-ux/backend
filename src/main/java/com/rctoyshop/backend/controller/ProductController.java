package com.rctoyshop.backend.controller;

import com.rctoyshop.backend.model.Product;
import com.rctoyshop.backend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // 標記為 RESTful API 控制器
@RequestMapping("/api/products") // 定義基礎 URL 路徑
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * GET /api/products : 取得所有商品列表
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /api/products/{id} : 根據 ID 取得單一商品詳情
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return productService.findProductById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST /api/products : 新增商品 (在真實專案中，應限制只有 ADMIN 可用)
     * 請求體 (Request Body) 應包含 Product JSON 物件 (包括圖片清單)
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product newProduct = productService.createProduct(product);
            // HTTP 201 CREATED
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            // 處理例如 ID 衝突或資料驗證失敗
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }
    }
}