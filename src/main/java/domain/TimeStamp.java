package domain;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TimeStamp {

    private int year;
    private int month;
    private int day;
    private int hour;


    // Formatted TimeStamp looks like {year='2020', month='12', day='14'}
    public TimeStamp(String jsonString){
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(jsonString);
        JsonObject rootObject = rootElement.getAsJsonObject();
        this.year = rootObject.getAsJsonPrimitive("year").getAsInt();
        this.month = rootObject.getAsJsonPrimitive("month").getAsInt();
        this.day = rootObject.getAsJsonPrimitive("day").getAsInt();
        this.hour = 0;
    }

    public TimeStamp(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = 9;
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

    @Override
    public String toString() {
        return "{" +
                "year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                '}';
    }
}
