package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;

import java.util.List;
import java.util.Objects;

public class AverageSleepSessionFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        double average = sleepSessions.stream()
                .mapToLong(SleepSession::getSessionDuration)
                .filter(Objects::nonNull)
                .average()
                .orElse(0);
        return new SleepAnalysisResult("Средняя продолжительность сна", average + " мин");
    }
}
