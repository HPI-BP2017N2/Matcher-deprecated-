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

class MatchOfferTitleTest {

    private final OfferConstruction offerConstruction = new OfferConstruction();

    @Test
    void match() {
        OfferMatchingRepository repository = mock(OfferMatchingRepository.class);

        MatchOfferTitle matchOfferTitle = new MatchOfferTitle(repository);

        Offer idealoOffer = offerConstruction.constructIdealoOffer();

        List<Offer> testOffers= new ArrayList<>();
        testOffers.add(idealoOffer);



        matchOfferTitle(repository, matchOfferTitle, idealoOffer, testOffers);
        noMatchOfferTitle(repository, matchOfferTitle, new ArrayList<>());


    }

    private void matchOfferTitle(OfferMatchingRepository repository, MatchOfferTitle matchOfferTitle, Offer idealoOffer, List<Offer> testOffers) {
        when(repository.searchOfferTitle(anyLong(), eq(offerConstruction.constructParsedOffer().getOfferTitle()))).thenReturn(testOffers);

        Offer responseOffer = matchOfferTitle.match(offerConstruction.constructParsedOffer());

        assertEquals(idealoOffer, responseOffer);
    }

    private void noMatchOfferTitle(OfferMatchingRepository repository, MatchOfferTitle matchOfferTitle, List<Offer> testOffers) {
        when(repository.searchOfferTitle(anyLong(), eq(offerConstruction.constructParsedOffer().getOfferTitle()))).thenReturn(testOffers);

        Offer responseOffer = matchOfferTitle.match(offerConstruction.constructParsedOffer());

        assertEquals(null, responseOffer);
    }
}