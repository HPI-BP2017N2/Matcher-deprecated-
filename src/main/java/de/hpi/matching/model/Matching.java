package de.hpi.matching.model;

import de.hpi.matching.dto.MatchingResponse;
import de.hpi.matching.model.strategies.MatchEan;
import de.hpi.matching.model.strategies.MatchHan;
import de.hpi.matching.model.strategies.MatchSku;
import de.hpi.matching.model.strategies.MatchStrategy;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class Matching {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private OfferMatchingRepository repo;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ArrayList<MatchStrategy> strategies;

    public Matching (OfferMatchingRepository repo) {
        setRepo(repo);
        setStrategies(new ArrayList<MatchStrategy>());
        getStrategies().add(new MatchEan(getRepo()));
        getStrategies().add(new MatchHan(getRepo()));
        getStrategies().add(new MatchSku(getRepo()));
    }
    
    public MatchingResponse match(Offer offer){

        Offer match;

        for(MatchStrategy strategy : getStrategies()){
            match = strategy.match(offer);
            if(match != null){
                return createMatchingResponse(offer, match);
            }
        }

        return new MatchingResponse();
    }

    private MatchingResponse createMatchingResponse(Offer parsedOffer, Offer idealoOffer){
        MatchingResponse response = new MatchingResponse();
        response.setIdealoOffer(true);
        response.setIdealoCategory(idealoOffer.getCategoryString());
        response.setShopId(parsedOffer.getShopId());
        response.setOfferId(idealoOffer.getOfferId());
        response.setParsedCategory(parsedOffer.getCategoryString());
        return response;
    }
}