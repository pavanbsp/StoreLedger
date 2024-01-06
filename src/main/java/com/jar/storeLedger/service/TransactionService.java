package com.jar.storeLedger.service;

import com.jar.storeLedger.constants.Currency;
import com.jar.storeLedger.constants.TransactionType;
import com.jar.storeLedger.entity.Transaction;
import com.jar.storeLedger.entity.repository.TransactionRepository;
import com.jar.storeLedger.exception.ApiException;
import com.jar.storeLedger.mapper.TransactionMapper;
import com.jar.storeLedger.model.DownloadReportRequest;
import com.jar.storeLedger.model.ExchangeRateData;
import com.jar.storeLedger.model.TransactionRequest;
import com.jar.storeLedger.model.TransactionResponse;
import com.opencsv.CSVWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService {

    TransactionRepository transactionRepository;
    RestTemplate restTemplate = new RestTemplate();

    public TransactionResponse recordTransaction(TransactionRequest request, Long kiranaStoreId) {
        Transaction transaction = TransactionMapper.convertToTransaction(request, kiranaStoreId);
        transactionRepository.save(transaction);
        return TransactionMapper.convertToTransactionResponse(transaction);
    }

    public TransactionResponse getTransaction(Long transactionId) throws ApiException {
        return TransactionMapper.convertToTransactionResponse(transactionRepository.get(transactionId));
    }

    public List<TransactionResponse> getTransactionList(Long kiranaStoreId, int pageNo, int pageSize) {
        List<Transaction> transactionList = transactionRepository.getTransactionsByFilters(kiranaStoreId, pageNo, pageSize);
        return transactionList.stream()
                .map(TransactionMapper::convertToTransactionResponse).collect(Collectors.toList());
    }

    public TransactionResponse updateTransaction(TransactionRequest request, Long transactionId) throws ApiException {
        Transaction transaction = transactionRepository.get(transactionId);
        transaction.setTransactionAmount(request.getTransactionAmount());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setCurrency(request.getCurrency());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setDescription(request.getDescription());
        transactionRepository.save(transaction);
        return TransactionMapper.convertToTransactionResponse(transaction);
    }

    public void deleteTransaction(Long transactionId) throws ApiException {
        Transaction transaction = transactionRepository.get(transactionId);
        transactionRepository.delete(transaction);
    }

    public void generateReport(Long transactionId, DownloadReportRequest request) {
        List<Transaction> transactionList;
        if (request.getTransactionType() == null) {
            transactionList = transactionRepository.getTransactionReportWithDate(transactionId, request.getStartDate(),
                    request.getEndDate());
        } else {
            transactionList = transactionRepository.getTransactionReportWithDateAndType(transactionId, request.getStartDate(),
                    request.getEndDate(), request.getTransactionType());
        }
        String fxRatesBaseUrl = "https://api.fxratesapi.com/latest";
        ExchangeRateData data = restTemplate.exchange(fxRatesBaseUrl, HttpMethod.GET, null, ExchangeRateData.class).getBody();

        double totalCreditAmount = getTotalAmountByType(transactionList, TransactionType.CREDIT, data);
        double totalDebitAmount = getTotalAmountByType(transactionList, TransactionType.DEBIT, data);
        downloadAsCsv(transactionList, totalCreditAmount, totalDebitAmount, data);
    }

    private double getTotalAmountByType(List<Transaction> transactionList, TransactionType transactionType, ExchangeRateData data) {
        return transactionList.stream()
                .filter(transaction -> transaction.getTransactionType() == transactionType)
                .mapToDouble(transaction -> getAmountInINR(transaction.getTransactionAmount(), transaction.getCurrency(), data))
                .sum();
    }

    private void downloadAsCsv(List<Transaction> transactionList, double totalCreditAmount, double totalDebitAmount, ExchangeRateData exchangeRateData) {
        String csvFilePath = transactionList.get(0).getKiranaStoreId() + "_transactions.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            // Writing header
            String[] header = {"transactionId", "transactionDate", "transactionAmount",
                    "transactionType", "currency", "description", "Amount(INR)"};
            writer.writeNext(header);
            for (Transaction transaction : transactionList) {
                String[] data = {
                        String.valueOf(transaction.getTransactionId()),
                        transaction.getTransactionDate().toString(),
                        String.valueOf(transaction.getTransactionAmount()),
                        transaction.getTransactionType().name(),
                        transaction.getCurrency().name(),
                        transaction.getDescription(),
                        String.valueOf(getAmountInINR(transaction.getTransactionAmount(), transaction.getCurrency(), exchangeRateData))
                };
                writer.writeNext(data);
            }
            // Write total credit amount, total debit amount, and net amount
            String[] totalCreditRow = {"Total Credit Amount", "", String.valueOf(totalCreditAmount), "", "", ""};
            String[] totalDebitRow = {"Total Debit Amount", "", String.valueOf(totalDebitAmount), "", "", ""};
            String[] netAmountRow = {"Net Amount", "", String.valueOf(totalCreditAmount - totalDebitAmount), "", "", ""};

            writer.writeNext(new String[]{});
            writer.writeNext(totalCreditRow);
            writer.writeNext(totalDebitRow);
            writer.writeNext(netAmountRow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double getAmountInINR(double amount, Currency currency, ExchangeRateData data) {
        if (currency == Currency.INR)
            return amount;
        return amount * data.getRates().get("INR");
    }

}