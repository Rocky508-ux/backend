-- ==========================================
-- (1) 資料庫結構 (Schema / DDL)
-- ==========================================

-- 清除舊表 (順序重要：先刪除有外鍵依賴的表)
-- 清除舊表 (順序重要：先刪除有外鍵依賴的表)
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS product_images;
DROP TABLE IF EXISTS cart_items; -- 必須在 products 和 carts 之前刪除
DROP TABLE IF EXISTS orders;     -- 必須在 users 之前刪除
DROP TABLE IF EXISTS carts;      -- 必須在 users 之前刪除
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users; 

-- 1. 使用者 (Users)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    birthday DATE,
    role ENUM('ADMIN', 'USER') DEFAULT 'USER',
    status ENUM('ACTIVE', 'DISABLED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. 商品 (Products)
CREATE TABLE products (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price INT NOT NULL,
    stock INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'available',
    category_id VARCHAR(50),
    tag VARCHAR(20),
    type VARCHAR(20),
    studio VARCHAR(100),
    scale VARCHAR(50),
    dimensions VARCHAR(100),
    material VARCHAR(100),
    estimated_arrival VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. 商品圖片 (Product Images)
CREATE TABLE product_images (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(50) NOT NULL,
    image_path VARCHAR(255) NOT NULL,
    is_main BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- 4. 訂單 (Orders)
CREATE TABLE orders (
    id VARCHAR(50) PRIMARY KEY,
    user_id INT NOT NULL,
    total_amount INT NOT NULL,
    total_items INT DEFAULT 0,
    status VARCHAR(20) DEFAULT '處理中',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    shipping_address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 5. 訂單明細 (Order Items)
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(50) NOT NULL,
    product_id VARCHAR(50) NOT NULL,
    product_name VARCHAR(255),
    quantity INT NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- 6. [Removed] 購物車 (Carts)
-- 7. [Removed] 購物車項目 (Cart Items)

-- (2) 初始資料 (Data / DML)
-- 已移至 data.sql
