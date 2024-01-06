package com.jar.storeLedger.model;

import lombok.Data;

@Data
public class TransactionResponse extends TransactionRequest {

    private Long transactionId;

    private Long KiranaStoreId;

}
