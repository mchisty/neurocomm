package com.gpayments.cardmanagement.controller;

import com.gpayments.cardmanagement.dto.CardRequest;
import com.gpayments.cardmanagement.dto.CardResponse;
import com.gpayments.cardmanagement.service.CardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class CardController {
    
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);
    
    private final CardService cardService;
    
    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }
    
    @PostMapping
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CardRequest request) {
        logger.info("Creating card for cardholder: {}", request.getCardholderName());
        CardResponse response = cardService.createCard(request);
        logger.info("Card created successfully with ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search/pan")
    public ResponseEntity<List<CardResponse>> searchByPan(
            @RequestParam @NotBlank @Pattern(regexp = "^\\d{12,19}$", message = "PAN must be 12-19 digits") 
            String pan) {
        logger.info("Searching cards by PAN");
        List<CardResponse> cards = cardService.searchByPan(pan);
        logger.info("Found {} cards for PAN search", cards.size());
        return ResponseEntity.ok(cards);
    }
    
    @GetMapping("/search/last-four")
    public ResponseEntity<List<CardResponse>> searchByLastFourDigits(
            @RequestParam @NotBlank @Pattern(regexp = "^\\d{4}$", message = "Last four digits must be exactly 4 digits") 
            String lastFourDigits) {
        logger.info("Searching cards by last four digits: {}", lastFourDigits);
        List<CardResponse> cards = cardService.searchByLastFourDigits(lastFourDigits);
        logger.info("Found {} cards for last four digits search", cards.size());
        return ResponseEntity.ok(cards);
    }
}
