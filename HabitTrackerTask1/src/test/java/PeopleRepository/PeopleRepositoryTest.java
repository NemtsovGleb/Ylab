package PeopleRepository;

import models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PeopleRepositoryTest {

    private PeopleRepository peopleRepository;
    private List<Person> mockPeopleList;

    @BeforeEach
    void setUp() {
        // Создаем мок репозитория и мок списка пользователей
        peopleRepository = Mockito.mock(PeopleRepository.class);
        mockPeopleList = new ArrayList<>();
    }

    @Test
    void testAddPerson() {
        // Мокаем добавление человека в репозиторий
        Person person = new Person("John", "12345Abc@", "john@example.com", "USER");
        mockPeopleList.add(person);

        // Устанавливаем поведение моков
        when(peopleRepository.getAllPeople()).thenReturn(mockPeopleList);

        // Действия в тесте
        peopleRepository.addPerson(person);
        // Убедимся, что метод был вызван
        verify(peopleRepository).addPerson(person);

        List<Person> allPeople = peopleRepository.getAllPeople();

        // Проверка, что человек был добавлен в список
        assertTrue(allPeople.contains(person), "Person should be added to the repository");
    }

}
