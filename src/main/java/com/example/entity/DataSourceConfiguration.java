package com.example.entity;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.custom")
public class DataSourceConfiguration {
    
    private boolean autoCommit;
    private String jdbcUrl;
    private String username;
    private String password;
    private String cachePrepStmts;
    private String prepStmtCacheSize;
    private String prepStmtCacheSqlLimit;
    
    @Bean
    public DataSource dataSource(){
        HikariDataSource dataSource;
        HikariConfig config = new HikariConfig();
        config.setAutoCommit(autoCommit);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts" , cachePrepStmts);
        config.addDataSourceProperty("prepStmtCacheSize" , prepStmtCacheSize);
        config.addDataSourceProperty("prepStmtCacheSqlLimit" , prepStmtCacheSqlLimit);
        dataSource = new HikariDataSource(config);
        return dataSource;
    }
    
}
