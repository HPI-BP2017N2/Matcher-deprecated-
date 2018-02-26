/*package de.hpi.matching.model.strategies;

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

class MatchEanTest {
    private final OfferConstruction offerConstruction = new OfferConstruction();

    @Test
    void match() {
        OfferMatchingRepository repository = mock(OfferMatchingRepository.class);

        MatchEan matchEan = new MatchEan(repository);

        Offer idealoOffer = offerConstruction.constructIdealoOffer();

        List<Offer> testOffers= new ArrayList<>();
        testOffers.add(idealoOffer);



        matchEan(repository, matchEan, idealoOffer, testOffers);
        noMatchEan(repository, matchEan, new ArrayList<>());


    }

    private void matchEan(OfferMatchingRepository repository, MatchEan matchEan, Offer idealoOffer, List<Offer> testOffers) {
        when(repository.searchEan(anyLong(), eq(offerConstruction.constructParsedOffer().getEan()))).thenReturn(testOffers);

        Offer responseOffer = matchEan.match(offerConstruction.constructParsedOffer());

        assertEquals(idealoOffer, responseOffer);
    }

    private void noMatchEan(OfferMatchingRepository repository, MatchEan matchEan, List<Offer> testOffers) {
        when(repository.searchEan(anyLong(), eq(offerConstruction.constructParsedOffer().getEan()))).thenReturn(testOffers);

        Offer responseOffer = matchEan.match(offerConstruction.constructParsedOffer());

        assertEquals(null, responseOffer);
    }


}*/