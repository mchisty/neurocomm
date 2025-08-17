package com.gpayments.cardmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(name = "cardholder_name", nullable = false)
    private String cardholderName;
    
    @NotNull
    @Column(name = "encrypted_pan", nullable = false, columnDefinition = "TEXT")
    private String encryptedPan;
    
    @NotNull
    @Column(name = "pan_hash", nullable = false)
    private String panHash;
    
    @NotNull
    @Column(name = "last_four_digits", nullable = false)
    private String lastFourDigits;
    
    @Column(name = "iv")
    private String iv;
    
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    @PrePersist
    protected void onCreate() {
        createdTime = LocalDateTime.now();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCardholderName() {
        return cardholderName;
    }
    
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
    
    public String getEncryptedPan() {
        return encryptedPan;
    }
    
    public void setEncryptedPan(String encryptedPan) {
        this.encryptedPan = encryptedPan;
    }
    
    public String getPanHash() {
        return panHash;
    }
    
    public void setPanHash(String panHash) {
        this.panHash = panHash;
    }
    
    public String getLastFourDigits() {
        return lastFourDigits;
    }
    
    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }
    
    public String getIv() {
        return iv;
    }
    
    public void setIv(String iv) {
        this.iv = iv;
    }
    
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
