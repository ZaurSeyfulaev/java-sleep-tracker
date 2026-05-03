package ru.yandex.practicum.sleeptracker;
import ru.yandex.practicum.sleeptracker.functions.*;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SleepTrackerApp {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Укажите путь к файлу и его название \n");
        String path = scanner.nextLine();
        SleepSession sleepSession = new SleepSession();
        List<SleepSession> sleepSessions = sleepSession.parseSleepingSession(path);
        List<SleepAnalysisFunction> sleepAnalysisFunctions = List.of(
                new AverageSleepSessionFunction(),
                new BadSleepSessionFunction(),
                new MaxSleepSessionFunction(),
                new MinSleepSessionFunction(),
                new NoSleepNightCountFunction(),
                new SleepChronotypeFunction(),
                new SleepSessionCountFunction()
        );

        List<SleepAnalysisResult> sleepAnalysisResults = sleepAnalysisFunctions.stream()
                .map(result -> result.apply(sleepSessions))
                .toList();
        sleepAnalysisResults.forEach(SleepAnalysisResult::print);
    }

}

// /Users/zaursejfulaev/Desktop/yandex/dev/sprint8/src/main/resources/sleep_log.txt