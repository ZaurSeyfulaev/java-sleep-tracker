package ru.yandex.practicum.sleeptracker.functions;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;
import java.util.List;
import java.util.Objects;

public class MinSleepSessionFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        long min = sleepSessions.stream()
                .mapToLong(SleepSession::getSessionDuration)
                .filter(Objects::nonNull)
                .min()
                .orElse(0);
        return new SleepAnalysisResult("Минимальная сессия сна ", min + " мин");
    }
}
