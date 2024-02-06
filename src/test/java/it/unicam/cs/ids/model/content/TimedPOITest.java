package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.Coordinate;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimedPOITest {

    @Test
    void testTimedPOI() {
        OffsetTime[][] openingTimes = new OffsetTime[7][7];
        OffsetTime[][] closingTimes = new OffsetTime[7][7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                openingTimes[i][j] = OffsetTime.of(LocalTime.of(8, 0), ZoneOffset.UTC);
                closingTimes[i][j] = OffsetTime.of(LocalTime.of(12, 0), ZoneOffset.UTC);
                openingTimes[i][j] = OffsetTime.of(LocalTime.of(14, 0), ZoneOffset.UTC);
                closingTimes[i][j] = OffsetTime.of(LocalTime.of(18, 0), ZoneOffset.UTC);
            }
        }
        TimedPOI timedPOI = new TimedPOI("name", "description", "createdBy", false, new Coordinate(0, 0), openingTimes, closingTimes);
        assertEquals(openingTimes, timedPOI.getOpeningTimes());
        assertEquals(closingTimes, timedPOI.getClosingTimes());
    }
}
