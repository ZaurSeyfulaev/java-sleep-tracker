package ru.yandex.practicum.sleeptracker.functions;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;
import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface SleepAnalysisFunction extends Function<List<SleepSession>, SleepAnalysisResult> {
}
