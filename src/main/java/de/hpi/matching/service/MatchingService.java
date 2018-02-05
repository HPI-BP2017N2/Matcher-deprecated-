package de.hpi.matching.service;

import de.hpi.matching.model.Matching;
import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.matching.repo.MatchingResultsRepository;
import de.hpi.matching.repo.ParsedOfferRepository;
import de.hpi.restclient.dto.MatchingResponse;
import de.hpi.restclient.dto.ParsedOffer;
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
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MatchingResultsRepository matchingResultsRepository;

    // initialization
    @Autowired
    public MatchingService (OfferMatchingRepository repository, ParsedOfferRepository parsedOfferRepository, MatchingResultsRepository matchingResultsRepository){
        setRepo(repository);
        setMatchingResultsRepository(matchingResultsRepository);
        setParsedOfferRepository(parsedOfferRepository);
        setMatching(new Matching(getRepo()));
    }

    // convenience
    public MatchingResponse matchSingleOffer(ParsedOffer offer) {
        if(isInDatabaseAndIdealoOffer(offer)) {
            return getMatchingResultsRepository().searchByUrl(offer.getShopId(), offer.getUrl());
        }
        return getMatching().match(offer);
    }

    public void matchOffersForShop(long shopId) {
        ParsedOffer offer;
        MatchingResponse response;
        do {
            offer = getParsedOfferRepository().popOffer(shopId);
            if (isInDatabaseAndIdealoOffer(offer)) {
                continue;
            }
            response = getMatching().match(offer);
            getMatchingResultsRepository().save(response);
        } while (offer != null);

    }

    // conditionals
    private boolean isInDatabaseAndIdealoOffer(ParsedOffer offer) {
        return isInDatabase(offer) && isIdealoOffer(offer);
    }

    private boolean isInDatabase(ParsedOffer offer) {
        return getMatchingResultsRepository().searchByUrl(offer.getShopId(), offer.getUrl()) != null;
    }

    private boolean isIdealoOffer(ParsedOffer offer) {
        return getMatchingResultsRepository().searchByUrl(offer.getShopId(), offer.getUrl()).isIdealoOffer();
    }
}