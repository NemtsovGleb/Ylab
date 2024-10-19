package controllers;

import services.PersonService;

import java.util.Scanner;

public class PersonController {

    private final PersonService personService;
    private final AdminController adminController;
    private final HabitController habitController;
    private static final Scanner scanner = new Scanner(System.in);

    public PersonController(PersonService personService, AdminController adminController, HabitController habitController) {
        this.personService = personService;
        this.adminController = adminController;
        this.habitController = habitController;
    }

    public void showUserMenu() {
        boolean loggedIn = true;
        while(loggedIn) {
            System.out.println("\n --- Добро пожаловать " + personService.getCurrentPerson().getUsername() + "! ---");
            System.out.println("1. Мой профиль");
            System.out.println("2. Выйти из аккаунта");
            System.out.println("3. Удалить свой аккаунт");
            System.out.println("4. Управление привычками");
            if(personService.getCurrentPerson().getRole().equals("ADMIN"))
                System.out.println("5. Управление пользователями");
            System.out.print("Выберите действие (1-5): ");

            String choice = scanner.nextLine().trim();
            switch(choice) {
                case "1":
                    showUserInfo();
                    break;
                case "2":
                    System.out.println("Выход из аккаунта");
                    loggedIn = false;
                    break;
                case "3":
                    loggedIn = personService.deleteAccount();
                    if(!loggedIn)
                        System.out.println("Аккаунт был успешно удален");
                    break;
                case "4":
                    habitController.viewHabits();
                    break;
                case "5":
                    adminController.showUsers();
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
            }
        }
    }

    public void showUserInfo() {
        boolean status = true;
        while(status) {
            System.out.println("\n--- ИНФОРМАЦИЯ О ПОЛЬЗОВАТЕЛЕ: ---");
            System.out.println("Имя пользователя: " + personService.getCurrentPerson().getUsername());
            System.out.println("Почта пользователя: " + personService.getCurrentPerson().getEmail());
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
                    personService.editPersonName();
                    break;
                case "2":
                    personService.editPersonEmail();
                    break;
                case "3":
                    personService.editPersonPassword();
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
