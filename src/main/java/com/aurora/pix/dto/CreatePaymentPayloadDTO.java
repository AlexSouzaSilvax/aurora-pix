package com.aurora.pix.dto;

import java.math.BigDecimal;

public record CreatePaymentPayloadDTO(BigDecimal value, String owner) {

}
