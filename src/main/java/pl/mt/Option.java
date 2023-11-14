package pl.mt;

import java.util.Optional;

public enum Option {
    ADD("1", "dodawanie transakcji"),
    MODIFY("2", "modyfikacja transakcji"),
    DELETE("3", "usuwanie transakcji"),
    READ("4", "wyświetlanie przychodów lub wydatków"),
    EXIT("0", "koniec");

    private final String option;
    private final String description;

    Option(String option, String description) {
        this.option = option;
        this.description = description;
    }

    public static Optional<Option> getOptionFromInput(String userInput) {
        for (Option value : Option.values()) {
            if (userInput.equals(value.option)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return option + " - " + description;
    }
}
