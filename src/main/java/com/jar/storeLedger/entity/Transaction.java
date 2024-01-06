package com.jar.storeLedger.entity;

import com.jar.storeLedger.constants.Currency;
import com.jar.storeLedger.constants.TransactionType;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private Long kiranaStoreId;

    @Column(nullable = false)
    private ZonedDateTime transactionDate;

    @Column(nullable = false)
    private double transactionAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String description;

}
