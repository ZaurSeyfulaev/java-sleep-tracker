package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;
import java.util.List;

public class BadSleepSessionFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        long badSleepSessionCount = sleepSessions.stream()
                .filter(session -> session.getStatus().equals("BAD"))
                .count();
        return new SleepAnalysisResult("Количество плохих сессий сна", badSleepSessionCount);
    }
}
