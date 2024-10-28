package repositories;

import config.DatabaseConnection;
import models.Person;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PeopleRepository {

    private Person currentPerson;
    private static PeopleRepository instance;

    private PeopleRepository() {}

    public static PeopleRepository getInstance() {
       if(instance == null)
           instance = new PeopleRepository();
       return instance;
    }

    // Получение всех пользователй из бд
    public List<Person> getAllPeople() {
        List<Person> people = new ArrayList<>();
        String query = "Select * from person";
        try(Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Person person = new Person();
                person.setUsername(resultSet.getString("username"));
                person.setPassword(resultSet.getString("password"));
                person.setEmail(resultSet.getString("email"));
                person.setRole(resultSet.getString("role"));
                person.setIsBlocked(resultSet.getBoolean("is_blocked"));

                people.add(person);

            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return people;
    }

    // Метод для добавления пользователя в базу данных
    public void addPerson(Person person) {
        String query = "INSERT INTO person (username, password, email, role, is_blocked) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, person.getUsername());
            statement.setString(2, person.getPassword());
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getRole());
            statement.setBoolean(5, person.getIsBlocked());

            statement.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Удалить пользователя
    public void remove(Person person) {
        String query = "Delete from person where username=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, person.getUsername());
            statement.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    public void blockByName(String name) {
        String query = "Update person set is_blocked = true where username = ?";
        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1,name);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Пользователь был успешно заблокирован!");
            } else {
                System.out.println("Пользователь с таким именем не найден.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Person> findPersonByName(String name) {
        String query = "select * from person where username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1,name);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setUsername(resultSet.getString("username"));
                person.setPassword(resultSet.getString("password"));
                person.setEmail(resultSet.getString("email"));
                person.setRole(resultSet.getString("role"));
                person.setIsBlocked(resultSet.getBoolean("is_blocked"));
                return Optional.of(person);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Person> findPersonByEmail(String email) {
        String query = "select * from person where email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setUsername(resultSet.getString("username"));
                person.setPassword(resultSet.getString("password"));
                person.setEmail(resultSet.getString("email"));
                person.setRole(resultSet.getString("role"));
                person.setIsBlocked(resultSet.getBoolean("is_blocked"));
                return Optional.of(person);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean hasAdmin() {
        String query = "SELECT 1 FROM person WHERE role = 'ADMIN'";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            return resultSet.next();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }


}
