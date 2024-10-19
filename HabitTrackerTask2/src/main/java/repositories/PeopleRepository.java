package repositories;

import models.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PeopleRepository {
    private static final String FILE_NAME = "HabitTrackerTask2/src/main/resources/people.db";
    private List<Person> people;
    private Person currentPerson;

    private static PeopleRepository instance;

    private PeopleRepository() {
        this.people = loadData();
    }


    public static PeopleRepository getInstance() {
       if(instance == null)
           instance = new PeopleRepository();
       return instance;
    }

    // Сохранение данных в файл
    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(this.people);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для загрузки данных из файла
    @SuppressWarnings("unchecked")
    private List<Person> loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Person>) in.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // Если файла нет, возвращаем пустой список
        } catch (EOFException e) {
            return new ArrayList<>(); // Если файл пустой, возвращаем пустой список
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Person> getAllPeople() {
        return people;
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public void remove(Person person) {
        people.remove(person);
    }

    public void blockByName(String name) {

        for(Person person: people) {
            if(person.getUsername().equals(name)) {
                person.setIsBlocked(true);
                break;
            }
        }
        System.out.println("Пользователь был успешно заблокирован!");
    }

    public Optional<Person> findPersonByName(String name) {
        for (Person person : people) {
            if (person.getUsername().equalsIgnoreCase(name)) {
                return Optional.of(person);
            }
        }
        return Optional.empty();
    }

    public Optional<Person> findPersonByEmail(String email) {
        for (Person person : people) {
            if (person.getEmail().equals(email)) {
                return Optional.of(person);
            }
        }
        return Optional.empty();
    }

    public boolean hasAdmin() {
        return people.stream().anyMatch(person -> "ADMIN".equals(person.getRole()));
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }


}
