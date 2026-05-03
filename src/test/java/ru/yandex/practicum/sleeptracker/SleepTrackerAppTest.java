package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    List<SleepSession> sleepSessions;

    private SleepSession createSession(String sleepStart, String sleepStop, String status) {
        LocalDateTime start = LocalDateTime.parse(sleepStart, FORMATTER);
        LocalDateTime end = LocalDateTime.parse(sleepStop, FORMATTER);
        return new SleepSession(start, end, status);
    }

    @Test
    void shouldCalculateAverageDurationForMultipleSessions() {
        sleepSessions = List.of(
                createSession("01.10.25 22:00", "01.10.25 23:40", "GOOD"),   //100
                createSession("02.10.25 20:00", "02.10.25 23:20", "NORMAL"),  //200
                createSession("03.10.25 18:00", "03.10.25 23:00", "BAD")    //300
        );
        AverageSleepSessionFunction function = new AverageSleepSessionFunction();
        SleepAnalysisResult sleepAnalysisResult = function.apply(sleepSessions);
        assertEquals("200.0 мин", sleepAnalysisResult.toString());
    }

    @Test
    void shouldReturnZeroWhenListIsEmpty() {
        sleepSessions = List.of();
        AverageSleepSessionFunction function = new AverageSleepSessionFunction();
        SleepAnalysisResult sleepAnalysisResult = function.apply(sleepSessions);
        assertEquals("0.0 мин", sleepAnalysisResult.toString());
    }

    @Test
    void shouldBeTwoBadSleepSessions() {
        sleepSessions = List.of(
                createSession("01.10.25 22:00", "01.10.25 23:40", "GOOD"),
                createSession("02.10.25 20:00", "02.10.25 23:20", "NORMAL"),
                createSession("03.10.25 18:00", "03.10.25 23:00", "BAD"),
                createSession("03.10.25 18:00", "03.10.25 23:00", "BAD")
        );
        BadSleepSessionFunction function = new BadSleepSessionFunction();
        SleepAnalysisResult sleepAnalysisResult = function.apply(sleepSessions);
        assertEquals("2", sleepAnalysisResult.toString());
    }

    @Test
    void shouldBeZeroBadSleepSessions() {
        sleepSessions = List.of(
                createSession("01.10.25 22:00", "01.10.25 23:40", "GOOD"),
                createSession("02.10.25 20:00", "02.10.25 23:20", "NORMAL")
        );
        BadSleepSessionFunction function = new BadSleepSessionFunction();
        SleepAnalysisResult sleepAnalysisResult = function.apply(sleepSessions);
        assertEquals("0", sleepAnalysisResult.toString());
    }

    @Test
    void shouldReturnMaxSleepSessions() {
        sleepSessions = List.of(
                createSession("01.10.25 22:00", "01.10.25 23:40", "GOOD"),   //100
                createSession("02.10.25 20:00", "02.10.25 23:20", "NORMAL"),  //200
                createSession("03.10.25 18:00", "03.10.25 23:00", "BAD")); //300

        MaxSleepSessionFunction maxSleepSessionFunction = new MaxSleepSessionFunction();
        SleepAnalysisResult sleepAnalysisResult = maxSleepSessionFunction.apply(sleepSessions);
        assertEquals("300 мин", sleepAnalysisResult.toString());
    }

    @Test
    void shouldReturnMaxSleepSessionsIfAllSessionsAreEquals() {
        sleepSessions = List.of(
                createSession("03.10.25 18:00", "03.10.25 23:00", "BAD"), //300
                createSession("03.10.25 18:00", "03.10.25 23:00", "BAD")); //300

        MaxSleepSessionFunction maxSleepSessionFunction = new MaxSleepSessionFunction();
        SleepAnalysisResult sleepAnalysisResult = maxSleepSessionFunction.apply(sleepSessions);
        assertEquals("300 мин", sleepAnalysisResult.toString());
    }

    @Test
    void shouldReturnMinSleepSessions() {
        sleepSessions = List.of(
                createSession("01.10.25 22:00", "01.10.25 23:40", "GOOD"),   //100
                createSession("02.10.25 20:00", "02.10.25 23:20", "NORMAL"),  //200
                createSession("03.10.25 18:00", "03.10.25 23:00", "BAD")); //300

        MinSleepSessionFunction minSleepSessionFunction = new MinSleepSessionFunction();
        SleepAnalysisResult sleepAnalysisResult = minSleepSessionFunction.apply(sleepSessions);
        assertEquals("100 мин", sleepAnalysisResult.toString());
    }

    @Test
    void shouldReturnMinSleepSessionsIfAllSessionsAreEquals() {
        sleepSessions = List.of(
                createSession("03.10.25 18:00", "03.10.25 23:00", "BAD"), //300
                createSession("03.10.25 18:00", "03.10.25 23:00", "BAD")); //300

        MinSleepSessionFunction minSleepSessionFunction = new MinSleepSessionFunction();
        SleepAnalysisResult sleepAnalysisResult = minSleepSessionFunction.apply(sleepSessions);
        assertEquals("300 мин", sleepAnalysisResult.toString());
    }

    @Test
    void shouldBeTwoNoSleepNightCount() {
        sleepSessions = List.of(
                createSession("01.10.25 06:00", "01.10.25 08:00", "NORMAL"));

        NoSleepNightCountFunction noSleepNightCountFunction = new NoSleepNightCountFunction();
        SleepAnalysisResult sleepAnalysisResult = noSleepNightCountFunction.apply(sleepSessions);
        assertEquals("2", sleepAnalysisResult.toString());
    }

    @Test
    void shouldBeOneNoSleepNightCount() {
        sleepSessions = List.of(
                createSession("01.10.25 12:00", "01.10.25 23:40", "GOOD"));

        NoSleepNightCountFunction noSleepNightCountFunction = new NoSleepNightCountFunction();
        SleepAnalysisResult sleepAnalysisResult = noSleepNightCountFunction.apply(sleepSessions);
        assertEquals("1", sleepAnalysisResult.toString());
    }

    @Test
    void shouldBeZeroNoSleepNightCount() {
        sleepSessions = List.of(
                createSession("01.10.25 23:00", "02.10.25 03:00", "NORMAL")
        );
        NoSleepNightCountFunction noSleepNightCountFunction = new NoSleepNightCountFunction();
        SleepAnalysisResult sleepAnalysisResult = noSleepNightCountFunction.apply(sleepSessions);
        assertEquals("0", sleepAnalysisResult.toString());
    }

    @Test
    void shouldBeFiveNoSleepNightCount() {
        sleepSessions = List.of(
                createSession("01.10.25 10:00", "01.10.25 12:00", "BAD"),
                createSession("05.10.25 14:00", "05.10.25 16:00", "BAD")
        );
        NoSleepNightCountFunction noSleepNightCountFunction = new NoSleepNightCountFunction();
        SleepAnalysisResult sleepAnalysisResult = noSleepNightCountFunction.apply(sleepSessions);
        assertEquals("5", sleepAnalysisResult.toString());
    }

    @Test
    void shouldBeTwoCountSleepSessions() {
        sleepSessions = List.of(createSession("01.10.25 10:00", "01.10.25 12:00", "BAD"),
                createSession("05.10.25 14:00", "05.10.25 16:00", "BAD"));

        SleepSessionCountFunction sleepSessionCountFunction = new SleepSessionCountFunction();
        SleepAnalysisResult sleepAnalysisResult = sleepSessionCountFunction.apply(sleepSessions);
        assertEquals("2", sleepAnalysisResult.toString());
    }

    @Test
    void shouldBeZeroCountSleepSessions() {
        sleepSessions = List.of();
        SleepSessionCountFunction sleepSessionCountFunction = new SleepSessionCountFunction();
        SleepAnalysisResult sleepAnalysisResult = sleepSessionCountFunction.apply(sleepSessions);
        assertEquals("0", sleepAnalysisResult.toString());
    }

    @Test
    void shouldBeReturnOWL() {
        sleepSessions = List.of(
                createSession("05.10.25 23:01", "06.10.25 09:01", "BAD")
        );
        SleepChronotypeFunction sleepChronotypeFunction = new SleepChronotypeFunction();
        SleepAnalysisResult sleepAnalysisResult = sleepChronotypeFunction.apply(sleepSessions);
        assertEquals("Сова", sleepAnalysisResult.toString());
    }

    @Test
    void shouldBeReturnLARK() {
        sleepSessions = List.of(
                createSession("05.10.25 21:59", "06.10.25 06:59", "BAD")
        );
        SleepChronotypeFunction sleepChronotypeFunction = new SleepChronotypeFunction();
        SleepAnalysisResult sleepAnalysisResult = sleepChronotypeFunction.apply(sleepSessions);
        assertEquals("Жаворонок", sleepAnalysisResult.toString());
    }

    @Test
    void shouldBeReturnPIGEON() {
        sleepSessions = List.of(
                createSession("05.10.25 10:00", "06.10.25 10:00", "BAD")
        );
        SleepChronotypeFunction sleepChronotypeFunction = new SleepChronotypeFunction();
        SleepAnalysisResult sleepAnalysisResult = sleepChronotypeFunction.apply(sleepSessions);
        assertEquals("Голубь", sleepAnalysisResult.toString());
    }
}