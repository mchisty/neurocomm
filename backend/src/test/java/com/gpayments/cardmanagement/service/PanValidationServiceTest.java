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
        // Arrange
        String pan = "4111111111111111";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertTrue(result);
    }

    @Test
    void Should_ReturnTrue_When_ValidMastercardProvided() {
        // Arrange
        String pan = "5555555555554444";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertTrue(result);
    }

    @Test
    void Should_ReturnTrue_When_ValidAmericanExpressProvided() {
        // Arrange
        String pan = "378282246310005";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertTrue(result);
    }

    @Test
    void Should_ReturnTrue_When_ValidDiscoverCardProvided() {
        // Arrange
        String pan = "6011111111111117";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertTrue(result);
    }

    @Test
    void Should_ReturnTrue_When_ValidJcbCardProvided() {
        // Arrange
        String pan = "3530111333300000";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertTrue(result);
    }

    @Test
    void Should_ReturnTrue_When_ValidDinersClubCardProvided() {
        // Arrange
        String pan = "3056930009020004";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertTrue(result);
    }

    @Test
    void Should_ReturnFalse_When_InvalidPanWithMinimumLengthProvided() {
        // Arrange
        String pan = "123456789012";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result); // This PAN doesn't pass Luhn check
    }

    @Test
    void Should_ReturnFalse_When_InvalidPanWithMaximumLengthProvided() {
        // Arrange
        String pan = "1234567890123456789";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result); // This PAN doesn't pass Luhn check
    }

    // Invalid PAN Tests - Null and Empty
    @Test
    void Should_ReturnFalse_When_NullPanProvided() {
        // Act
        boolean result = panValidationService.isValidPan(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_EmptyPanProvided() {
        // Arrange
        String pan = "";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_BlankPanProvided() {
        // Arrange
        String pan = "   ";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    // Invalid PAN Tests - Length Issues
    @Test
    void Should_ReturnFalse_When_PanLengthLessThan12() {
        // Arrange
        String pan = "12345678901";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanLengthGreaterThan19() {
        // Arrange
        String pan = "12345678901234567890";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanLengthIs11() {
        // Arrange
        String pan = "12345678901";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanLengthIs20() {
        // Arrange
        String pan = "12345678901234567890";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    // Invalid PAN Tests - Format Issues
    @Test
    void Should_ReturnFalse_When_PanContainsLetters() {
        // Arrange
        String pan = "411111111111111A";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanContainsSpecialCharacters() {
        // Arrange
        String pan = "4111111111111111!";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanContainsSpaces() {
        // Arrange
        String pan = "4111 1111 1111 1111";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanContainsHyphens() {
        // Arrange
        String pan = "4111-1111-1111-1111";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanContainsMixedCharacters() {
        // Arrange
        String pan = "4111ABC1111111111";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    // Invalid PAN Tests - Luhn Algorithm Failures
    @Test
    void Should_ReturnFalse_When_InvalidVisaCardProvided() {
        // Arrange
        String pan = "4111111111111112";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_InvalidMastercardProvided() {
        // Arrange
        String pan = "5555555555554445";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_InvalidAmericanExpressProvided() {
        // Arrange
        String pan = "378282246310006";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanWithWrongCheckDigitProvided() {
        // Arrange
        String pan = "1234567890123456";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnTrue_When_PanWithAllZerosProvided() {
        // Arrange
        String pan = "0000000000000000";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertTrue(result); // All zeros passes Luhn check
    }

    @Test
    void Should_ReturnFalse_When_PanWithAllOnesProvided() {
        // Arrange
        String pan = "1111111111111111";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    // Edge Cases
    @Test
    void Should_ReturnFalse_When_PanWithSingleDigitChanged() {
        // Arrange
        String pan = "4111111111111110";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanWithTransposedDigits() {
        // Arrange
        String pan = "4111111111111101";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnTrue_When_ValidPanWithLeadingZerosProvided() {
        // Arrange
        String pan = "0000000000000000";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertTrue(result); // All zeros passes Luhn check
    }

    // Boundary Tests
    @Test
    void Should_ReturnFalse_When_PanWithExactly12DigitsProvided() {
        // Arrange
        String pan = "123456789012";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result); // This PAN doesn't pass Luhn check
    }

    @Test
    void Should_ReturnFalse_When_PanWithExactly19DigitsProvided() {
        // Arrange
        String pan = "1234567890123456789";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result); // This PAN doesn't pass Luhn check
    }

    @Test
    void Should_ReturnFalse_When_PanWithExactly11DigitsProvided() {
        // Arrange
        String pan = "12345678901";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_ReturnFalse_When_PanWithExactly20DigitsProvided() {
        // Arrange
        String pan = "12345678901234567890";

        // Act
        boolean result = panValidationService.isValidPan(pan);

        // Assert
        assertFalse(result);
    }

    // Real-world Test Cases
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
    void Should_ReturnTrue_When_CommonValidMastercardsProvided() {
        // Arrange
        String[] validMastercards = {
            "5555555555554444",
            "5105105105105100"
        };

        // Act & Assert
        for (String pan : validMastercards) {
            assertTrue(panValidationService.isValidPan(pan), "Failed for PAN: " + pan);
        }
    }

    @Test
    void Should_ReturnTrue_When_CommonValidAmericanExpressCardsProvided() {
        // Arrange
        String[] validAmexCards = {
            "378282246310005",
            "371449635398431"
        };

        // Act & Assert
        for (String pan : validAmexCards) {
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
