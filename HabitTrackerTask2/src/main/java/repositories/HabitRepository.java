package repositories;

import models.Habit;
import models.Person;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitRepository {

    private static PeopleRepository peopleRepository;
    private static HabitRepository instance;

    private HabitRepository(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public static HabitRepository getInstance(PeopleRepository peopleRepository) {
        if(instance == null)
            instance = new HabitRepository(peopleRepository);
        return instance;
    }

    public void addHabit(Habit habit) {
        String query = "INSERT INTO habit (name, description, created_at, frequency, owner_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, habit.getName());
            statement.setString(2, habit.getDescription());
            statement.setDate(3, new Date(habit.getCreateAt().getTime()));
            statement.setString(4, habit.getFrequency());
            statement.setInt(5, habit.getOwner().getId());

            statement.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void remove(Habit habit) {
        String query = "Delete from habit where name=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, habit.getName());
            statement.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<Habit> getOwnersHabits() {
        List<Habit> habits = new ArrayList<>();
        String query = "SELECT * FROM habit WHERE owner_id = ?";
        String query2 = "SELECT * FROM habit_completion_dates WHERE habit_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement statement2 = connection.prepareStatement(query2)) {

            // Устанавливаем ID текущего пользователя
            statement.setInt(1, peopleRepository.getCurrentPerson().getId());

            // Выполняем запрос на получение привычек
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Habit habit = new Habit();
                    habit.setId(resultSet.getInt("id"));
                    habit.setName(resultSet.getString("name"));
                    habit.setDescription(resultSet.getString("description"));
                    habit.setCreateAt(resultSet.getTimestamp("created_at"));
                    habit.setFrequency(resultSet.getString("frequency"));
                    habit.setOwner(peopleRepository.getCurrentPerson());

                    // Устанавливаем id привычки и выполняем запрос для получения дат выполнения
                    statement2.setInt(1, habit.getId());
                    try (ResultSet resultSet2 = statement2.executeQuery()) {
                        while (resultSet2.next()) {
                            Date date = resultSet2.getDate("completion_date");
                            habit.getCompletionDates().add(date);
                        }
                    }

                    habits.add(habit);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return habits;
    }

}
