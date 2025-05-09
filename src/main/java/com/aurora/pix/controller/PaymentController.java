package com.aurora.pix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurora.pix.dto.CreatePixQrCodeStaticPayload;
import com.aurora.pix.service.PaymentService;

@RestController
@RequestMapping("/api/payment/pix")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("create-qrcode-static")
    public ResponseEntity<Object> createPixQrCodeStatic(
            @RequestBody @Validated CreatePixQrCodeStaticPayload createPixQrCodeStaticPayload) {
        return paymentService.createPixQrCodeStatic(createPixQrCodeStaticPayload);
    }
}
