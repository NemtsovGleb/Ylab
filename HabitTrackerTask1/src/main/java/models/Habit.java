package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Habit implements Serializable {

    private String name;
    private String description;
    private Date createAt;
    private String frequency;
    private Person owner;
    private List<Date> completionDates; // хранение истории выполнения

    public Habit(String name, String description, String frequency, Person owner) {
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.createAt = new Date();
        this.owner = owner;
        this.completionDates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public String getFormatCreateAt() {
        // Создаем форматтер с кастомным форматом
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm:ss");

        // Применяем форматтер к дате
        return formatter.format(createAt);
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public List<Date> getCompletionDates() {
        return completionDates;
    }

    public void addCompletionDate(Date date) {
        this.completionDates.add(date);
    }

    public void removeCompletionDate(Date date) {
        this.completionDates.remove(date);
    }
}
