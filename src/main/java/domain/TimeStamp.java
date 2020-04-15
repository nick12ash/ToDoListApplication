package domain;

public class TimeStamp {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;


    // Formatted TimeStamp looks like 2017-01-23T18:35:23.669Z
    public TimeStamp(String formattedTimeStamp){
        this.year = Integer.parseInt(formattedTimeStamp.substring(0,4));
        this.month = Integer.parseInt(formattedTimeStamp.substring(5,7));
        this.day = Integer.parseInt(formattedTimeStamp.substring(8,10));
        this.hour = Integer.parseInt(formattedTimeStamp.substring(11,13));
        this.minute = Integer.parseInt(formattedTimeStamp.substring(14,16));
        this.second = Integer.parseInt(formattedTimeStamp.substring(17,19));
    }

    public TimeStamp(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
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

    @Override
    public String toString() {
        return "{" +
                "year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                '}';
    }
}
