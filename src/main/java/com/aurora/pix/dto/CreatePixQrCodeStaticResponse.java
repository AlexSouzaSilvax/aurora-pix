package com.aurora.pix.dto;

import java.util.LinkedHashMap;

import lombok.Data;

@Data
public class CreatePixQrCodeStaticResponse {

    private String idQRCodeStatic;

    private String qrCodeBase64;

    private String pixCode;

    public CreatePixQrCodeStaticResponse(LinkedHashMap<String, Object> response) {
        this.idQRCodeStatic = (String) response.get("id");
        this.qrCodeBase64 = (String) response.get("encodedImage");
        this.pixCode = (String) response.get("payload");
    }

}
