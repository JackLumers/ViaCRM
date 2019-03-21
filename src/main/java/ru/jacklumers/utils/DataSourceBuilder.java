package ru.jacklumers.utils;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Класс-утилита, с помощью которой создается подключение к базе данных
 */
public final class DataSourceBuilder {

    /**
     * Сделать DataSource, используя свойства.
     *
     * @param properties - свойства подключения, где
     *                   db.url - URL
     *                   db.username - Имя пользователя БД
     *                   db.password - Пароль
     *                   db.driverClassName - Класс драйвера JDBC
     * @return DataSource - фабрика для взятия подключения к физическому источнику данных,
     * (базе данных), который эта DataSource представляет.
     * @see DataSource
     * @see DriverManagerDataSource
     */
    public static DataSource buildDataSourceUsingProperties(Properties properties) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String dbUrl = properties.getProperty("db.url");
        String dbUsername = properties.getProperty("db.username");
        String dbPassword = properties.getProperty("db.password");
        String driverClassName = properties.getProperty("db.driverClassName");

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }
}
