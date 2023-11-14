package pl.mt;

import java.util.List;
import java.util.Optional;

public class BudgetApp {
    private final TransactionDao transactionDao;
    private final UserReader userReader;

    public BudgetApp() {
        this.userReader = new UserReader();
        this.transactionDao = new TransactionDao();
    }

    public void mainLoop() {
        boolean isRunning = true;
        do {
            String userInput = userReader.getOption();
            Optional<Option> optionFromInput = Option.getOptionFromInput(userInput);
            optionFromInput.ifPresentOrElse(
                    this::chooseOption,
                    () -> System.out.println("Błędna opcja.")
            );
            if (optionFromInput.isPresent() && optionFromInput.get() == Option.EXIT) {
                isRunning = false;
            }
        } while (isRunning);
    }

    private void chooseOption(Option option) {
        switch (option) {
            case ADD -> save();
            case MODIFY -> update();
            case DELETE -> delete();
            case READ -> read();
            case EXIT -> close();
            default -> System.out.println("Nieprawidłowa opcja - spróbuj ponownie.");
        }
    }

    private void save() {
        Transaction transaction = userReader.createTransaction();
        transactionDao.save(transaction);
        System.out.println("Transakcja została zapisana pomyślnie!");
    }

    private void update() {
        int id = userReader.getId();
        transactionDao.findById(id).ifPresentOrElse(
                transaction -> {
                    Transaction transactionToUpdate = userReader.createTransaction();
                    int updated = transactionDao.update(transactionToUpdate, id);
                    System.out.println("Zaktualizowano wpisów: " + updated);
                },
                () -> System.out.println("Brak transakcji o tym ID. Spróbuj ponownie.")
        );
    }

    private void delete() {
        int id = userReader.getId();
        transactionDao.findById(id).ifPresentOrElse(
                transaction -> {
                    int deleted = transactionDao.delete(id);
                    System.out.println("Usunięto wpisów: " + deleted);
                },
                () -> System.out.println("Brak transakcji o tym ID. Spróbuj ponownie.")
        );
    }

    private void read() {
        String userInput = userReader.chooseTransactionType();
        Optional<Type> typeFromInput = Type.getTypeFromInput(userInput);
        typeFromInput.ifPresentOrElse(
                this::showTransactions,
                () -> System.out.println("Błędny typ transakcji.")
        );
    }

    private void showTransactions(Type type) {
        List<Transaction> transactions = getList(type);
        if (transactions.isEmpty()) {
            System.out.println("Brak transakcji tego typu.");
        } else {
            transactions.forEach(System.out::println);
        }
        System.out.println();
    }

    private List<Transaction> getList(Type type) {
        return switch (type) {
            case INCOME -> transactionDao.readByType(Type.INCOME);
            case EXPENSE -> transactionDao.readByType(Type.EXPENSE);
        };
    }

    private void close() {
        transactionDao.close();
        System.out.println("Papa!");
    }
}
