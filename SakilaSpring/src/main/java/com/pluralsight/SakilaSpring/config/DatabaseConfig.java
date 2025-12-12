package com.pluralsight.SakilaSpring.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    private final String jdbcUrl;
    private final String dbUsername;
    private final String dbPassword;

    public DatabaseConfig(
            @Value("${spring.datasource.url}") String jdbcUrl
    ) {
        this.jdbcUrl = requireNonEmpty(jdbcUrl, "spring.datasource.url");
        this.dbUsername = requireNonEmpty(
                System.getProperty("dbUsername"),
                "dbUsername system property"
        );
        this.dbPassword = requireNonEmpty(
                System.getProperty("dbPassword"),
                "dbPassword system property"
        );
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(jdbcUrl);
        ds.setUsername(dbUsername);
        ds.setPassword(dbPassword);

        // Connection pool tuning
        ds.setInitialSize(2);
        ds.setMaxTotal(20);
        ds.setMaxIdle(10);
        ds.setMinIdle(2);
        ds.setMaxWaitMillis(5000);

        // Validation
        ds.setTestOnBorrow(true);
        ds.setValidationQuery("SELECT 1");

        return ds;
    }

    private static String requireNonEmpty(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required configuration value for: " + name
            );
        }
        return value;
    }
}