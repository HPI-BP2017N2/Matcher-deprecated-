package de.hpi.matching.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


public class MatchingResponse {
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Number shopId, offerId;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String parsedCategory, idealoCategory;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private boolean isIdealoOffer;

    // initialization
    public MatchingResponse(Number shopId, String parsedCategory){
        self().setIdealoOffer(false);
        self().setShopId(shopId);
        self().setParsedCategory(parsedCategory);
    }

    public MatchingResponse(Number shopId, Number offerId, String parsedCategory, String idealoCategory){
        self().setIdealoOffer(true);
        self().setShopId(shopId);
        self().setOfferId(offerId);
        self().setParsedCategory(parsedCategory);
        self().setIdealoCategory(idealoCategory);
    }

    private MatchingResponse self(){
        return this;
    }
}
