package com.gpayments.cardmanagement.service;

import com.gpayments.cardmanagement.dto.CardRequest;
import com.gpayments.cardmanagement.dto.CardResponse;
import com.gpayments.cardmanagement.model.Card;
import com.gpayments.cardmanagement.repository.CardRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {
    
    private final CardRepository cardRepository;
    private final EncryptionService encryptionService;
    private final PanValidationService panValidationService;
    
    public CardService(CardRepository cardRepository, 
                      EncryptionService encryptionService, 
                      PanValidationService panValidationService) {
        this.cardRepository = cardRepository;
        this.encryptionService = encryptionService;
        this.panValidationService = panValidationService;
    }
    
    public CardResponse createCard(CardRequest request) {
        validateRequest(request);
        
        Card card = createCardEntity(request);
        cardRepository.save(card);
        
        return createCardResponse(card, request.getPan());
    }
    
    public List<CardResponse> searchByPan(String pan) {
        try {
            String panHash = encryptionService.generateHash(pan);
            List<Card> cards = cardRepository.findByPanHash(panHash);
            
            return cards.stream()
                .map(card -> createCardResponse(card, pan))
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error searching by PAN", e);
        }
    }
    
    public List<CardResponse> searchByLastFourDigits(String lastFourDigits) {
        try {
            List<Card> cards = cardRepository.findByLastFourDigits(lastFourDigits);
            
            return cards.stream()
                .map(this::createCardResponseFromEncrypted)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error searching by last four digits", e);
        }
    }
    
    // Private helper methods
    private void validateRequest(CardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Card request cannot be null");
        }
        if (!panValidationService.isValidPan(request.getPan())) {
            throw new IllegalArgumentException("Invalid PAN");
        }
    }
    
    private Card createCardEntity(CardRequest request) {
        try {
            Card card = new Card();
            card.setCardholderName(request.getCardholderName());
            card.setEncryptedPan(encryptionService.encrypt(request.getPan()));
            card.setPanHash(encryptionService.generateHash(request.getPan()));
            card.setLastFourDigits(encryptionService.getLastFourDigits(request.getPan()));
            card.setIv("");
            return card;
        } catch (Exception e) {
            throw new RuntimeException("Error creating card entity", e);
        }
    }
    
    private CardResponse createCardResponse(Card card, String originalPan) {
        try {
            return new CardResponse(
                card.getId(),
                card.getCardholderName(),
                encryptionService.maskPan(originalPan),
                card.getCreatedTime()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error creating card response", e);
        }
    }
    
    private CardResponse createCardResponseFromEncrypted(Card card) {
        try {
            String decryptedPan = encryptionService.decrypt(card.getEncryptedPan());
            return new CardResponse(
                card.getId(),
                card.getCardholderName(),
                encryptionService.maskPan(decryptedPan),
                card.getCreatedTime()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error processing encrypted card", e);
        }
    }
}
