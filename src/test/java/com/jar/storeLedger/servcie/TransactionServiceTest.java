package com.jar.storeLedger.servcie;

import com.jar.storeLedger.constants.Currency;
import com.jar.storeLedger.constants.TransactionType;
import com.jar.storeLedger.entity.KiranaStore;
import com.jar.storeLedger.entity.Transaction;
import com.jar.storeLedger.entity.repository.KiranaStoreRepository;
import com.jar.storeLedger.entity.repository.TransactionRepository;
import com.jar.storeLedger.exception.ApiException;
import com.jar.storeLedger.model.TransactionRequest;
import com.jar.storeLedger.model.TransactionResponse;
import com.jar.storeLedger.service.TransactionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionServiceTest extends AbstractServiceTest {

    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    KiranaStoreRepository kiranaStoreRepository;

    @Before
    public void setup() {
        KiranaStore kiranaStore = getTestStore();
        kiranaStoreRepository.save(kiranaStore);
    }

    @Test
    public void testRecordTransaction() throws ApiException {
        KiranaStore kiranaStore = kiranaStoreRepository.getByMobile(getTestStore().getMobile());
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setTransactionAmount(122);
        transactionRequest.setTransactionType(TransactionType.CREDIT);
        transactionRequest.setCurrency(Currency.INR);
        transactionRequest.setTransactionDate(ZonedDateTime.now());
        transactionRequest.setDescription("now");

        TransactionResponse response = transactionService.recordTransaction(transactionRequest, kiranaStore.getId());

        Transaction transaction = transactionRepository.get(response.getTransactionId());
        assertNotNull(transaction);
        assertEquals(transaction.getTransactionAmount(), transactionRequest.getTransactionAmount(), 0.0001);
        assertEquals(transaction.getTransactionType(), transactionRequest.getTransactionType());
        assertEquals(transaction.getCurrency(), transactionRequest.getCurrency());
    }

    public KiranaStore getTestStore() {
        KiranaStore kiranaStore = new KiranaStore();
        kiranaStore.setStoreName("dummy");
        kiranaStore.setMobile("9876543210");
        return kiranaStore;
    }

}
