package util;

import models.Person;
import repositories.PeopleRepository;

import java.util.Optional;

public class PersonValidator {

    private final PeopleRepository peopleRepository;

    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    // Проверка логина
    public boolean validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Ошибка: Логин не может быть пустым.");
            return false;
        }
        if (!username.matches("^[a-zA-Z0-9_.-]{3,15}$")) { // Логин должен соответствовать шаблону
            System.out.println("Ошибка: Логин должен содержать от 3 до 15 символов и состоять из букв, цифр, точек, дефисов или символов подчеркивания.");
            return false;
        }
        if (!isNameUnique(username)) { // Проверка уникальности логина в базе данных
            System.out.println("Ошибка: Логин '" + username + "' уже занят. Пожалуйста, выберите другой.");
            return false;
        }
        System.out.println("Логин валидный.");
        return true;
    }

    // Метод проверки уникальности логина
    public boolean isNameUnique(String name) {
        Optional<Person> person = peopleRepository.findPersonByName(name);
        return person.isEmpty(); // Возвращаем true, если человек с таким именем не найден
    }

    // Метод проверки уникальности почты
    public boolean isEmailUnique(String email) {
        Optional<Person> person = peopleRepository.findPersonByEmail(email);
        return person.isEmpty(); // возвращаем true, если человек с таким именем не найден
    }

    // Проверка email
    public boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Ошибка: Email не может быть пустым.");
            return false;
        }

        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            System.out.println("Ошибка: Неверный формат email. Пример: example@domain.com");
            return false;
        }

        if (!isEmailUnique(email)) { // Проверка уникальности логина в базе данных
            System.out.println("Ошибка: Email '" + email + "' уже занят. Пожалуйста, выберите другой.");
            return false;
        }

        System.out.println("Email валидный.");
        return true;
    }

    // Проверка пароля
    public boolean validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Ошибка: Пароль не может быть пустым.");
            return false;
        }
        if (password.length() < 8) {
            System.out.println("Ошибка: Пароль должен содержать не менее 8 символов.");
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            System.out.println("Ошибка: Пароль должен содержать хотя бы одну цифру.");
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            System.out.println("Ошибка: Пароль должен содержать хотя бы одну строчную букву.");
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            System.out.println("Ошибка: Пароль должен содержать хотя бы одну заглавную букву.");
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()_+=-].*")) {
            System.out.println("Ошибка: Пароль должен содержать хотя бы один специальный символ (!@#$%^&*()_+=-).");
            return false;
        }
        System.out.println("Пароль валидный.");
        return true;
    }


}
