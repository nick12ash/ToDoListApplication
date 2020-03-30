package domain;

public class TimeStamp {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public TimeStamp(int aYear, int aMonth, int aDay, int aHour, int aMinute, int aSecond){
        this.year = aYear;
        this.month = aMonth;
        this.day = aDay;
        this.hour = aHour;
        this.minute = aMinute;
        this.second = aSecond;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
}
