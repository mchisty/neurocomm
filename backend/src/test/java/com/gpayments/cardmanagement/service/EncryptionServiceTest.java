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
        String plaintext = "test data";
        String encrypted = encryptionService.encrypt(plaintext);
        assertNotNull(encrypted);
        assertNotEquals(plaintext, encrypted);
        assertTrue(encrypted.length() > 0);
    }

    @Test
    void Should_ThrowIllegalArgumentException_When_NullPlaintextProvided() {
        assertThrows(IllegalArgumentException.class, () -> {
            encryptionService.encrypt(null);
        });
    }

    @Test
    void Should_EncryptEmptyString_When_EmptyPlaintextProvided() throws Exception {
        String plaintext = "";
        String encrypted = encryptionService.encrypt(plaintext);
        assertNotNull(encrypted);
        assertNotEquals(plaintext, encrypted);
    }

    @Test
    void Should_EncryptSamePlaintextToSameCiphertext_When_EncryptedMultipleTimes() throws Exception {
        String plaintext = "consistent encryption test";
        String encrypted1 = encryptionService.encrypt(plaintext);
        String encrypted2 = encryptionService.encrypt(plaintext);
        assertEquals(encrypted1, encrypted2, "Same plaintext should encrypt to same ciphertext");
    }

    // Decryption Tests
    @Test
    void Should_DecryptCiphertext_When_ValidEncryptedDataProvided() throws Exception {
        String originalText = "test data for decryption";
        String encrypted = encryptionService.encrypt(originalText);
        String decrypted = encryptionService.decrypt(encrypted);
        assertEquals(originalText, decrypted);
    }

    @Test
    void Should_ThrowIllegalArgumentException_When_NullEncryptedDataProvided() {
        assertThrows(IllegalArgumentException.class, () -> {
            encryptionService.decrypt(null);
        });
    }

    @Test
    void Should_DecryptEmptyString_When_EmptyStringWasEncrypted() throws Exception {
        String originalText = "";
        String encrypted = encryptionService.encrypt(originalText);
        String decrypted = encryptionService.decrypt(encrypted);

        assertEquals(originalText, decrypted);
    }

    @Test
    void Should_ThrowException_When_InvalidEncryptedDataProvided() {
        String invalidEncryptedData = "invalid-base64-data!@#";

        assertThrows(Exception.class, () -> {
            encryptionService.decrypt(invalidEncryptedData);
        });
    }

    // Hash Generation Tests
    @Test
    void Should_GenerateHash_When_ValidPanProvided() throws Exception {
        String pan = TEST_PAN;

        String hash = encryptionService.generateHash(pan);
        assertNotNull(hash);
        assertNotEquals(pan, hash);
        assertTrue(hash.length() > 0);
    }

    @Test
    void Should_ThrowIllegalArgumentException_When_NullPanProvidedForHash() {
        assertThrows(IllegalArgumentException.class, () -> {
            encryptionService.generateHash(null);
        });
    }


    // Last Four Digits Tests
    @Test
    void Should_ReturnLastFourDigits_When_PanWithMoreThanFourDigitsProvided() {
        String pan = TEST_PAN;

        String lastFour = encryptionService.getLastFourDigits(pan);
        assertEquals(TEST_PAN_LAST_FOUR, lastFour);
    }

    @Test
    void Should_ReturnEntirePan_When_PanWithExactlyFourDigitsProvided() {
        String pan = "1234";

        String result = encryptionService.getLastFourDigits(pan);
        assertEquals(pan, result);
    }

    @Test
    void Should_ReturnEmptyString_When_NullPanProvided() {
        String result = encryptionService.getLastFourDigits(null);
        assertEquals("", result);
    }


    // Masking Tests
    @Test
    void Should_MaskPan_When_PanWithMoreThanFourDigitsProvided() {
        String pan = TEST_PAN;
        String masked = encryptionService.maskPan(pan);
        assertEquals(TEST_MASKED_PAN, masked);
    }

    @Test
    void Should_ReturnEntirePan_When_PanWithFourOrFewerDigitsProvided() {
        String pan = "1234";
        String masked = encryptionService.maskPan(pan);

        assertEquals(pan, masked);
    }





}
