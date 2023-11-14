package pl.mt;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Scanner;

public class UserReader {
    private final Scanner scanner;

    public UserReader() {
        this.scanner = new Scanner(System.in);
    }

    public String getOption() {
        System.out.println("<< Wybierz opcję: >>");
        for (Option opt : Option.values()) {
            System.out.println(opt);
        }
        return scanner.nextLine();
    }

    public Transaction createTransaction() {
        System.out.println("<< Podaj dane transakcji >>");
        Type type = getType();
        System.out.println("Podaj opis:");
        String description = scanner.nextLine();
        BigDecimal amount = getAmount();
        Date date = getDate();
        return new Transaction(type, description, amount, date);
    }

    private Type getType() {
        Optional<Type> typeFromInput;
        do {
            String userInput = chooseTransactionType();
            typeFromInput = Type.getTypeFromInput(userInput);
        } while (typeFromInput.isEmpty());
        return typeFromInput.get();
    }

    public String chooseTransactionType() {
        System.out.println("Wybierz typ transakcji:");
        for (Type value : Type.values()) {
            System.out.println(value);
        }
        return scanner.nextLine().toUpperCase();
    }

    private BigDecimal getAmount() {
        BigDecimal amount;
        do {
            System.out.println("Podaj kwotę (wartość dodatnia)):");
            amount = BigDecimal.valueOf(scanner.nextDouble());
            scanner.nextLine();
        } while (amount.compareTo(BigDecimal.ZERO) <= 0);
        return amount;
    }

    private Date getDate() {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        do {
            System.out.println("Podaj datę transakcji w formacie RRRR-MM-DD");
            String userInput = scanner.nextLine();
            try {
                date = new Date(dateFormat.parse(userInput).getTime());
            } catch (ParseException e) {
                System.out.println("Niepoprawny format daty. Spróbuj ponownie.");
            }
        } while (date == null);
        return date;
    }

    public int getId() {
        System.out.println("<< Podaj ID transakcji: >>");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }
}
