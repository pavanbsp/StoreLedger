package com.jar.storeLedger.model;

import com.jar.storeLedger.constants.Currency;
import com.jar.storeLedger.constants.TransactionType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
public class TransactionRequest {

    @DecimalMin(value = "0", inclusive = false, message = "Please enter valid transactionAmount")
    private double transactionAmount;

    @NotNull(message = "Please enter valid transactionType")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull(message = "Please enter valid currency type")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private ZonedDateTime transactionDate;

    private String description;

}
