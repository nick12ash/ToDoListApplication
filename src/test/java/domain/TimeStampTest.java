package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeStampTest {



    TimeStamp newStamp = new TimeStamp("{year='2020', month='12', day='14'}");

    @Test
    void getYear() {
        assertEquals(2020, newStamp.getYear());
    }

    @Test
    void getMonth() {
        assertEquals(12, newStamp.getMonth());
    }

    @Test
    void getDay() {
        assertEquals(14, newStamp.getDay());
    }

    @Test
    void getHour() {
        assertEquals(0, newStamp.getHour());
    }

}