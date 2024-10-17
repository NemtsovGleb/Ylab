package services;

import models.Person;
import repositories.PeopleRepository;

import java.util.List;
import java.util.Scanner;

public class AdminService {

    private final PeopleRepository peopleRepository;
    List<Person> people;
    private static final Scanner scanner = new Scanner(System.in);

    public AdminService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    public void showUsers(Person currentPerson) {
        people = peopleRepository.getAllPeople();
        boolean status = true;
        while(status) {

            if(people.size() == 1) {
                System.out.println("Список пользователей пуст");
            } else {
                int index = 1;
                System.out.println("\n Список пользователй:");
                for (Person person : people) {

                    if(currentPerson == person)
                        continue;

                    String block = "";
                    if(person.getIsBlocked())
                        block = " Заблокирован";

                    System.out.println(index + ". " + person.getUsername() + " " + person.getRole() + block);
                    index++;
                }
            }

            System.out.println("\n--- Управление ---");
            System.out.println("1. Удалить пользователя");
            System.out.println("2. Заблокировать пользователя");
            System.out.println("3. Вернуться");

            System.out.print("Выберите действие (1 или 2): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    removeByName();
                    break;
                case "2":
                    blockByName();
                    break;
                case "3":
                    status = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
            }

        }
    }


    public void removeByName() {
        boolean status = true;
        while(status) {
            System.out.println("Напишите имя человека, которого хотите удалить или exit если передумали:");
            String name = scanner.nextLine().trim();
            if(name.equals("exit"))
                break;


            if(peopleRepository.findPersonByName(name).isEmpty()) {
                System.out.println("Такого человека нет, попробуйте еще");
                continue;
            }

            peopleRepository.removeByName(name);

            status = false;

        }
    }

    public void blockByName() {
        boolean status = true;
        while(status) {
            System.out.println("Напишите имя человека, которого хотите заблокировать или exit если передумали:");
            String name = scanner.nextLine().trim();
            if(name.equals("exit"))
                break;


            if(peopleRepository.findPersonByName(name).isEmpty()) {
                System.out.println("Такого человека нет, попробуйте еще");
                continue;
            }

            peopleRepository.blockByName(name);

            status = false;

        }
    }
}
