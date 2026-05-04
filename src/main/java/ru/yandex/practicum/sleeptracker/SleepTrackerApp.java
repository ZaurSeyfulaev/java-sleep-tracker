package ru.yandex.practicum.sleeptracker;
import ru.yandex.practicum.sleeptracker.functions.*;
import java.util.List;
import java.util.Objects;

public class SleepTrackerApp {

    public static void main(String[] args) {

        String path = "";
        if (args.length == 0) {
            System.out.println("Пожалуйста укажите пусть к файлу");
            System.exit(0); // Идея подсказала
        } else if (args.length > 1) {
            System.out.println("Нельзя указать более 1 пути к файлу");
            System.exit(0);
        } else {
             path = args[0];
        }

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
                .filter(Objects::nonNull)
                .map(result -> result.apply(sleepSessions))
                .toList();
        sleepAnalysisResults.forEach(SleepAnalysisResult::print);
    }
}