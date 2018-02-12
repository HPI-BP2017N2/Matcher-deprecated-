package de.hpi.matching.model.strategies;

import de.hpi.matching.model.OfferConstruction;
import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.restclient.pojo.Offer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MatchHanTest {

    private final OfferConstruction offerConstruction = new OfferConstruction();

    @Test
    void match() {
        OfferMatchingRepository repository = mock(OfferMatchingRepository.class);

        MatchHan matchHan = new MatchHan(repository);

        Offer idealoOffer = offerConstruction.constructIdealoOffer();

        List<Offer> testOffers= new ArrayList<>();
        testOffers.add(idealoOffer);



        matchHan(repository, matchHan, idealoOffer, testOffers);
        noMatchHan(repository, matchHan, new ArrayList<>());


    }

    private void matchHan(OfferMatchingRepository repository, MatchHan matchHan, Offer idealoOffer, List<Offer> testOffers) {
        when(repository.searchHan(anyLong(), eq(offerConstruction.constructParsedOffer().getHan()))).thenReturn(testOffers);

        Offer responseOffer = matchHan.match(offerConstruction.constructParsedOffer());

        assertEquals(idealoOffer, responseOffer);
    }

    private void noMatchHan(OfferMatchingRepository repository, MatchHan matchHan, List<Offer> testOffers) {
        when(repository.searchHan(anyLong(), eq(offerConstruction.constructParsedOffer().getHan()))).thenReturn(testOffers);

        Offer responseOffer = matchHan.match(offerConstruction.constructParsedOffer());

        assertEquals(null, responseOffer);
    }
}