# 如何新增商品圖片對照表

想要幫商品加上圖片，您需要知道兩個東西的對應關係：
1. **商品的 ID** (在 `products` 資料表)
2. **圖片的檔名** (在 `product_images` 資料表)

## 步驟說明

### 第一步：決定您的「商品 ID」
假設您要新增一個「七龍珠 悟空」的商品。
您在 `products` 資料表設定的 ID 是 `dbz-goku`。

```sql
INSERT INTO products (id, name, ...) VALUES ('dbz-goku', '七龍珠 悟空', ...);
```

### 第二步：準備圖片
1. 找一張帥氣的悟空圖片。
2. 將它命名為好辨識的名字，例如 `goku_super.jpg`。
3. **放入資料夾**：
   將 `goku_super.jpg` 複製到 `backend/src/main/resources/static/image/` 資料夾內。

### 第三步：建立「對應橋樑」
這一步最重要！您需要告訴資料庫：「`dbz-goku` 這個商品，它的圖片是 `/image/goku_super.jpg`」。

請在 `data.sql` (或是您的資料庫管理工具) 執行這行：

```sql
INSERT INTO product_images (product_id, image_path, is_main) VALUES
('dbz-goku', '/image/goku_super.jpg', TRUE);
```

| 欄位 | 填寫內容 | 意義 |
| :--- | :--- | :--- |
| `product_id` | **dbz-goku** | 必須跟商品的 ID **一模一樣** |
| `image_path` | **/image/goku_super.jpg** | 必須跟您的檔名 **一模一樣** (前面要加 `/image/`) |
| `is_main` | `TRUE` | `TRUE` 代表這是封面圖，`FALSE` 代表是附圖 |

### 範例對照

看看您現在的 `data.sql` 範例：

```sql
-- 商品 (ID 是 dbz-broly)
('dbz-broly', '七龍珠超 布羅利...', ...)

-- 圖片 (把 dbz-broly 連接到 beast.webp)
('dbz-broly', '/image/beast.webp', TRUE)
```

這樣就串起來了！
