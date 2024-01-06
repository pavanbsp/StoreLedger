package com.jar.storeLedger.service;

import com.jar.storeLedger.entity.Transaction;
import com.jar.storeLedger.entity.repository.TransactionRepository;
import com.jar.storeLedger.mapper.TransactionMapper;
import com.jar.storeLedger.model.TransactionRequest;
import com.jar.storeLedger.model.TransactionResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService {

    TransactionRepository transactionRepository;

    public TransactionResponse recordTransaction(TransactionRequest request, Long kiranaStoreId) {
        Transaction transaction = TransactionMapper.convertToTransaction(request, kiranaStoreId);
        transactionRepository.save(transaction);
        return TransactionMapper.convertToTransactionResponse(transaction);
    }

    public TransactionResponse updateTransaction(TransactionRequest request, Long transactionId) {
        Transaction transaction = transactionRepository.select(transactionId);
        transaction.setTransactionAmount(request.getTransactionAmount());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setCurrency(request.getCurrency());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setDescription(request.getDescription());
        transactionRepository.save(transaction);
        return TransactionMapper.convertToTransactionResponse(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.select(transactionId);
        transactionRepository.delete(transaction);
    }

}
