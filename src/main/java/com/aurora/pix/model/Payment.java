package com.aurora.pix.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.aurora.pix.enums.StatusPayment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "qr_code_base64", length = 999)
    private String qrCodeBase64; // encodedImage

    private String codePix; // payload

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "owner", nullable = false)
    private String owner; // Owner ID (user) Payment

    public Payment(UUID id) {
        this.id = id;
    }

    public Payment(BigDecimal value, String qrCodeBase64, String codePix, String owner) {
        this.value = value;
        this.status = StatusPayment.CREATED;
        this.qrCodeBase64 = qrCodeBase64;
        this.codePix = codePix;
        this.dateCreated = LocalDateTime.now();
        this.owner = owner;
    }

}