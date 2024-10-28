package controllers;

import models.Habit;
import services.HabitService;

import java.util.Scanner;

public class HabitController {

    private final HabitService habitService;
    private static final Scanner scanner = new Scanner(System.in);

    public HabitController( HabitService habitService) {
        this.habitService = habitService;
    }

    public void viewHabits() {
        while(true) {
            // Отобразим список всех привычек у данного пользователя
            habitService.showHabits();
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
                    habitService.addHabit();
                    break;
                case "2":
                    habitService.removeHabit();
                    break;
                case "3":
                    editHabit();
                    break;
                case "4":
                    habitService.completeHabit();
                    break;
                case "5":
                    habitService.generateHabitStats();
                    break;
                case "6":
                    habitService.calculateStreak();
                    break;
                case "7":
                    habitService.filterHabitsByCreationDate();
                    break;
                case "8":
                    habitService.filterHabitsByCompletionStatus();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
            }
        }
    }

    public void editHabit() {
        Habit habitToEdit = habitService.showHabitsForEdit();
            if(habitToEdit != null)
                showEditMenu(habitToEdit);
    }

    private void showEditMenu(Habit habit) {
        while(true) {
            System.out.println("\nИнформация о привычке:");
            System.out.println("1. Название привычки: " + habit.getName());
            System.out.println("2. Описание привычки: " + habit.getDescription());
            System.out.println("3. Частота выполнения: " + habit.getFrequency());

            System.out.println("Введите номер параметра для редактирования (1-3) или exit если передумали: ");
            String choice = scanner.nextLine().trim();

            if(choice.equals("exit"))
                return;

            switch(choice) {
                case "1":
                    habitService.editName(habit);
                    break;
                case "2":
                    habitService.editDescription(habit);
                    break;
                case "3":
                    habitService.editFrequency(habit);
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста повторите попытку.");
            }
        }
    }

}
