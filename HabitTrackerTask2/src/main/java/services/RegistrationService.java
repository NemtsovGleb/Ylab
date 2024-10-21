package services;

import models.Person;
import repositories.PeopleRepository;
import util.PersonValidator;

import java.util.Scanner;

public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PersonValidator personValidator;
    private static final Scanner scanner = new Scanner(System.in);

    public RegistrationService(PeopleRepository peopleRepository, PersonValidator personValidator) {
        this.peopleRepository = peopleRepository;
        this.personValidator = personValidator;
    }

    public void registerPerson() {
        String username;
        String email;
        String password;
        String role;

            // Ввод логина с циклом валидации
            while (true) {
                System.out.print("Введите логин или exit если передумали: ");
                username = scanner.nextLine();

                if (username.equals("exit"))
                    return;

                if (personValidator.validateUsername(username))
                    break; // Логин валидный и уникальный — продолжаем

                System.out.println("Попробуйте снова ввести логин.");
            }

            // Ввод email с циклом валидации
            while (true) {
                System.out.print("Введите email или exit если передумали: ");
                email = scanner.nextLine();

                if (email.equals("exit"))
                    return;

                if (personValidator.validateEmail(email))
                    break; // Email валидный — продолжаем


                System.out.println("Попробуйте снова ввести email.");
            }

            // Ввод пароля с циклом валидации
            while (true) {
                System.out.print("Введите пароль: ");
                password = scanner.nextLine();
                if (personValidator.validatePassword(password)) {
                    break; // Пароль валидный — продолжаем
                }
                System.out.println("Попробуйте снова ввести пароль.");
            }

            //Проверка наличия админа в системе
            if (peopleRepository.hasAdmin()) {
                role = "USER";
            } else {
                role = "ADMIN";
            }

            // Если все данные валидны, создаем нового пользователя
            Person newPerson = new Person(username, password, email, role); // Создаем объект Person
            peopleRepository.addPerson(newPerson); // Добавляем пользователя в репозиторий
            System.out.println("Пользователь успешно зарегистрирован: " + username);

    }
}
