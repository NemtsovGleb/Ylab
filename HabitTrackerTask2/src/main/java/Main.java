import config.LiquibaseConfig;
import controllers.AdminController;
import controllers.AuthController;
import controllers.HabitController;
import controllers.PersonController;
import repositories.HabitRepository;
import repositories.PeopleRepository;
import services.*;
import util.PersonValidator;


public class Main {

    public static void main(String[] args) {

        // Запуск миграций Liquibase перед запуском приложения
        LiquibaseConfig.runMigrations();

        // инициализация слоев программы
        PeopleRepository peopleRepository = PeopleRepository.getInstance();
        HabitRepository habitRepository = HabitRepository.getInstance(peopleRepository);

        PersonValidator validator = new PersonValidator(peopleRepository);

        PersonService personService = new PersonService(peopleRepository);
        RegistrationService registrationService = new RegistrationService(peopleRepository, validator);
        AuthenticationService authService = new AuthenticationService(peopleRepository);
        AdminService adminService = new AdminService(peopleRepository);
        HabitService habitService = new HabitService(peopleRepository, habitRepository);

        HabitController habitController = new HabitController(habitService);
        AdminController adminController = new AdminController(peopleRepository, adminService, personService);
        PersonController personController = new PersonController(personService, adminController, habitController);
        AuthController authController = new AuthController(registrationService, authService, personController);

        authController.start();

    }
}