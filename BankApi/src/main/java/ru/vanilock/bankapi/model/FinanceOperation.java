package ru.vanilock.bankapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "finance_operations")
public class FinanceOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    @JsonIgnore()
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = true)
    private User recipient;

    @NonNull
    @Column(name = "operation", nullable = false)
    private String operation;

    @NonNull
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "date")
    private LocalDate date;

    public FinanceOperation(@NonNull User sender, @NonNull String operation, @NonNull BigDecimal value,
                            LocalDate date) {
        this.sender = sender;
        this.operation = operation;
        this.amount = value;
        this.date = date;
    }

    public FinanceOperation(@NonNull User sender, User recipient, @NonNull String operation, @NonNull BigDecimal value,
                            LocalDate date) {
        this.sender = sender;
        this.recipient = recipient;
        this.operation = operation;
        this.amount = value;
        this.date = date;
    }

    public FinanceOperation(long id, @NonNull User sender, @NonNull String operation, @NonNull BigDecimal value, LocalDate date) {
        this.id = id;
        this.sender = sender;
        this.operation = operation;
        this.amount = value;
        this.date = date;
    }
}
