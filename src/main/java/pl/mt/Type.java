package pl.mt;

import java.util.Optional;

public enum Type {
    INCOME("Przychód", "P"),
    EXPENSE("Wydatek", "W");

    private final String plName;
    private final String abbreviation;

    Type(String plName, String abbreviation) {
        this.plName = plName;
        this.abbreviation = abbreviation;
    }

    public static Optional<Type> getTypeFromInput(String userInput) {
        for (Type value : Type.values()) {
            if (userInput.equals(value.abbreviation)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return abbreviation + " >> " + plName;
    }
}
