package pl.mt;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDao {
    private final Connection connection;

    public TransactionDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/budget?serverTimezone=UTC&characterEncoding=utf8",
                    "root",
                    "admin"
            );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Błąd sterownika.");
        } catch (SQLException e) {
            throw new RuntimeException("Błąd połączenia z bazą danych.");
        }
    }

    public void save(Transaction transaction) {
        final String sql = "INSERT INTO transaction (type, description, amount, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transaction.getType().name());
            statement.setString(2, transaction.getDescription());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setDate(4, transaction.getDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Błąd zapisu w bazie danych.");
        }
    }

    public List<Transaction> readByType(Type userType) {
        List<Transaction> transactions = new ArrayList<>();
        final String sql = "SELECT * FROM transaction WHERE type = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userType.name());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Type type = Type.valueOf(resultSet.getString("type"));
                String description = resultSet.getString("description");
                BigDecimal amount = BigDecimal.valueOf(resultSet.getDouble("amount"));
                Date date = Date.valueOf(resultSet.getString("date"));
                transactions.add(new Transaction(id, type, description, amount, date));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Błąd odczytu z bazy danych.");
        }
        return transactions;
    }

    public int delete(int id) {
        final String sql = "DELETE FROM transaction WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Błąd usuwania z bazy danych.");
        }
    }

    public Optional<Transaction> findById(int id) {
        final String sql = "SELECT * FROM transaction WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Type type = Type.valueOf(resultSet.getString("type"));
                String description = resultSet.getString("description");
                BigDecimal amount = BigDecimal.valueOf(resultSet.getDouble("amount"));
                Date date = Date.valueOf(resultSet.getString("date"));
                return Optional.of(new Transaction(id, type, description, amount, date));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Błąd wyszukiwania w bazie danych.");
        }
        return Optional.empty();
    }

    public int update(Transaction transaction, int id) {
        final String sql = "UPDATE transaction SET type = ?, description = ?, amount = ?, date = ?  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transaction.getType().name());
            statement.setString(2, transaction.getDescription());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setDate(4, transaction.getDate());
            statement.setInt(5, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Błąd modyfikacji w bazie danych.");
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Błąd podczas zakańczania połączenia.");
        }
    }
}
