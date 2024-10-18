package controllers;

import services.AuthenticationService;
import services.RegistrationService;
import java.util.Scanner;

public class AuthController {

    private final RegistrationService registrationService;
    private final AuthenticationService authService;
    private final PersonController personController;
    private static final Scanner scanner = new Scanner(System.in);

    public AuthController(RegistrationService registrationService, AuthenticationService authService, PersonController personController) {
        this.registrationService = registrationService;
        this.authService = authService;
        this.personController = personController;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Войти");
            System.out.println("2. Создать аккаунт");
            System.out.println("3. Выйти из программы");
            System.out.print("Выберите действие (1, 2 или 3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if(authService.authenticate())
                        personController.showUserMenu();
                    break;
                case "2":
                    registrationService.registerPerson();
                    break;
                case "3":
                    System.out.println("До свидания!");
                    running = false;  // Завершаем цикл, чтобы выйти из программы
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите действие(1, 2 или 3.");
            }
        }
    }
}
