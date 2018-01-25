package de.hpi.matching.dto;

import lombok.AccessLevel;
import lombok.Getter;

public class MatchingResponse {
    @Getter(AccessLevel.PUBLIC) private long shopID;
    @Getter(AccessLevel.PUBLIC) private String offerTitle;
    @Getter(AccessLevel.PUBLIC) private String ean;
    @Getter(AccessLevel.PUBLIC) private String han;
    @Getter(AccessLevel.PUBLIC) private String sku;
    @Getter(AccessLevel.PUBLIC) private String url;
    @Getter(AccessLevel.PUBLIC) private double price;
    @Getter(AccessLevel.PUBLIC) private String categoryString;
}
