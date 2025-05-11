package com.aurora.pix.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Objects;

import com.aurora.pix.enums.StatusPayment;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ConfirmPaymentResponse {

    private String owner;

    private String idQRCodeStatic;

    @Enumerated(EnumType.STRING)
    private StatusPayment status;

    private LocalDateTime paidDate;

    private BigDecimal value;

    private String paymentReceiptUrl;

    public ConfirmPaymentResponse(LinkedHashMap<String, Object> response) {
        this.owner = Objects.toString(response.get("description"), "Não informado");
        this.idQRCodeStatic = (String) response.get("conciliationIdentifier");
        this.status = StatusPayment.valueOf(response.get("status").toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(response.get("effectiveDate").toString(), formatter);
        this.paidDate = dateTime;
        this.value = new BigDecimal(response.get("value").toString());
        this.paymentReceiptUrl = (String) response.get("transactionReceiptUrl");
    }

}
