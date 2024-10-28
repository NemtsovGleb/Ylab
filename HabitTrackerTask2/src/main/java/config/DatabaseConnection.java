package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();

        // Используем ClassLoader для загрузки файла ресурсов
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new FileNotFoundException("Файл 'application.properties' не найден в classpath");
            }
            properties.load(input);
        }
        return properties;
    }

    public static Connection getConnection() throws IOException, SQLException {
        Properties properties = loadProperties();

        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        Connection connection = DriverManager.getConnection(url,username, password);

        // Устанавливаем схему по умолчанию для каждого подключения
        try (Statement statement = connection.createStatement()) {
            statement.execute("SET search_path TO app_schema");
        }

        return connection;
    }
}
