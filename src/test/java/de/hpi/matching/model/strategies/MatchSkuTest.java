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

class MatchSkuTest {

    private final OfferConstruction offerConstruction = new OfferConstruction();

    @Test
    void match() {
        OfferMatchingRepository repository = mock(OfferMatchingRepository.class);

        MatchSku matchSku = new MatchSku(repository);

        Offer idealoOffer = offerConstruction.constructIdealoOffer();

        List<Offer> testOffers= new ArrayList<>();
        testOffers.add(idealoOffer);



        matchSku(repository, matchSku, idealoOffer, testOffers);
        noMatchSku(repository, matchSku, new ArrayList<>());


    }

    private void matchSku(OfferMatchingRepository repository, MatchSku matchSku, Offer idealoOffer, List<Offer> testOffers) {
        when(repository.searchSku(anyLong(), eq(offerConstruction.constructParsedOffer().getSku()))).thenReturn(testOffers);

        Offer responseOffer = matchSku.match(offerConstruction.constructParsedOffer());

        assertEquals(idealoOffer, responseOffer);
    }

    private void noMatchSku(OfferMatchingRepository repository, MatchSku matchSku, List<Offer> testOffers) {
        when(repository.searchSku(anyLong(), eq(offerConstruction.constructParsedOffer().getSku()))).thenReturn(testOffers);

        Offer responseOffer = matchSku.match(offerConstruction.constructParsedOffer());

        assertEquals(null, responseOffer);
    }
}