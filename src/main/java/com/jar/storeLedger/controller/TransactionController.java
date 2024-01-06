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

    /**
     * Endpoint to record a new transaction for a Kirana store.
     *
     * @param request       The request containing details of the transaction.
     * @param kiranaStoreId The ID of the Kirana store for which the transaction is recorded.
     * @return A response containing details of the recorded transaction.
     */
    @PreAuthorize("@permissionService.verifyAccess(#kiranaStoreId)")
    @PostMapping("/{kiranaStoreId}/record")
    public TransactionResponse recordTransaction(@RequestBody @Valid TransactionRequest request,
                                                 @PathVariable("kiranaStoreId") Long kiranaStoreId) {
        return transactionService.recordTransaction(request, kiranaStoreId);
    }

    /**
     * Endpoint to retrieve details of a specific transaction for a Kirana store.
     *
     * @param kiranaStoreId   The ID of the Kirana store.
     * @param transactionId   The ID of the transaction to retrieve.
     * @return A response containing details of the requested transaction.
     * @throws ApiException If there is an error while retrieving the transaction.
     */
    @PreAuthorize("@permissionService.verifyAccess(#kiranaStoreId)")
    @GetMapping("/{kiranaStoreId}/view/{transactionId}")
    public TransactionResponse getTransaction(@PathVariable("kiranaStoreId") Long kiranaStoreId,
                                              @PathVariable("transactionId") Long transactionId) throws ApiException {
        return transactionService.getTransaction(transactionId);
    }

    /**
     * Endpoint to retrieve a paginated list of transactions for a Kirana store.
     *
     * @param kiranaStoreId The ID of the Kirana store.
     * @param pageNo        The page number for pagination (default: 1).
     * @param pageSize      The number of transactions per page (default: 10).
     * @return A list of transactions for the specified Kirana store.
     * @throws ApiException If there is an error while retrieving the transactions.
     */
    @PreAuthorize("@permissionService.verifyAccess(#kiranaStoreId)")
    @GetMapping("/{kiranaStoreId}/view")
    public List<TransactionResponse> getTransactionList(@PathVariable("kiranaStoreId") Long kiranaStoreId,
                                                        @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws ApiException {
        return transactionService.getTransactionList(kiranaStoreId, pageNo, pageSize);
    }

    /**
     * Endpoint to update details of a specific transaction for a Kirana store.
     *
     * @param request         The request containing updated details of the transaction.
     * @param kiranaStoreId   The ID of the Kirana store.
     * @param transactionId   The ID of the transaction to update.
     * @return A response containing details of the updated transaction.
     * @throws ApiException If there is an error while updating the transaction.
     */
    @PreAuthorize("@permissionService.verifyAccess(#kiranaStoreId)")
    @PutMapping("/{kiranaStoreId}/update/{transactionId}")
    public TransactionResponse updateTransaction(@RequestBody @Valid TransactionRequest request,
                                                 @PathVariable("kiranaStoreId") Long kiranaStoreId,
                                                 @PathVariable("transactionId") Long transactionId) throws ApiException {
        return transactionService.updateTransaction(request, transactionId);
    }

    /**
     * Endpoint to delete a specific transaction for a Kirana store.
     *
     * @param kiranaStoreId   The ID of the Kirana store.
     * @param transactionId   The ID of the transaction to delete.
     * @throws ApiException If there is an error while deleting the transaction.
     */
    @PreAuthorize("@permissionService.verifyAccess(#kiranaStoreId)")
    @DeleteMapping("/{kiranaStoreId}/delete/{transactionId}")
    public void deleteTransaction(@PathVariable("kiranaStoreId") Long kiranaStoreId,
                                  @PathVariable("transactionId") Long transactionId) throws ApiException {
        transactionService.deleteTransaction(transactionId);
    }

    /**
     * Endpoint to download a comprehensive CSV report of transactions for a Kirana store.
     *
     * @param kiranaStoreId The ID of the Kirana store.
     * @param request       The request containing parameters for generating the report.
     */
    @PreAuthorize("@permissionService.verifyAccess(#kiranaStoreId)")
    @PostMapping("/{kiranaStoreId}/download-report")
    public void downloadReport(@PathVariable("kiranaStoreId") Long kiranaStoreId,
                               @RequestBody @Valid DownloadReportRequest request) {
        transactionService.generateReport(kiranaStoreId, request);
    }

}
