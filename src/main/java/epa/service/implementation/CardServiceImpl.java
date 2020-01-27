package epa.service.implementation;

        import epa.entity.Card;
        import epa.repository.CardRepository;
        import epa.service.CardService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<Card> findAll() { return cardRepository.findAll(); }

    public Optional<Card> findById(String id) { return cardRepository.findById(id); }

    @Override
    public Card save(Card card) { return cardRepository.save(card); }

}

