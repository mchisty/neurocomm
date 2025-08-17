package com.gpayments.cardmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CardRequest {
    
    @NotBlank
    private String cardholderName;
    
    @NotBlank
    @Pattern(regexp = "^\\d{12,19}$", message = "PAN must be 12-19 digits")
    private String pan;
    
    @NotBlank
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{2})$", message = "Expiry date must be in MM/YY format")
    private String expiryDate;
    
    @NotBlank
    @Pattern(regexp = "^\\d{3,4}$", message = "CVV must be 3-4 digits")
    private String cvv;

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
