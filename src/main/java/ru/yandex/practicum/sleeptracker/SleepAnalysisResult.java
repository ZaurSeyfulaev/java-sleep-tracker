package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    private final String description;
    private final Object value;

    public SleepAnalysisResult(String description, Object value) {
        this.description = description;
        this.value = value;
    }

    public void print() {
        System.out.println(description + " : " + value);
    }

    // Требуется для тестов
    @Override
    public String toString() {
        return value.toString();

    }
}
