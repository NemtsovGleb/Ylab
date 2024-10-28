package services;

import models.Person;
import repositories.PeopleRepository;

import java.util.Scanner;

public class AdminService {

    private final PeopleRepository peopleRepository;
    private static final Scanner scanner = new Scanner(System.in);

    public AdminService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    private boolean showUsers() {
        // Выведем список пользователей
        if(peopleRepository.getAllPeople().size() == 1) {
            System.out.println("Список пользователей пуст");
            return true;
        } else {
            int index = 1;
            System.out.println("\n Список пользователй:");
            for (Person person : peopleRepository.getAllPeople()) {

                // Не будем отображать самого админа в качестве пользователя которого можно удалить
                if(peopleRepository.getCurrentPerson() == person)
                    continue;

                String block = "";
                if(person.getIsBlocked())
                    block = " Заблокирован";

                System.out.println(index + ". " + person.getUsername() + " " + person.getRole() + block);
                index++;
            }
            return false;
        }
    }

    public void removeByName() {
        boolean status = true;
        while(status) {

            if(showUsers())
                break;
            System.out.println("Напишите номер человека, которого хотите удалить(1-" + (peopleRepository.getAllPeople().size()+1) + ") или exit если передумали:");
            String number = scanner.nextLine().trim();
            if(number.equals("exit"))
                break;

            peopleRepository.getAllPeople().remove(Integer.parseInt(number));

            status = false;

        }
    }

    public void blockByName() {
        boolean status = true;
        while(status) {

            if(showUsers())
                break;
            System.out.println("Напишите номер человека, которого хотите заблокировать(1-" + (peopleRepository.getAllPeople().size()+1) + ") или exit если передумали:");
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
