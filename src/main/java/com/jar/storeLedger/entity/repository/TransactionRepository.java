package com.jar.storeLedger.entity.repository;

import com.jar.storeLedger.constants.TransactionType;
import com.jar.storeLedger.entity.Transaction;
import com.jar.storeLedger.exception.ApiException;
import com.jar.storeLedger.exception.ErrorCode;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class TransactionRepository extends AbstractRepository<Transaction> {

    private static final String SELECT_TRANSACTIONS_BY_KIRANA_STORE_ID = "SELECT t FROM Transaction t WHERE " +
            "kiranaStoreId = :kiranaStoreId ORDER BY transactionDate DESC";
    private static final String SELECT_TRANSACTIONS_BY_DATE = "SELECT t FROM Transaction t WHERE kiranaStoreId = :kiranaStoreId " +
            "AND transactionDate >= :startDate AND transactionDate <= :endDate ORDER BY transactionDate DESC";
    private static final String SELECT_TRANSACTIONS_BY_DATE_AND_TYPE = "SELECT t FROM Transaction t WHERE kiranaStoreId = :kiranaStoreId " +
            "AND transactionDate >= :startDate AND transactionDate <= :endDate AND transactionType = :transactionType ORDER BY transactionDate DESC";

    public TransactionRepository() {
        super(Transaction.class);
    }

    public Transaction get(Long transactionId) throws ApiException {
        Transaction transaction = select(transactionId);
        if (transaction == null) {
            throw new ApiException("Transaction doesn't exists", ErrorCode.NOT_FOUND);
        }
        return transaction;
    }

    public List<Transaction> getTransactionsByFilters(Long kiranaStoreId, int pageNo, int pageSize) {
        TypedQuery<Transaction> query = createJpqlQuery(SELECT_TRANSACTIONS_BY_KIRANA_STORE_ID);
        query.setParameter("kiranaStoreId", kiranaStoreId);
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public List<Transaction> getTransactionReportWithDate(Long kiranaStoreId, ZonedDateTime startDate, ZonedDateTime endDate) {
        TypedQuery<Transaction> query = createJpqlQuery(SELECT_TRANSACTIONS_BY_DATE);
        query.setParameter("kiranaStoreId", kiranaStoreId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public List<Transaction> getTransactionReportWithDateAndType(Long kiranaStoreId, ZonedDateTime startDate,
                                                                 ZonedDateTime endDate, TransactionType transactionType) {
        TypedQuery<Transaction> query = createJpqlQuery(SELECT_TRANSACTIONS_BY_DATE_AND_TYPE);
        query.setParameter("kiranaStoreId", kiranaStoreId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("transactionType", transactionType);
        return query.getResultList();
    }

}
