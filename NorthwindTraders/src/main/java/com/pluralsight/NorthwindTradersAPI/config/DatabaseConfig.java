package com.pluralsight.NorthwindTradersAPI.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.Optional;

@Configuration
public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    private final String url;
    private final String username;
    private final String password;

    private BasicDataSource dataSource;

    public DatabaseConfig(
            @Value("${datasource.url}") String url,
            @Value("${dbUsername:}") String injectedUsername,
            @Value("${dbPassword:}") String injectedPassword) {

        this.url = url;

        // Prefer Spring-injected values, fallback to System properties if empty
        this.username = Optional.ofNullable(injectedUsername).filter(s -> !s.isBlank())
                .orElseGet(() -> System.getProperty("dbUsername"));
        this.password = Optional.ofNullable(injectedPassword).filter(s -> !s.isBlank())
                .orElseGet(() -> System.getProperty("dbPassword"));
    }

    private void initializeDataSource() {
        if (username == null || password == null) {
            log.warn("Database username or password not provided via Spring properties or system properties.");
        }

        dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // Optional tuning for production:
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(10);
        dataSource.setMinIdle(5);
        dataSource.setMaxWaitMillis(10000);

        log.info("Initialized BasicDataSource with URL [{}] and user [{}]", url, username);
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        return this.dataSource;
    }
}
