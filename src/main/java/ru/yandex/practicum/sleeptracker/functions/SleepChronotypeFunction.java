package ru.yandex.practicum.sleeptracker.functions;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class SleepChronotypeFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Хронотип пользователя", "Голубь");
        }

        LocalDateTime firstStart = sessions.stream()
                .map(SleepSession::getStartTime)
                .min(LocalDateTime::compareTo)
                .get();

        LocalDateTime lastEnd = sessions.stream()
                .map(SleepSession::getEndTime)
                .max(LocalDateTime::compareTo)
                .get();

        LocalDate firstNight = firstStart.toLocalTime().isAfter(LocalTime.NOON)
                ? firstStart.toLocalDate().plusDays(1)
                : firstStart.toLocalDate();

        LocalDate lastNight = lastEnd.toLocalDate();

        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(firstNight, lastNight);

        int[] counts = Stream.iterate(firstNight, date -> date.plusDays(1))
                .limit(daysBetween + 1)
                .map(night -> {

                    LocalDateTime nightStart = night.atStartOfDay();
                    LocalDateTime nightEnd = night.atTime(6, 0);
                    return sessions.stream()
                            .filter(s -> !s.getEndTime().isBefore(nightStart) && !s.getStartTime().isAfter(nightEnd))
                            .findFirst()
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .map(session -> {
                    LocalTime bedtime = session.getStartTime().toLocalTime();
                    LocalTime wakeup = session.getEndTime().toLocalTime();
                    if (bedtime.isAfter(LocalTime.of(23, 0)) && wakeup.isAfter(LocalTime.of(9, 0))) {
                        return "owl";
                    } else if (bedtime.isBefore(LocalTime.of(22, 0)) && wakeup.isBefore(LocalTime.of(7, 0))) {
                        return "lark";
                    } else {
                        return "pigeon";
                    }
                })
                .reduce(new int[]{0, 0, 0},
                        (arr, type) -> {
                            if (type.equals("owl")) arr[0]++;
                            else if (type.equals("lark")) arr[1]++;
                            else arr[2]++;
                            return arr;
                        },
                        (arr1, arr2) -> new int[]{arr1[0] + arr2[0], arr1[1] + arr2[1], arr1[2] + arr2[2]}
                );

        int owl = counts[0];
        int lark = counts[1];
        int pigeon = counts[2];

        String result;
        if (owl > lark && owl > pigeon) {
            result = "Сова";
        } else if (lark > owl && lark > pigeon) {
            result = "Жаворонок";
        } else {
            result = "Голубь";
        }
        return new SleepAnalysisResult("Хронотип пользователя", result);
    }
}