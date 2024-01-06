package com.jar.storeLedger.controller;

import com.jar.storeLedger.exception.ApiException;
import com.jar.storeLedger.model.DownloadReportRequest;
import com.jar.storeLedger.model.TransactionRequest;
import com.jar.storeLedger.model.TransactionResponse;
import com.jar.storeLedger.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {

    TransactionService transactionService;

    @PreAuthorize("@permissionService.verifyAccess(#kiranaStoreId)")
    @PostMapping("/{kiranaStoreId}/record")
    public TransactionResponse recordTransaction(@RequestBody @Valid TransactionRequest request,
                                                 @PathVariable("kiranaStoreId") Long kiranaStoreId) {
        return transactionService.recordTransaction(request, kiranaStoreId);
    }

    @PreAuthorize("@permissionService.verifyAccess(#kiranaStoreId)")
    @GetMapping("/{kiranaStoreId}/view/{transactionId}")
    public TransactionResponse getTransaction(@PathVariable("kiranaStoreId") Long kiranaStoreId,
                                              @PathVariable("transactionId") Long transactionId) throws ApiException {
        return transactionService.getTransaction(transactionId);
    }

    @PreAuthorize("@permissionService.verifyAccess(#kiranaStoreId)")
    @GetMapping("/{kiranaStoreId}/view")
    public List<TransactionResponse> getTransactionList(@PathVariable("kiranaStoreId") Long kiranaStoreId,
                                                        @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws ApiException {
        return transactionService.getTransactionList(kiranaStoreId, pageNo, pageSize);
    }

    @PreAuthorize("@permissionService.verifyAccess(#kiranaStoreId)")
    @PutMapping("/{kiranaStoreId}/update/{transactionId}")
    public TransactionResponse updateTransaction(@RequestBody @Valid TransactionRequest request,
                                                 @PathVariable("kiranaStoreId") Long kiranaStoreId,
                                                 @PathVariable("transactionId") Long transactionId) throws ApiException {
        return transactionService.updateTransaction(request, transactionId);
    }

    @PreAuthorize("@permissionService.verifyAccess(#kiranaStoreId)")
    @DeleteMapping("/{kiranaStoreId}/delete/{transactionId}")
    public void deleteTransaction(@PathVariable("kiranaStoreId") Long kiranaStoreId,
                                  @PathVariable("transactionId") Long transactionId) throws ApiException {
        transactionService.deleteTransaction(transactionId);
    }

    @PreAuthorize("@permissionService.verifyAccess(#kiranaStoreId)")
    @GetMapping("/{kiranaStoreId}/download-report")
    public void downloadReport(@PathVariable("kiranaStoreId") Long kiranaStoreId,
                               @RequestBody @Valid DownloadReportRequest request) {
        transactionService.generateReport(kiranaStoreId, request);
    }

}
