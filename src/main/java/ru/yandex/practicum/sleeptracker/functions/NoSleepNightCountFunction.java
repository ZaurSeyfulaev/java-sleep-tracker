package ru.yandex.practicum.sleeptracker.functions;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class NoSleepNightCountFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult("Бессонные ночи", 0L);
        }

        LocalDateTime firstStart = sessions.stream()
                .map(SleepSession::getStartTime)
                .min(LocalDateTime::compareTo)
                .orElseThrow();
        LocalDateTime lastEnd = sessions.stream()
                .map(SleepSession::getEndTime)
                .max(LocalDateTime::compareTo)
                .orElseThrow();

        LocalDate firstNight;
        if (firstStart.toLocalTime().isAfter(LocalTime.NOON)) {
            firstNight = firstStart.toLocalDate().plusDays(1);
        } else {
            firstNight = firstStart.toLocalDate().minusDays(1);
        }

        LocalDate lastNight;
        if (lastEnd.toLocalTime().isAfter(LocalTime.NOON)) {
            lastNight = lastEnd.toLocalDate().minusDays(1);
        } else {
            lastNight = lastEnd.toLocalDate();
        }

        long sleeplessNights = 0;
        LocalDate current = firstNight;
        while (!current.isAfter(lastNight)) {
            LocalDateTime nightStart = current.atStartOfDay();
            LocalDateTime nightEnd = current.atTime(6, 0);

            boolean isCovered = sessions.stream().anyMatch(session ->
                    session.getStartTime().isBefore(nightEnd) &&
                            session.getEndTime().isAfter(nightStart)
            );
            if (!isCovered) {
                sleeplessNights++;
            }
            current = current.plusDays(1);
        }
        return new SleepAnalysisResult("Бессонные ночи", sleeplessNights);
    }
}