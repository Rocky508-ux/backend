package com.rctoyshop.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rctoyshop.backend.model.Product;
import com.rctoyshop.backend.repository.ProductRepository;

/**
 * 商品服務：處理商品 CRUD 和庫存/上下架管理
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    // Remove ProductImageRepository

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ----------------------------------------------------
    // 基礎/查詢功能
    // ----------------------------------------------------

    /**
     * 取得所有商品列表 (AdminProduct.vue: fetchProducts)
     */
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    /**
     * 根據 ID 取得單一商品
     */
    public Optional<Product> findProductById(String id) {
        return productRepository.findById(id);
    }

    // ----------------------------------------------------
    // 📢 AdminProduct.vue 必備功能
    // ----------------------------------------------------

    /**
     * 創建新商品
     */
    @Transactional
    public Product createProduct(Product product) {
        // 1. 設置時間戳
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        // 2. Spring Data JDBC 會自動處理 MappedCollection (Set<ProductImage>) 的儲存
        return productRepository.save(product);
    }

    /**
     * 更新商品資料
     */
    @Transactional
    public Product updateProduct(String id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();

            // 複製屬性
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            product.setStudio(updatedProduct.getStudio());
            product.setType(updatedProduct.getType());
            product.setTag(updatedProduct.getTag());
            product.setDescription(updatedProduct.getDescription());
            product.setEstimatedArrival(updatedProduct.getEstimatedArrival());
            // ... (更新其他規格欄位)

            product.setUpdatedAt(LocalDateTime.now());

            // 更新圖片：直接替換 Set 集合即可
            // Spring Data JDBC 會自動比對差異，進行 Delete/Insert 操作
            product.setImages(updatedProduct.getImages());

            return productRepository.save(product);
        }
        return null; // Or throw exception
    }

    /**
     * 刪除商品
     */
    @Transactional
    public void deleteProduct(String id) {
        // Spring Data JDBC 會自動級聯刪除關聯的圖片
        productRepository.deleteById(id);
    }

    // ----------------------------------------------------
    // 業務邏輯功能
    // ----------------------------------------------------

    /**
     * 處理庫存扣除
     */
    @Transactional
    public void deductStock(String productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found."));

        if (product.getStock() < quantity) {
            throw new IllegalStateException("Insufficient stock for product: " + productId);
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
}