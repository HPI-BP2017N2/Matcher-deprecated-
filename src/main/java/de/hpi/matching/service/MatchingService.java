package de.hpi.matching.service;

import de.hpi.matching.model.Matching;
import de.hpi.matching.repo.MatchingResultsRepository;
import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.matching.repo.ParsedOfferRepository;
import de.hpi.restclient.pojo.ExtractedDataMap;
import de.hpi.restclient.pojo.MatchingResponse;
import de.hpi.restclient.pojo.OfferAttribute;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
public class MatchingService {

    private OfferMatchingRepository repo;
    private Matching matching;
    private ParsedOfferRepository parsedOfferRepository;
    private MatchingResultsRepository matchingResultsRepository;

    // initialization
    @Autowired
    public MatchingService (OfferMatchingRepository repository,
                            ParsedOfferRepository parsedOfferRepository,
                            MatchingResultsRepository matchingResultsRepository){
        setRepo(repository);
        setMatchingResultsRepository(matchingResultsRepository);
        setParsedOfferRepository(parsedOfferRepository);
        setMatching(new Matching(getRepo()));
    }

    // convenience
    public void matchOffersForShop(long shopId) {
        ExtractedDataMap extractedDataMap;
        MatchingResponse response;
        do {
            extractedDataMap = getParsedOfferRepository().popOffer(shopId);

            if (extractedDataMap == null) continue;
            String url = extractedDataMap.getData().get(OfferAttribute.URL).getValue();
            if (isInDatabase(shopId, url) && isIdealoOffer(shopId, url)) continue;
            response = getMatching().match(shopId, extractedDataMap);
            getMatchingResultsRepository().save(response);

        } while (extractedDataMap != null);
    }

    // conditionals
    private boolean isInDatabase(long shopId, String url) {
        return getMatchingResultsRepository().searchByUrl(shopId, url) != null;
    }

    private boolean isIdealoOffer(long shopId, String url) {
        return getMatchingResultsRepository().searchByUrl(shopId, url).isIdealoOffer();
    }
}