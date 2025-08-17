package com.gpayments.cardmanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PanValidationServiceTest {

    private PanValidationService panValidationService;

    @BeforeEach
    void setUp() {
        panValidationService = new PanValidationService();
    }

    // Valid PAN Tests
    @Test
    void Should_ReturnTrue_When_ValidVisaCardProvided() {
        String pan = "4111111111111111";

        boolean result = panValidationService.isValidPan(pan);
        assertTrue(result);
    }




    @Test
    void Should_ReturnFalse_When_InvalidPanWithMinimumLengthProvided() {
        String pan = "123456789012";
        boolean result = panValidationService.isValidPan(pan);

        assertFalse(result); // This PAN doesn't pass Luhn check
    }

    @Test
    void Should_ReturnFalse_When_InvalidPanWithMaximumLengthProvided() {
        String pan = "1234567890123456789";

        boolean result = panValidationService.isValidPan(pan);
        assertFalse(result); // This PAN doesn't pass Luhn check
    }


    // Invalid PAN Tests - Format Issues
    @Test
    void Should_ReturnFalse_When_PanContainsLetters() {
        String pan = "411111111111111A";

        boolean result = panValidationService.isValidPan(pan);
        assertFalse(result);
    }


    @Test
    void Should_ReturnFalse_When_PanContainsSpaces() {
        String pan = "4111 1111 1111 1111";

        boolean result = panValidationService.isValidPan(pan);
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanContainsHyphens() {
        String pan = "4111-1111-1111-1111";

        boolean result = panValidationService.isValidPan(pan);
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanContainsMixedCharacters() {
        String pan = "4111ABC1111111111";

        boolean result = panValidationService.isValidPan(pan);
        assertFalse(result);
    }



    @Test
    void Should_ReturnFalse_When_PanWithWrongCheckDigitProvided() {
        String pan = "1234567890123456";
        boolean result = panValidationService.isValidPan(pan);
        assertFalse(result);
    }

    @Test
    void Should_ReturnTrue_When_PanWithAllZerosProvided() {
        String pan = "0000000000000000";
        boolean result = panValidationService.isValidPan(pan);
        assertTrue(result); // All zeros passes Luhn check
    }


    @Test
    void Should_ReturnFalse_When_PanWithSingleDigitChanged() {
        String pan = "4111111111111110";
        boolean result = panValidationService.isValidPan(pan);
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanWithTransposedDigits() {
        String pan = "4111111111111101";
        boolean result = panValidationService.isValidPan(pan);
        assertFalse(result);
    }

    @Test
    void Should_ReturnTrue_When_ValidPanWithLeadingZerosProvided() {
        String pan = "0000000000000000";

        boolean result = panValidationService.isValidPan(pan);
        assertTrue(result); // All zeros passes Luhn check
    }


    @Test
    void Should_ReturnTrue_When_CommonValidVisaCardsProvided() {
        // Arrange
        String[] validVisaCards = {
            "4111111111111111",
            "4012888888881881",
            "4222222222222"
        };

        // Act & Assert
        for (String pan : validVisaCards) {
            assertTrue(panValidationService.isValidPan(pan), "Failed for PAN: " + pan);
        }
    }



    @Test
    void Should_ReturnFalse_When_CommonInvalidCardsProvided() {
        // Arrange
        String[] invalidCards = {
            "4111111111111112",
            "5555555555554445",
            "378282246310006",
            "1234567890123456"
        };

        // Act & Assert
        for (String pan : invalidCards) {
            assertFalse(panValidationService.isValidPan(pan), "Should have failed for PAN: " + pan);
        }
    }

}
