package controllers;

import models.Habit;
import models.Person;

public class HabitController {





    public void viewHabits(Person currentPerson) {
        habits = currentPerson.getHabits();
        outer:
        while(true) {

            if (habits.isEmpty()) {
                System.out.println("\nПривычки отсутствуют.");
            } else {
                System.out.println("\n--- Ваши привычки ---");
                for (Habit habit : habits) {
                    System.out.println("Название: " + habit.getName() + "\nОписание: " + habit.getDescription() + "\nДата создания: " + habit.getFormatCreateAt());
                    System.out.println("---------------");
                }
            }

            System.out.println("\n--- Управление ---");
            System.out.println("1. Добавить привычку");
            System.out.println("2. Удалить привычку");
            System.out.println("3. Редактировать привычку");
            System.out.println("4. Отметить выполнение привычки");
            System.out.println("5. Показать статистику привычек за определенный период(день, неделя, месяц)");
            System.out.println("6. Показать текущую серию выполнения привычек");
            System.out.println("7. Фильтрация привычек по дате создания");
            System.out.println("8. Фильтрация привычек по статусу выполнения");
            System.out.println("9. Вернуться");

            System.out.print("Выберите действие (1-9): ");

            String choice = scanner.nextLine().trim();

            switch(choice) {
                case "1":
                    addHabit(currentPerson);
                    break;
                case "2":
                    removeHabit(currentPerson);
                    break;
                case "3":
                    editHabit(currentPerson);
                    break;
                case "4":
                    completeHabit(currentPerson);
                    break;
                case "5":
                    generateHabitStats(currentPerson);
                    break;
                case "6":
                    calculateStreak(currentPerson);
                    break;
                case "7":
                    filterHabitsByCreationDate(currentPerson);
                    break;
                case "8":
                    filterHabitsByCompletionStatus(currentPerson);
                    break;
                case "9":
                    break outer;
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
            }
        }
    }
}
