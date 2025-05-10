package com.aurora.pix.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.aurora.pix.dto.AssasHttpResponse;
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
            CreatePixQrCodeStaticPayload createPixQrCodeStaticPayload) {

        try {
            createPixQrCodeStaticPayload.setAddressKey(getKeyPixCodeAssas());

            AssasHttpResponse createPixQrCodeStaticResponse = assasService
                    .createPixQrCodeStatic(createPixQrCodeStaticPayload);

            if (createPixQrCodeStaticResponse.isSuccess()) {

                Payment newPayment = new Payment(createPixQrCodeStaticPayload,
                        new CreatePixQrCodeStaticResponse(createPixQrCodeStaticResponse.getData()));

                newPayment = paymentRepository.save(newPayment);

                // TODO: Criar log de sucesso

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
            // TODO: Fazer tratativa para quando der erro aqui
        }

        return null;

    }

}
