package de.hpi.matching.model;

import de.hpi.matching.model.strategies.*;
import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.restclient.pojo.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.util.ArrayList;

@Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
public class Matching {

    private OfferMatchingRepository repo;
    private ArrayList<MatchStrategy> strategies;
    private Logger logger = Logger.getRootLogger();


    // initialization
    public Matching (OfferMatchingRepository repo) {
        setRepo(repo);
        setStrategies(new ArrayList<>());
        getStrategies().add(new MatchEan(getRepo()));
        getStrategies().add(new MatchHan(getRepo()));
        getStrategies().add(new MatchSku(getRepo()));
        getStrategies().add(new MatchOfferTitle(getRepo()));
        getStrategies().add(new MatchUnspecificAttributes(getRepo(), 2));
    }

    // convenience
    public MatchingResponse match(long shopId, ExtractedDataMap extractedDataMap){
        Offer match;
        String category = null;
        if (extractedDataMap.getData().get(OfferAttribute.CATEGORY_STRING) != null){
            category = extractedDataMap.getData().get(OfferAttribute.CATEGORY_STRING).getValue();
        }

        for(MatchStrategy strategy : getStrategies()){
            match = strategy.match(shopId, extractedDataMap);
            if(match != null){
                getLogger().info("Match found for \"" + match.getOfferTitle().get("0") + "\" with field(s) " + strategy.getMatchReason() + ".");

                return new SuccessfulMatchingResponse(shopId,
                        extractedDataMap.getData().get(OfferAttribute.URL).getValue(),
                        match.getOfferId(),
                        category,
                        match.getCategoryString());
            }
        }

        getLogger().info("No match found for article \"" + extractedDataMap.getData().get(OfferAttribute.OFFER_TITLE).getValue()
                + "\" with price " + extractedDataMap.getData().get(OfferAttribute.PRICE).getValue()
                + " on the URL " + extractedDataMap.getData().get(OfferAttribute.URL).getValue());

        return new UnsuccessfulMatchingResponse(shopId,
                extractedDataMap.getData().get(OfferAttribute.URL).getValue(),
                category);
    }
}