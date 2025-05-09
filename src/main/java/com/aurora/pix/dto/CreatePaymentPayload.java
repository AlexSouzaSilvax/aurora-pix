package com.aurora.pix.dto;

import java.math.BigDecimal;

public record CreatePaymentPayload(BigDecimal value, String owner) {

}
