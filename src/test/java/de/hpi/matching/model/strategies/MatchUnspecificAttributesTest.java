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

class MatchUnspecificAttributesTest {

    private final OfferConstruction offerConstruction = new OfferConstruction();

    @Test
    void match() {
        OfferMatchingRepository repository = mock(OfferMatchingRepository.class);

        MatchEan matchEan = new MatchEan(repository);

        Offer idealoOffer = offerConstruction.constructIdealoOffer();

        List<Offer> testOffers= new ArrayList<>();
        testOffers.add(idealoOffer);

        //TODO Construct many different offers and see whether the matching of unspecific attributes works


    }

}