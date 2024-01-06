package com.jar.storeLedger.mapper;

import com.jar.storeLedger.entity.Transaction;
import com.jar.storeLedger.model.TransactionRequest;
import com.jar.storeLedger.model.TransactionResponse;

import java.time.ZonedDateTime;

public class TransactionMapper {


    public static Transaction convertToTransaction(TransactionRequest request, Long kiranaStoreId) {
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(request.getTransactionAmount());
        if (request.getTransactionDate() != null) {
            transaction.setTransactionDate(request.getTransactionDate());
        } else {
            transaction.setTransactionDate(ZonedDateTime.now());
        }
        transaction.setTransactionType(request.getTransactionType());
        transaction.setCurrency(request.getCurrency());
        transaction.setDescription(request.getDescription());
        transaction.setKiranaStoreId(kiranaStoreId);
        return transaction;
    }

    public static TransactionResponse convertToTransactionResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setTransactionId(transaction.getTransactionId());
        response.setTransactionAmount(transaction.getTransactionAmount());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setTransactionType(transaction.getTransactionType());
        response.setCurrency(transaction.getCurrency());
        response.setDescription(transaction.getDescription());
        response.setKiranaStoreId(transaction.getKiranaStoreId());
        return response;
    }

}
