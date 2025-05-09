package com.aurora.pix.dto;

import java.util.LinkedHashMap;

import lombok.Data;

@Data
public class CreatePixQrCodeStaticResponse {

    private String qrCodeBase64;

    private String pixCode;

    public CreatePixQrCodeStaticResponse(LinkedHashMap<String, Object> response) {
        this.qrCodeBase64 = (String) response.get("encodedImage");
        this.pixCode = (String) response.get("payload");
    }

}
