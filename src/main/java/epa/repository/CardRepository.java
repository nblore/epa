package epa.repository;

import epa.entity.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*** 
Allows database interaction using spring data

 ***/
@Repository
public interface CardRepository extends CrudRepository<Card, String> {

    List<Card> findAll();
    Card save(Card card);
}
