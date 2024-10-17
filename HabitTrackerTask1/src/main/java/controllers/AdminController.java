package controllers;

import models.Person;
import repositories.PeopleRepository;
import services.AdminService;
import services.PersonService;

import java.util.Scanner;

public class AdminController {

    private final PeopleRepository peopleRepository;
    private final AdminService adminService;
    private final PersonService personService;
    private static final Scanner scanner = new Scanner(System.in);

    public AdminController(PeopleRepository peopleRepository, AdminService adminService, PersonService personService) {
        this.peopleRepository = peopleRepository;
        this.adminService = adminService;
        this.personService = personService;
    }

    public void showUsers() {
        boolean status = true;
        while(status) {
            System.out.println("\n--- Управление ---");
            System.out.println("1. Удалить пользователя");
            System.out.println("2. Заблокировать пользователя");
            System.out.println("3. Вернуться");
            System.out.print("Выберите действие (1 или 2): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    adminService.removeByName();
                    break;
                case "2":
                    adminService.blockByName();
                    break;
                case "3":
                    status = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
            }

        }
    }
}
