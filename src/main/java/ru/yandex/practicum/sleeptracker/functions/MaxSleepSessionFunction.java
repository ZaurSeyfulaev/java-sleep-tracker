package ru.yandex.practicum.sleeptracker.functions;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;
import java.util.List;
import java.util.Objects;

public class MaxSleepSessionFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        long max = sleepSessions.stream()
                .mapToLong(SleepSession::getSessionDuration)
                .filter(Objects::nonNull)
                .max()
                .orElse(0);
        return new SleepAnalysisResult("Максимальная продолжительность сна", max + " мин");
    }
}
