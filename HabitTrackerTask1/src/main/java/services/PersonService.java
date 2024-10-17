package services;

import models.Person;
import repositories.PeopleRepository;
import security.AuthProviderImpl;
import util.PersonValidator;

import java.util.Scanner;

public class PersonService {

    private final PeopleRepository peopleRepository;
    private final PersonValidator personValidator;
    private final AuthProviderImpl authProvider;
    private final HabitService habitService;
    private final AdminService adminService;
    private Person currentPerson;

    private static final Scanner scanner = new Scanner(System.in);

    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
        this.personValidator =  new PersonValidator(peopleRepository);
        this.authProvider = new AuthProviderImpl(peopleRepository);
        this.habitService = new HabitService(peopleRepository);
        this.adminService = new AdminService(peopleRepository);
    }

    public void logout() {
        this.currentPerson = null;
    }
    public boolean login() {
        return authProvider.authenticate();
    }

    public void addPerson() {
        String username;
        String email;
        String password;
        String role;

        outer:
        while(true) {

            // Ввод логина с циклом валидации
            while (true) {
                System.out.print("Введите логин или exit если передумали: ");
                username = scanner.nextLine();

                if (username.equals("exit"))
                    break outer;

                if (personValidator.validateUsername(username))
                    break; // Логин валидный и уникальный — продолжаем

                System.out.println("Попробуйте снова ввести логин.");
            }

            // Ввод email с циклом валидации
            while (true) {
                System.out.print("Введите email или exit если передумали: ");
                email = scanner.nextLine();

                if (email.equals("exit"))
                    break outer;

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
            peopleRepository.saveData(); // Сохраняем изменения в файл
            System.out.println("Пользователь успешно зарегистрирован: " + username);
            break;
        }
    }


    public void showUserMenu() {
        boolean loggedIn = true;
        currentPerson = authProvider.getCurrentPerson();
        while(loggedIn) {
            System.out.println("\n --- Добро пожаловать " + currentPerson.getUsername() + "! ---");
            System.out.println("1. Мой профиль");
            System.out.println("2. Выйти из аккаунта");
            System.out.println("3. Удалить свой аккаунт");
            System.out.println("4. Управление привычками");
            if(currentPerson.getRole().equals("ADMIN"))
                System.out.println("5. Управление пользователями");


            System.out.print("Выберите действие (1-5): ");

            String choice = scanner.nextLine().trim();

            switch(choice) {
                case "1":
                    showUserInfo();
                    break;
                case "2":
                    System.out.println("Выход из аккаунта");
                    logout();
                    loggedIn = false;
                    break;
                case "3":
                    loggedIn = deleteAccount();
                    if(!loggedIn)
                        System.out.println("Аккаунт был успешно удален");
                    break;
                case "4":
                    habitService.viewHabits(currentPerson);
                    break;
                case "5":
                    adminService.showUsers(currentPerson);
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
            }

        }
    }

    public boolean deleteAccount() {
        System.out.println("\n--- Удаление аккаунта! ---");
        boolean status = true;
        while(true) {
            System.out.println("Введите свой пароль или exit если передумали:");
            String password = scanner.nextLine().trim();

            if (password.equals("exit"))
                break;

            if (password.equals(currentPerson.getPassword())) {
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
                peopleRepository.remove(currentPerson);
                logout();
                break;
            case "2":
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
        }
        return status;
    }

    public void showUserInfo() {
        boolean status = true;
        while(status) {
            System.out.println("\n--- ИНФОРМАЦИЯ О ПОЛЬЗОВАТЕЛЕ: ---");
            System.out.println("Имя пользователя: " + currentPerson.getUsername());
            System.out.println("Почта пользователя: " + currentPerson.getEmail());

            System.out.println("1. Изменить данные");
            System.out.println("2. Вернуться в меню");
            System.out.print("Выберите действие (1 или 2): ");

            String choice = scanner.nextLine().trim();

            switch(choice) {
                case "1":
                    editUserInfo();
                    break;
                case "2":
                    status = false;
                    break;
                default:
                    System.out.println("Неверный выбор пожалуйста повторите попытку");
            }

        }
    }

    public void editUserInfo() {
        boolean status = true;
        while(status) {
            System.out.println("\n--- РЕДАКТОР ПОЛЬЗОВАТЕЛЯ: ---");
            System.out.println("1. Изменить логин");
            System.out.println("2. Изменить почту");
            System.out.println("3. Изменить пароль");
            System.out.println("4. Вернуться");

            System.out.print("Выберите действие (1,2,3 или 4): ");

            String choice = scanner.nextLine().trim();

            switch(choice) {
                case "1":
                    while (true) {
                        System.out.print("Введите новый логин или exit если передумали: ");
                        String username = scanner.nextLine().trim();
                        if(username.equals("exit"))
                            break;

                        if (personValidator.validateUsername(username)) {
                            currentPerson.setUsername(username);
                            System.out.println("Вы успешно изменили свой логин!");
                            break;
                        }

                        System.out.println("Логин введен неправильно, попробуйте еще.");
                    }
                    break;

                case "2":
                    while (true) {
                        System.out.print("Введите новую почту или exit если передумали: ");
                        String email = scanner.nextLine().trim();
                        if(email.equals("exit"))
                            break;

                        if (personValidator.validateEmail(email)) {
                            currentPerson.setEmail(email);
                            System.out.println("Вы успешно изменили свою почту!");
                            break;
                        }

                        System.out.println("Почта введена неправильно, попробуйте еще.");
                    }
                    break;

                case "3":
                    outer:
                    while (true) {
                        System.out.print("Введите ваш текущий пароль или exit если передумали: ");
                        String password = scanner.nextLine().trim();
                        if(password.equals("exit"))
                            break;

                        if(password.equals(currentPerson.getPassword())) {

                            while(true) {
                                System.out.println("Введите новый пароль или exit если передумали: ");
                                password = scanner.nextLine().trim();
                                if (password.equals("exit"))
                                    break outer;

                                if (personValidator.validatePassword(password)) {

                                    currentPerson.setPassword(password);
                                    System.out.println("Вы успешно изменили пароль");
                                    break;

                                }
                                System.out.println("Вы ввели некорректный пароль попробуйте еще");
                            }
                        } else {
                            System.out.println("Вы неправильно ввели пароль");
                        }

                    }
                    break;
                case "4":
                    status = false;
                    break;
                default:
                    System.out.println("Неверный выбор пожалуйста повторите попытку");
            }
            }


    }
}
