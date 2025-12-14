package com.rctoyshop.backend.model;

// 引入 Spring Data JDBC 核心註解
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column; // 用於映射底線命名欄位
import lombok.Data;

// 引入 Java 8 日期時間類別
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
// 1. 使用 Spring Data JDBC 的 @Table
@Table("users") 
// 將類別名稱改為 User (單數慣例)
public class User { 

    // 2. 使用 Spring Data JDBC 的 @Id 標記主鍵 (無需 @GeneratedValue)
    @Id 
    private Integer id; 

    // 由於您的 JPA 寫法中沒有 @Column(name="email")，
    // Spring Data JDBC 會自動嘗試將 email 映射到資料庫的 email 欄位。
    private String email; 

    private String password;
    private String name;
    private String phone;
    
    // 3. 將 java.sql.Date 替換為 Java 8 的 LocalDate
    private LocalDate birthday; 

    // 4. ENUM 類型在 JDBC 中通常直接映射為 String 
    private String role;
    private String status;

    // 5. 將 java.sql.Timestamp 替換為 Java 8 的 LocalDateTime
    // 由於資料庫欄位是 created_at (底線命名)，Java 屬性名為 created_at (底線命名) 
    // 或者，建議改成駝峰命名並顯式映射：
    @Column("created_at")
    private LocalDateTime createdAt;
    // (如果保留 created_at，也可以省略 @Column，但這裡採用更標準的 Java 駝峰命名風格)
}