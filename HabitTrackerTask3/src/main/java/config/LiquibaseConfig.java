package config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class LiquibaseConfig {
    // Запуск миграций
    public static void runMigrations() {
        try (Connection connection = DatabaseConnection.getConnection()) {

            // Создать схемы, если они не существуют
            createSchemas(connection);

            // Инициализация базы данных для Liquibase
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            // Создание экземпляра Liquibase с файлом миграций
            Liquibase liquibase = new Liquibase("db/changelog/main-changelog.xml",
                    new ClassLoaderResourceAccessor(),
                    database);

            // Установить схему для служебных таблиц Liquibase
            database.setLiquibaseSchemaName("liquibase_schema");

            // Запуск миграций
            liquibase.update("");
        } catch (SQLException | IOException | LiquibaseException e) {
            // Обработка исключений
            e.printStackTrace();
        }
    }

    // Метод для создания схемы, если она не существует
    private static void createSchemas(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Создание схемы liquibase_schema, если она еще не создана
            statement.execute("CREATE SCHEMA IF NOT EXISTS liquibase_schema");

            // Создание схемы app_schema, если она еще не создана
            statement.execute("CREATE SCHEMA IF NOT EXISTS app_schema");
        }
    }

}
