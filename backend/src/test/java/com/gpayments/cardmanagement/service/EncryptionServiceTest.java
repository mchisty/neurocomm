package com.gpayments.cardmanagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceTest {

    private EncryptionService encryptionService;

    private static final String TEST_SECRET_KEY = "MySecretKey123456789012345678901";
    private static final String TEST_PAN = "4111111111111111";
    private static final String TEST_PAN_LAST_FOUR = "1111";
    private static final String TEST_MASKED_PAN = "************1111";

    @BeforeEach
    void setUp() {
        encryptionService = new EncryptionService(TEST_SECRET_KEY);
    }

    // Encryption Tests
    @Test
    void Should_EncryptPlaintext_When_ValidInputProvided() throws Exception {
        // Arrange
        String plaintext = "test data";

        // Act
        String encrypted = encryptionService.encrypt(plaintext);

        // Assert
        assertNotNull(encrypted);
        assertNotEquals(plaintext, encrypted);
        assertTrue(encrypted.length() > 0);
    }

    @Test
    void Should_ThrowIllegalArgumentException_When_NullPlaintextProvided() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            encryptionService.encrypt(null);
        });
    }

    @Test
    void Should_EncryptEmptyString_When_EmptyPlaintextProvided() throws Exception {
        // Arrange
        String plaintext = "";

        // Act
        String encrypted = encryptionService.encrypt(plaintext);

        // Assert
        assertNotNull(encrypted);
        assertNotEquals(plaintext, encrypted);
    }

    @Test
    void Should_EncryptSamePlaintextToSameCiphertext_When_EncryptedMultipleTimes() throws Exception {
        // Arrange
        String plaintext = "consistent encryption test";

        // Act
        String encrypted1 = encryptionService.encrypt(plaintext);
        String encrypted2 = encryptionService.encrypt(plaintext);

        // Assert
        assertEquals(encrypted1, encrypted2, "Same plaintext should encrypt to same ciphertext");
    }

    // Decryption Tests
    @Test
    void Should_DecryptCiphertext_When_ValidEncryptedDataProvided() throws Exception {
        // Arrange
        String originalText = "test data for decryption";
        String encrypted = encryptionService.encrypt(originalText);

        // Act
        String decrypted = encryptionService.decrypt(encrypted);

        // Assert
        assertEquals(originalText, decrypted);
    }

    @Test
    void Should_ThrowIllegalArgumentException_When_NullEncryptedDataProvided() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            encryptionService.decrypt(null);
        });
    }

    @Test
    void Should_DecryptEmptyString_When_EmptyStringWasEncrypted() throws Exception {
        // Arrange
        String originalText = "";
        String encrypted = encryptionService.encrypt(originalText);

        // Act
        String decrypted = encryptionService.decrypt(encrypted);

        // Assert
        assertEquals(originalText, decrypted);
    }

    @Test
    void Should_ThrowException_When_InvalidEncryptedDataProvided() {
        // Arrange
        String invalidEncryptedData = "invalid-base64-data!@#";

        // Act & Assert
        assertThrows(Exception.class, () -> {
            encryptionService.decrypt(invalidEncryptedData);
        });
    }

    // Hash Generation Tests
    @Test
    void Should_GenerateHash_When_ValidPanProvided() throws Exception {
        // Arrange
        String pan = TEST_PAN;

        // Act
        String hash = encryptionService.generateHash(pan);

        // Assert
        assertNotNull(hash);
        assertNotEquals(pan, hash);
        assertTrue(hash.length() > 0);
    }

    @Test
    void Should_ThrowIllegalArgumentException_When_NullPanProvidedForHash() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            encryptionService.generateHash(null);
        });
    }

    @Test
    void Should_GenerateSameHash_When_SamePanProvidedMultipleTimes() throws Exception {
        // Arrange
        String pan = TEST_PAN;

        // Act
        String hash1 = encryptionService.generateHash(pan);
        String hash2 = encryptionService.generateHash(pan);

        // Assert
        assertEquals(hash1, hash2, "Same PAN should generate same hash");
    }

    @Test
    void Should_GenerateDifferentHashes_When_DifferentPansProvided() throws Exception {
        // Arrange
        String pan1 = "4111111111111111";
        String pan2 = "5555555555554444";

        // Act
        String hash1 = encryptionService.generateHash(pan1);
        String hash2 = encryptionService.generateHash(pan2);

        // Assert
        assertNotEquals(hash1, hash2, "Different PANs should generate different hashes");
    }

    // Last Four Digits Tests
    @Test
    void Should_ReturnLastFourDigits_When_PanWithMoreThanFourDigitsProvided() {
        // Arrange
        String pan = TEST_PAN;

        // Act
        String lastFour = encryptionService.getLastFourDigits(pan);

        // Assert
        assertEquals(TEST_PAN_LAST_FOUR, lastFour);
    }

    @Test
    void Should_ReturnEntirePan_When_PanWithLessThanFourDigitsProvided() {
        // Arrange
        String pan = "123";

        // Act
        String result = encryptionService.getLastFourDigits(pan);

        // Assert
        assertEquals(pan, result);
    }

    @Test
    void Should_ReturnEntirePan_When_PanWithExactlyFourDigitsProvided() {
        // Arrange
        String pan = "1234";

        // Act
        String result = encryptionService.getLastFourDigits(pan);

        // Assert
        assertEquals(pan, result);
    }

    @Test
    void Should_ReturnEmptyString_When_NullPanProvided() {
        // Act
        String result = encryptionService.getLastFourDigits(null);

        // Assert
        assertEquals("", result);
    }

    @Test
    void Should_ReturnEmptyString_When_EmptyPanProvided() {
        // Arrange
        String pan = "";

        // Act
        String result = encryptionService.getLastFourDigits(pan);

        // Assert
        assertEquals("", result);
    }

    // Masking Tests
    @Test
    void Should_MaskPan_When_PanWithMoreThanFourDigitsProvided() {
        // Arrange
        String pan = TEST_PAN;

        // Act
        String masked = encryptionService.maskPan(pan);

        // Assert
        assertEquals(TEST_MASKED_PAN, masked);
    }

    @Test
    void Should_ReturnEntirePan_When_PanWithFourOrFewerDigitsProvided() {
        // Arrange
        String pan = "1234";

        // Act
        String masked = encryptionService.maskPan(pan);

        // Assert
        assertEquals(pan, masked);
    }

    @Test
    void Should_ReturnEntirePan_When_PanWithThreeDigitsProvided() {
        // Arrange
        String pan = "123";

        // Act
        String masked = encryptionService.maskPan(pan);

        // Assert
        assertEquals(pan, masked);
    }

    @Test
    void Should_ReturnEmptyString_When_NullPanProvidedForMasking() {
        // Act
        String masked = encryptionService.maskPan(null);

        // Assert
        assertEquals("", masked);
    }

    @Test
    void Should_ReturnEmptyString_When_EmptyPanProvidedForMasking() {
        // Arrange
        String pan = "";

        // Act
        String masked = encryptionService.maskPan(pan);

        // Assert
        assertEquals("", masked);
    }

    @Test
    void Should_MaskPanWithCorrectNumberOfAsterisks_When_LongPanProvided() {
        // Arrange
        String pan = "1234567890123456"; // 16 digits

        // Act
        String masked = encryptionService.maskPan(pan);

        // Assert
        assertEquals("************3456", masked);
        assertEquals(pan.length(), masked.length());
        assertEquals("3456", masked.substring(masked.length() - 4));
    }

    // Integration Tests
    @Test
    void Should_EncryptAndDecryptSuccessfully_When_ValidDataProvided() throws Exception {
        // Arrange
        String originalData = "sensitive card data";

        // Act
        String encrypted = encryptionService.encrypt(originalData);
        String decrypted = encryptionService.decrypt(encrypted);

        // Assert
        assertEquals(originalData, decrypted);
    }

    @Test
    void Should_HandleSpecialCharacters_When_EncryptingAndDecrypting() throws Exception {
        // Arrange
        String originalData = "card@#$%^&*()_+{}|:<>?[]\\;'\",./";

        // Act
        String encrypted = encryptionService.encrypt(originalData);
        String decrypted = encryptionService.decrypt(encrypted);

        // Assert
        assertEquals(originalData, decrypted);
    }

    @Test
    void Should_HandleUnicodeCharacters_When_EncryptingAndDecrypting() throws Exception {
        // Arrange
        String originalData = "card with unicode: 中文 Español Français";

        // Act
        String encrypted = encryptionService.encrypt(originalData);
        String decrypted = encryptionService.decrypt(encrypted);

        // Assert
        assertEquals(originalData, decrypted);
    }
}
