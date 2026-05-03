package ru.yandex.practicum.sleeptracker.functions;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;
import java.util.List;

public class SleepSessionCountFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSession) {
        return new SleepAnalysisResult("Общее количество сессий сна", sleepSession.size());
    }
}
