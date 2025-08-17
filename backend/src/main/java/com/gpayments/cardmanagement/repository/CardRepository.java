package com.gpayments.cardmanagement.repository;

import com.gpayments.cardmanagement.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    
    @Query("SELECT c FROM Card c WHERE c.panHash = :panHash")
    List<Card> findByPanHash(@Param("panHash") String panHash);
    
    @Query("SELECT c FROM Card c WHERE c.lastFourDigits = :lastFourDigits")
    List<Card> findByLastFourDigits(@Param("lastFourDigits") String lastFourDigits);
}
