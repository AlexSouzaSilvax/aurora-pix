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

    public CreatePixQrCodeStaticPayload(String description, BigDecimal value) {
        this.description = description;
        this.value = value;
        this.format = "ALL";
    }

}
