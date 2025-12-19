-- (1) 使用者
INSERT IGNORE INTO users (email, password, name, role, status) VALUES 
('admin@rc.com', 'admin123', 'Admin User', 'ADMIN', 'active'), -- pass: admin123
('user@example.com', 'password', 'Normal User', 'USER', 'active'), -- pass: 123456
('user2@example.com', '123456', 'User2', 'USER', 'active'); -- pass: 123456

-- (2) 商品
INSERT IGNORE INTO products (id, name, description, price, stock, category_id, tag, type, studio, scale, dimensions, material, estimated_arrival) VALUES
('gundam-rx-78-2', '鋼彈 RX-78-2 Ver.Ka', '由知名設計師 Katoki Hajime 監修，追求極致的機械結構與可動性。', 3200, 10, 'gundam', 'new', 'model', 'Bandai Namco', '1/100 (MG)', 'H:18cm', 'PS, ABS, PVC', '現貨'),
('dbz-broly', '七龍珠超 布羅利 (Broly) 傳說超級賽亞人', '傳說中的超級賽亞人，擁有無窮無盡的破壞力。', 18800, 3, 'dbz', '預購', 'figure', 'Deyin Studio', '1/6', 'H:45cm W:38cm D:30cm', '進口樹脂 + PU', '2025年 第3季'),
('one-piece-luffy', 'ONE PIECE 魯夫', '草帽海賊團的船長，夢想是找到傳說中的大秘寶「ONE PIECE」。', 2200, 20, 'onepiece', 'new', 'figure', 'Banpresto', 'N/A', 'H:16cm', 'PVC', '現貨'),
('naruto-uzumaki', '火影忍者 鳴人', '木葉忍者村的英雄，體內封印著九尾妖狐。', 2800, 15, 'naruto', '現貨', 'figure', 'MegaHouse', '1/8', 'H:20cm', 'PVC', '現貨'),
('pokemon-pikachu', '寶可夢 皮卡丘', '小智最親密的夥伴，擅長使用十萬伏特。', 2000, 50, 'pokemon', NULL, 'prize', 'Pokemon Center', 'N/A', 'H:10cm', '布偶', '現貨'),
('demon-slayer-tanjiro', '鬼滅之刃 炭治郎', '為了讓變成鬼的妹妹復原而加入鬼殺隊的少年。', 2400, 10, 'other', '現貨', 'figure', 'Aniplex', '1/8', 'H:18cm', 'PVC', '現貨');

-- (3) 商品圖片
INSERT INTO product_images (product_id, image_path, is_main) VALUES
('gundam-rx-78-2', '/image/draHa.jpg', TRUE),
('gundam-rx-78-2', '/image/draHa-2.jpg', FALSE),
('dbz-broly', '/image/beast.webp', TRUE),
('dbz-broly', '/image/broly1.jpg', FALSE),
('one-piece-luffy', '/image/drass3-1.jpg', TRUE),
('naruto-uzumaki', '/image/drass3-2.jpg', TRUE),
('pokemon-pikachu', '/image/ball.jpg', TRUE),
('demon-slayer-tanjiro', '/image/drass3-3.jpg', TRUE);

-- (4) 訂單 (Orders)
INSERT IGNORE INTO orders (id, user_id, total_amount, status, shipping_address) VALUES 
('ORD-20231212-001', 2, 5400, '已送達', '台北市信義區市府路45號'),
('ORD-20231212-002', 3, 18800, '處理中', '台中市西區台灣大道二段'),
('ORD-NEW-USER', 3, 2800, '處理中', '高雄市新興區');

-- (5) 訂單明細 (Order Items)
INSERT IGNORE INTO order_items (order_id, product_id, product_name, quantity, price) VALUES 
('ORD-20231212-001', 'gundam-rx-78-2', '鋼彈 RX-78-2 Ver.Ka', 1, 3200),
('ORD-20231212-001', 'one-piece-luffy', 'ONE PIECE 魯夫', 1, 2200),
('ORD-20231212-002', 'dbz-broly', '七龍珠超 布羅利 (Broly)', 1, 18800),
('ORD-NEW-USER', 'naruto-uzumaki', '火影忍者 鳴人', 1, 2800);
