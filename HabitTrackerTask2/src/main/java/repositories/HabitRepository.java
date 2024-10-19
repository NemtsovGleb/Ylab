package repositories;

import models.Habit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HabitRepository {

    private static final String FILE_NAME = "HabitTrackerTask2/src/main/resources/habits.db";
    private List<Habit> habits;
    private List<Habit> ownersHabits;
    private static HabitRepository instance;

    private HabitRepository() {
        this.habits = loadData();
    }

    public static HabitRepository getInstance() {
        if(instance == null)
            instance = new HabitRepository();
        return instance;
    }

    // Сохранение данных в файл
    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(this.habits);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для загрузки данных из файла
    @SuppressWarnings("unchecked")
    private List<Habit> loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Habit>) in.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // Если файла нет, возвращаем пустой список
        } catch (EOFException e) {
            return new ArrayList<>(); // Если файл пустой, возвращаем пустой список
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Habit> getAllHabits() {
        return habits;
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
    }

    public void remove(Habit habit) {
        habits.remove(habit);
    }

    public List<Habit> getOwnersHabits() {
        return ownersHabits;
    }

    public void setOwnersHabits(List<Habit> ownersHabits) {
        this.ownersHabits = ownersHabits;
    }
}
