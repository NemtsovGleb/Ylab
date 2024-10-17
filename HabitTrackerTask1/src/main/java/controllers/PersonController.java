package controllers;

import services.PersonService;

public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
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

}
