package de.hpi.matching.model;

import de.hpi.matching.model.strategies.*;
import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.restclient.dto.ParsedOffer;
import de.hpi.restclient.pojo.MatchingResponse;
import de.hpi.restclient.pojo.Offer;
import de.hpi.restclient.pojo.SuccessfulMatchingResponse;
import de.hpi.restclient.pojo.UnsuccessfulMatchingResponse;
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
    public MatchingResponse match(ParsedOffer offer){
        Offer match;
        for(MatchStrategy strategy : getStrategies()){
            match = strategy.match(offer);
            if(match != null){
                getLogger().info("Match found for \"" + match.getOfferTitle().get("0") + "\" with field(s) " + strategy.getMatchReason() + ".");
                return new SuccessfulMatchingResponse(offer.getShopId(), offer.getUrl(), match.getOfferId(), offer.getCategoryString(), match.getCategoryString());
            }
        }

        getLogger().info("No match found for article \"" + offer.getOfferTitle() + "\" with price " + offer.getPrice() + " on the URL " + offer.getUrl());
        return new UnsuccessfulMatchingResponse(offer.getShopId(), offer.getUrl(), offer.getCategoryString());
    }
}