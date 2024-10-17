package security;

import models.Person;
import repositories.PeopleRepository;

import java.util.Optional;
import java.util.Scanner;

public class AuthProviderImpl {

    private final PeopleRepository peopleRepository;
    private Person currentPerson;


    private static final Scanner scanner = new Scanner(System.in);

    public AuthProviderImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public boolean authenticate() {
        Optional<Person> person;

        while (true) {
            System.out.print("\nВведите логин или exit для выхода: ");
            String username = scanner.nextLine().trim();
            if(username.equals("exit"))
                return false;
            person = peopleRepository.findPersonByName(username);
            if (person.isPresent()) {
                break; // логин верный
            }
            System.out.println("\nЛогин введен неправильно, попробуйте еще.");
        }

            Person person1 = person.get();

            while(true) {
                System.out.print("\nВведите пароль или exit для выхода: ");
                String password = scanner.nextLine().trim();
                if(password.equals("exit"))
                    return false;

                if (person1.getPassword().equals(password)) {

                    if(person1.getIsBlocked()) {
                        System.out.println("Извините данный аккаунт заблокировн");
                        return false;
                    }

                    System.out.println("\nВы успешно вошли в свой аккаунт");
                    currentPerson = person1;
                    break; // пароль верный
                }
                System.out.println("\nПароль введен неправильно, поробуйте еще");
            }
            return true;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }


}
