package com.aurora.pix.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePixQrCodeStaticPayload {

    private String addressKey;

    private String description;

    private BigDecimal value;

    private String format;

    public CreatePixQrCodeStaticPayload(CreatePaymentPayload createPaymentPayload) {
        this.description = createPaymentPayload.owner();
        this.value = createPaymentPayload.value();
        this.format = "ALL";
    }

}
