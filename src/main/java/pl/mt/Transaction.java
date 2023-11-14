package pl.mt;

import java.math.BigDecimal;
import java.sql.Date;

public class Transaction {
    private Integer id;
    private Type type;
    private String description;
    private BigDecimal amount;
    private Date date;

    public Transaction(Type type, String description, BigDecimal amount, Date date) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(Integer id, Type type, String description, BigDecimal amount, Date date) {
        this(type, description, amount, date);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return id +
                " :: " + type +
                " :: " + description +
                " :: " + amount +
                " :: " + date;
    }
}
