package com.aurora.pix.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.aurora.pix.dto.CreatePixQrCodeStaticPayload;
import com.aurora.pix.dto.CreatePixQrCodeStaticResponse;
import com.aurora.pix.enums.StatusPayment;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment")
@Getter
@Setter
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "id_qr_code_static")
    @JsonProperty("id_qr_code_static")
    private String idQRCodeStatic;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    private StatusPayment status;

    @Lob
    @Column(name = "qr_code_base64", columnDefinition = "TEXT")
    @JsonProperty("qr_code_base64")
    private String qrCodeBase64;

    @Column(name = "code_pix")
    @JsonProperty("code_pix")
    private String codePix;

    @Column(name = "date_created", nullable = false)
    @JsonProperty("date_created")
    private LocalDateTime dateCreated;

    @Column(name = "paid_date")
    @JsonProperty("paid_date")
    private LocalDateTime paidDate;

    @Column(name = "payment_receipt_url")
    @JsonProperty("payment_receipt_url")
    private String paymentReceiptUrl;

    private String description;

    public Payment() {
    }

    public Payment(CreatePixQrCodeStaticPayload createPixQrCodeStaticPayload,
            CreatePixQrCodeStaticResponse createPixQrCodeStaticResponse) {
        this.idQRCodeStatic = createPixQrCodeStaticResponse.getIdQRCodeStatic();
        this.value = createPixQrCodeStaticPayload.getValue();
        this.status = StatusPayment.CREATED;
        this.qrCodeBase64 = createPixQrCodeStaticResponse.getQrCodeBase64();
        this.codePix = createPixQrCodeStaticResponse.getPixCode();
        this.dateCreated = LocalDateTime.now();
        this.description = createPixQrCodeStaticPayload.getDescription();
    }

}