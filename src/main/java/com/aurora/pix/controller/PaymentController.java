package com.aurora.pix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurora.pix.dto.CreatePaymentPayload;
import com.aurora.pix.model.Payment;
import com.aurora.pix.service.PaymentService;

@RestController
@RequestMapping("/api/payment/pix")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("create-qrcode-static")
    public ResponseEntity<Object> createPixQrCodeStatic(
            @RequestBody @Validated CreatePaymentPayload createPaymentPayload) {
        return paymentService.createPixQrCodeStatic(createPaymentPayload);
    }

    @GetMapping("confirm-payment")
    public ResponseEntity<Boolean> confirmPayment(@RequestParam("id_qr_code_static") String idQrCodeStatic) {
        return paymentService.confirmPayment(idQrCodeStatic);
    }

    @GetMapping("payments")
    public ResponseEntity<List<Payment>> findAllPayments() {
        return paymentService.findAllPayments();
    }
}
