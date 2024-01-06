package com.jar.storeLedger.model;

import com.jar.storeLedger.constants.TransactionType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
public class DownloadReportRequest {

    @NotNull(message = "Please enter valid startDate")
    private ZonedDateTime startDate;
    @NotNull(message = "Please enter valid endDate")
    private ZonedDateTime endDate;
    private TransactionType transactionType;

}
