package utils;

import domain.TimeStamp;
import domain.ToDoItem;

import java.time.LocalDate;
import java.time.Period;

public class Reminder {

    private LocalDate dateNow = LocalDate.now();
    private LocalDate dueDate;
    public String message;
    private Period period;


    public Reminder(ToDoItem item){
        TimeStamp timeStamp = new TimeStamp(item.dueDate);
        this.dueDate = LocalDate.of(timeStamp.getYear(), timeStamp.getMonth(), timeStamp.getDay());
        this.message = "Your '" + item.about + "' is almost due!";
        this.period = Period.between(dateNow, dueDate);
    }

    public int timeLeft() {
        return daysLeft();
    }

    public String getMessage(){
        String unitOfMeasure;
        int measurement;
        String pluralizer = "s";
        if(yearsLeft() > 0){
            unitOfMeasure = "year";
            measurement = yearsLeft();
        }
        else if(monthsLeft() > 0){
            unitOfMeasure = "month";
            measurement = monthsLeft();
        }
        else{
            unitOfMeasure = "day";
            measurement = daysLeft();
        }
        if (measurement != 1 || measurement != -1){
            unitOfMeasure += pluralizer;
        }
        return String.format("%s\nYou have %d %s to complete this item", message, measurement, unitOfMeasure);
    }

    public int daysLeft(){
        return period.getDays();
    }

    public int monthsLeft(){
        return  period.getMonths();
    }

    public int yearsLeft(){
        return period.getYears();
    }



}
