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
        String category = getExtractedDataMapValue(extractedDataMap, OfferAttribute.CATEGORY_STRING);

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

        String offerTitle = getExtractedDataMapValue(extractedDataMap, OfferAttribute.OFFER_TITLE);
        String price = getExtractedDataMapValue(extractedDataMap, OfferAttribute.PRICE);
        getLogger().info("No match found for article \"" + offerTitle
                + "\" with price " + price
                + " on the URL " + extractedDataMap.getData().get(OfferAttribute.URL).getValue());

        return new UnsuccessfulMatchingResponse(shopId,
                extractedDataMap.getData().get(OfferAttribute.URL).getValue(),
                category);
    }

    // actions
    private String getExtractedDataMapValue(ExtractedDataMap extractedDataMap, Object attribute) {
        ExtractedDataEntry entry = extractedDataMap.getData().get(attribute);
        if(entry != null) {
            return entry.getValue();
        }
        return null;
    }
}