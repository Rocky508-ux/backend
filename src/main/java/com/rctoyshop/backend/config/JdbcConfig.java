package com.rctoyshop.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;

/**
 * Spring Data JDBC Configuration
 * Enables automatic camelCase to snake_case mapping for database columns.
 */
@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

    @Bean
    public NamingStrategy namingStrategy() {
        return new NamingStrategy() {
            @Override
            public String getColumnName(RelationalPersistentProperty property) {
                // Assert.notNull(property, "Property must not be null");
                // Convert camelCase to snake_case (e.g., userId -> user_id)
                String propertyName = property.getName();
                return propertyName.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
            }
        };
    }
}
