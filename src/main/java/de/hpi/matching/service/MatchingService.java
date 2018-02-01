package de.hpi.matching.service;

import de.hpi.matching.model.Matching;
import de.hpi.matching.model.OfferMatchingRepository;
import de.hpi.matching.model.data.MatchingResponseRepository;
import de.hpi.matching.model.data.ParsedOfferRepository;
import de.hpi.restclient.dto.MatchingResponse;
import de.hpi.restclient.dto.ParsedOffer;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private OfferMatchingRepository repo;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private Matching matching;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParsedOfferRepository parsedOfferRepository;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MatchingResponseRepository matchingResponseRepository;

    // initialization
    @Autowired
    public MatchingService (OfferMatchingRepository repository){
        setRepo(repository);
        setMatching(new Matching(getRepo()));
    }

    // convenience
    public MatchingResponse matchSingleOffer(ParsedOffer offer) {
        return getMatching().match(offer);
    }

    public void matchOffersForShop(long shopId){
        ParsedOffer offer;
        MatchingResponse response;
        do {
            offer = getParsedOfferRepository().getFirstOffer(shopId);
            response = getMatching().match(offer);
            getMatchingResponseRepository().saveMatchingResponse(response);
            getParsedOfferRepository().removeFirstOffer(shopId);
        } while (offer != null);

    }
}