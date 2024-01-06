package com.jar.storeLedger.entity.repository;

import com.jar.storeLedger.entity.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository extends AbstractRepository<Transaction> {

    public TransactionRepository() {
        super(Transaction.class);
    }

}
