package ru.myspar.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Мужской"),
    FEMALE("Женский");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Gender fromString(String text) {
        for (Gender gender : Gender.values()) {
            if (gender.displayName.equalsIgnoreCase(text)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Недопустимое значение пола: " + text);
    }

}
