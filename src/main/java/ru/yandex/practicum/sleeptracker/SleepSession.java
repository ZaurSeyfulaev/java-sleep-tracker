package ru.yandex.practicum.sleeptracker;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SleepSession {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    public SleepSession() {
    }

    public SleepSession(LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public List<SleepSession> parseSleepingSession(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            return bufferedReader.lines().toList().stream()
                    .map(line -> line.split(";"))
                    .filter(line -> line.length == 3)
                    .map(session -> {
                        LocalDateTime startTime = LocalDateTime.parse(session[0], FORMAT);
                        LocalDateTime endTime = LocalDateTime.parse(session[1], FORMAT);
                        String status = session[2];
                        return new SleepSession(startTime, endTime, status);
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public long getSessionDuration() {
        return Duration.between(startTime, endTime).toMinutes();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "SleepSession{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                '}';
    }
}
