package com.gpayments.cardmanagement.service;

import org.springframework.stereotype.Service;

@Service
public class PanValidationService {
    
    public boolean isValidPan(String pan) {
        if (pan == null || pan.length() < 12 || pan.length() > 19) {
            return false;
        }
        if (!pan.matches("\\d+")) {
            return false;
        }
        return checkLuhn(pan);
    }


    // Luhn Algorithm
    // Source: https://www.geeksforgeeks.org/dsa/luhn-algorithm/
    private boolean checkLuhn(String pan) {
        int sum = 0;
        int nDigits = pan.length();
        boolean alternate = false;

        for (int i = nDigits - 1; i >= 0; i--) {
            int n = Integer.parseInt(pan.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        
        return sum % 10 == 0;
    }
}
