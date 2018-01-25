package de.hpi.matching.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class MatchingResponse {
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) long shopId;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String parsedCategory;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private String idealoCategory;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private boolean isIdealoOffer;
    @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) private Number offerId;


}
