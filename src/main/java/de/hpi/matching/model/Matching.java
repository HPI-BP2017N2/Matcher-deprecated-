package de.hpi.matching.model;

import de.hpi.matching.dto.MatchingResponse;
import de.hpi.matching.model.strategies.*;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class Matching {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private OfferMatchingRepository repo;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ArrayList<MatchStrategy> strategies;

    // initialization
    public Matching (OfferMatchingRepository repo) {
        setRepo(repo);
        setStrategies(new ArrayList<MatchStrategy>());
        getStrategies().add(new MatchEan(getRepo()));
        getStrategies().add(new MatchHan(getRepo()));
        getStrategies().add(new MatchSku(getRepo()));
        getStrategies().add(new MatchOfferTitle(getRepo()));
    }

    // convenience
    public MatchingResponse match(Offer offer){
        Offer match;

        for(MatchStrategy strategy : getStrategies()){
            match = strategy.match(offer);
            if(match != null){
                return new MatchingResponse(offer.getShopId(), match.getOfferId(), offer.getCategoryString(), match.getCategoryString());
            }
        }

        return new MatchingResponse(offer.getShopId(), offer.getCategoryString());
    }
}