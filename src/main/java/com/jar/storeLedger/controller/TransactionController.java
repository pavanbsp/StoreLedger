package com.jar.storeLedger.controller;

import com.jar.storeLedger.model.TransactionRequest;
import com.jar.storeLedger.model.TransactionResponse;
import com.jar.storeLedger.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {

    TransactionService transactionService;

    @PostMapping("/{kiranaStoreId}/record")
    public TransactionResponse recordTransaction(@RequestBody @Valid TransactionRequest request,
                                                 @PathVariable("kiranaStoreId") Long kiranaStoreId) {
        return transactionService.recordTransaction(request, kiranaStoreId);
    }

    @PutMapping("/{kiranaStoreId}/update/{transactionId}")
    public TransactionResponse updateTransaction(@RequestBody @Valid TransactionRequest request,
                                                 @PathVariable("kiranaStoreId") Long kiranaStoreId,
                                                 @PathVariable("transactionId") Long transactionId) {
        return transactionService.updateTransaction(request, transactionId);
    }

    @DeleteMapping("/{kiranaStoreId}/delete/{transactionId}")
    public void deleteTransaction(@PathVariable("kiranaStoreId") Long kiranaStoreId,
                                  @PathVariable("transactionId") Long transactionId) {
        transactionService.deleteTransaction(transactionId);
    }

}
