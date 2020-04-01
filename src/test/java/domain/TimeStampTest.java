package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeStampTest {

    TimeStamp newStamp = new TimeStamp("2017-01-23T18:35:23.669Z");

    @Test
    void getYear() {
        assertEquals(2017, newStamp.getYear());
    }

    @Test
    void getMonth() {
        assertEquals(01, newStamp.getMonth());
    }

    @Test
    void getDay() {
        assertEquals(23, newStamp.getDay());
    }

    @Test
    void getHour() {
        assertEquals(18, newStamp.getHour());
    }

    @Test
    void getMinute() {
        assertEquals(35, newStamp.getMinute());
    }

    @Test
    void getSecond() {
        assertEquals(23, newStamp.getSecond());
    }
}