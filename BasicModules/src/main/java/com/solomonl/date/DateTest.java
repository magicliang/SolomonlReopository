package com.solomonl.date;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by liangchuan on 2017/6/14.
 */
public class DateTest {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        System.out.println(date.getTime());

        Clock clock = Clock.systemUTC();
        System.out.println(ZoneId.getAvailableZoneIds());
        LocalDate dateFromClock = LocalDate.now( clock );
        LocalTime timeFromClock = LocalTime.now( clock );
        System.out.println(clock.millis());
        System.out.println(dateFromClock);
        System.out.println(timeFromClock);

        // Change zone does not change milliseconds.
        clock = clock.withZone(ZoneId.of("Pacific/Pago_Pago"));
        dateFromClock = LocalDate.now( clock );
        timeFromClock = LocalTime.now( clock );
        System.out.println(clock.millis());
        System.out.println(dateFromClock);
        System.out.println(timeFromClock);
    }
}
