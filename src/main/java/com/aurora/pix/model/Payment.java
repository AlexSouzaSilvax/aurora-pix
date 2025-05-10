package com.aurora.pix.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.aurora.pix.dto.CreatePixQrCodeStaticPayload;
import com.aurora.pix.dto.CreatePixQrCodeStaticResponse;
import com.aurora.pix.enums.StatusPayment;

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

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    private StatusPayment status;

    @Lob
    @Column(name = "qr_code_base64", columnDefinition = "TEXT")
    private String qrCodeBase64;

    private String codePix;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "owner", nullable = false)
    private String owner;

    public Payment(CreatePixQrCodeStaticPayload createPixQrCodeStaticPayload,
            CreatePixQrCodeStaticResponse createPixQrCodeStaticResponse) {
        this.value = createPixQrCodeStaticPayload.getValue();
        this.status = StatusPayment.CREATED;
        this.qrCodeBase64 = createPixQrCodeStaticResponse.getQrCodeBase64();
        this.codePix = createPixQrCodeStaticResponse.getPixCode();
        this.dateCreated = LocalDateTime.now();
        this.owner = createPixQrCodeStaticPayload.getDescription();
    }

}