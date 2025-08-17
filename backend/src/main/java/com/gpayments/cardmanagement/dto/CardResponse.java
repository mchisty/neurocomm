package com.gpayments.cardmanagement.dto;

import java.time.LocalDateTime;

public class CardResponse {
    
    private Long id;
    private String cardholderName;
    private String maskedPan;
    private LocalDateTime createdTime;
    
    public CardResponse() {}
    
    public CardResponse(Long id, String cardholderName, String maskedPan, LocalDateTime createdTime) {
        this.id = id;
        this.cardholderName = cardholderName;
        this.maskedPan = maskedPan;
        this.createdTime = createdTime;
    }
    
    // Getters and Setters
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
    
    public String getMaskedPan() {
        return maskedPan;
    }
    
    public void setMaskedPan(String maskedPan) {
        this.maskedPan = maskedPan;
    }
    
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}



