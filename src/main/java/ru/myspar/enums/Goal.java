package ru.myspar.enums;

import lombok.Getter;

@Getter
public enum Goal {
    WEIGHT_LOSS("Похудение"),
    MAINTENANCE("Поддержание"),
    WEIGHT_GAIN("Набор массы");

    private final String displayName;

    Goal(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Goal fromString(String text) {
        for (Goal goal : Goal.values()) {
            if (goal.displayName.equalsIgnoreCase(text)) {
                return goal;
            }
        }
        throw new IllegalArgumentException("Недопустимое значение: " + text);
    }
}