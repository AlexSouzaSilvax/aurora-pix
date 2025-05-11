package com.aurora.pix.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.aurora.pix.dto.AssasHttpResponse;
import com.aurora.pix.dto.ConfirmPaymentResponse;
import com.aurora.pix.dto.CreatePaymentPayload;
import com.aurora.pix.dto.CreatePixQrCodeStaticPayload;
import com.aurora.pix.dto.CreatePixQrCodeStaticResponse;
import com.aurora.pix.exceptions.ApiErrorException;
import com.aurora.pix.model.Payment;
import com.aurora.pix.repository.PaymentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentService {

    PaymentRepository paymentRepository;

    AssasService assasService;

    public ResponseEntity<Object> createPixQrCodeStatic(
            CreatePaymentPayload createPaymentPayload) {

        try {
            CreatePixQrCodeStaticPayload createPixQrCodeStaticPayload = new CreatePixQrCodeStaticPayload(
                    createPaymentPayload);

            createPixQrCodeStaticPayload.setAddressKey(getKeyPixCodeAssas());

            AssasHttpResponse createPixQrCodeStaticResponse = assasService
                    .createPixQrCodeStatic(createPixQrCodeStaticPayload);

            if (createPixQrCodeStaticResponse.isSuccess()) {

                Payment newPayment = new Payment(createPixQrCodeStaticPayload,
                        new CreatePixQrCodeStaticResponse(createPixQrCodeStaticResponse.getData()));

                newPayment = paymentRepository.save(newPayment);

                return ResponseEntity.ok(newPayment);
            } else {
                return ResponseEntity.status(createPixQrCodeStaticResponse.getCode())
                        .header("message", createPixQrCodeStaticResponse.getMessage()).body(null);
            }

        } catch (ApiErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).header("message", e.getMessage()).body(null);
        }
    }

    private String getKeyPixCodeAssas() {

        AssasHttpResponse response = assasService.getKeyPixCodeAssas();

        if (response.isSuccess()) {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) response.getData().get("key");

            if (dataList != null && !dataList.isEmpty()) {
                Map<String, Object> firstItem = dataList.get(0);
                return (String) firstItem.get("key");
            }
        } else {

        }

        return null;

    }

    public ResponseEntity<Boolean> confirmPayment(String idQRCodeStatic) {

        try {

            Optional<ConfirmPaymentResponse> result = findPaymentByIdQRCodeStatic(
                    (List<LinkedHashMap<String, Object>>) assasService.findAllPixPayments().getData().get("data"),
                    idQRCodeStatic);

            if (result.isPresent()) {
                ConfirmPaymentResponse confirmPayment = result.get();

                Optional<Payment> paymentOpt = getPaymentByIdQrCodeStatic(idQRCodeStatic);

                paymentOpt.ifPresentOrElse(
                        payment -> createOrUpdatePaymentPay(payment, confirmPayment),
                        () -> createOrUpdatePaymentPay(new Payment(), confirmPayment));

                return ResponseEntity.status(HttpStatus.OK).header("message", "Paid!").body(true);
            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED).header("message", "Not paid yet.").body(false);
            }

        } catch (ApiErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).header("message", e.getMessage()).body(false);
        }
    }

    private Optional<ConfirmPaymentResponse> findPaymentByIdQRCodeStatic(
            List<LinkedHashMap<String, Object>> payments, String idQRCodeStatic) {
        return payments.stream()
                .filter(payment -> {
                    Object conciliationIdentifier = payment.get("conciliationIdentifier");
                    return conciliationIdentifier != null
                            && conciliationIdentifier.toString().equalsIgnoreCase(idQRCodeStatic);
                })
                .findFirst()
                .map(ConfirmPaymentResponse::new);
    }

    private Optional<Payment> getPaymentByIdQrCodeStatic(String idQRCodeStatic) {
        Payment probe = new Payment();
        probe.setIdQRCodeStatic(idQRCodeStatic);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnoreNullValues()
                .withMatcher("id_qr_code_static", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());

        Example<Payment> example = Example.of(probe, matcher);

        return paymentRepository.findOne(example);
    }

    private Payment createOrUpdatePaymentPay(Payment payment, ConfirmPaymentResponse confirmPayment) {
        if (payment.getDateCreated() == null) {
            payment.setDateCreated(LocalDateTime.now());
        }
        if (payment.getValue() == null) {
            payment.setValue(confirmPayment.getValue());
        }
        payment.setStatus(confirmPayment.getStatus());
        payment.setIdQRCodeStatic(confirmPayment.getIdQRCodeStatic());
        payment.setPaymentReceiptUrl(confirmPayment.getPaymentReceiptUrl());
        payment.setPaidDate(confirmPayment.getPaidDate());
        return paymentRepository.save(payment);
    }

    public ResponseEntity<List<Payment>> findAllPayments() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(paymentRepository.findAll());
        } catch (ApiErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).header("message", e.getMessage()).body(null);
        }
    }

}
