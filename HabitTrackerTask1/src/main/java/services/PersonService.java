package services;

import models.Person;
import repositories.PeopleRepository;
import util.PersonValidator;

import java.util.List;
import java.util.Scanner;

public class PersonService {

    private final PeopleRepository peopleRepository;
    private final PersonValidator personValidator;

    private static final Scanner scanner = new Scanner(System.in);

    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
        this.personValidator = new PersonValidator(peopleRepository);
    }

    public Person getCurrentPerson() {
        return peopleRepository.getCurrentPerson();
    }


    public void editPersonName() {
        while (true) {
            System.out.print("Введите новый логин или exit если передумали: ");
            String username = scanner.nextLine().trim();
            if (username.equals("exit"))
                break;

            if (personValidator.validateUsername(username)) {
                getCurrentPerson().setUsername(username);
                System.out.println("Вы успешно изменили свой логин!");
                break;
            }
            System.out.println("Логин введен неправильно, попробуйте еще.");
        }

    }

    public void editPersonEmail() {
        while (true) {
            System.out.print("Введите новую почту или exit если передумали: ");
            String email = scanner.nextLine().trim();
            if(email.equals("exit"))
                break;

            if (personValidator.validateEmail(email)) {
                getCurrentPerson().setEmail(email);
                System.out.println("Вы успешно изменили свою почту!");
                break;
            }

            System.out.println("Почта введена неправильно, попробуйте еще.");
        }
    }

    public void editPersonPassword() {

        while (true) {
            System.out.print("Введите ваш текущий пароль или exit если передумали: ");
            String password = scanner.nextLine().trim();
            if(password.equals("exit"))
                break;

            if(password.equals(getCurrentPerson().getPassword())) {

                while(true) {
                    System.out.println("Введите новый пароль или exit если передумали: ");
                    password = scanner.nextLine().trim();
                    if (password.equals("exit"))
                        return;

                    if (personValidator.validatePassword(password)) {
                        getCurrentPerson().setPassword(password);
                        System.out.println("Вы успешно изменили пароль");
                        break;
                    }
                    System.out.println("Вы ввели некорректный пароль попробуйте еще");
                }
            } else {
                System.out.println("Вы неправильно ввели пароль");
            }
        }
    }

    public boolean deleteAccount() {
        System.out.println("\n--- Удаление аккаунта! ---");
        boolean status = true;
        while (true) {
            System.out.println("Введите свой пароль или exit если передумали:");
            String password = scanner.nextLine().trim();

            if (password.equals("exit"))
                break;

            if (password.equals(peopleRepository.getCurrentPerson().getPassword())) {
                break;
            } else {
                System.out.println("Вы неправильно ввели пароль.");
            }

        }

        System.out.println("Вы уверены что хотите удалить аккаунт?");
        System.out.println("1. Да");
        System.out.println("2. Нет");
        System.out.println("Выберите действие(1 или 2):");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                status = false;
                peopleRepository.remove(peopleRepository.getCurrentPerson());
                break;
            case "2":
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
        }
        return status;
    }

    public List<Person> getAllPeople() {
        return peopleRepository.getAllPeople();
    }

}


