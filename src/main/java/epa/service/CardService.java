package epa.service;

import epa.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {

    List<Card> findAll();
    Optional<Card> findById(String id);
    Card save(Card card);
}

