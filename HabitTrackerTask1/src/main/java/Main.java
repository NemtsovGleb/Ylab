import repositories.PeopleRepository;
import services.PersonService;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // инициализация слоев программы
        PeopleRepository peopleRepository = PeopleRepository.getInstance();
        PersonService PersonService = new PersonService(peopleRepository);

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
                     if(PersonService.login())
                         PersonService.showUserMenu();
                    break;
                case "2":
                     PersonService.addPerson();
                    break;
                case "3":
                    peopleRepository.saveData();
                    System.out.println("До свидания!");
                    running = false;  // Завершаем цикл, чтобы выйти из программы
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите действие(1, 2 или 3.");
            }
        }
    }
}