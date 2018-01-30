package de.hpi.matching.dto;

import lombok.AccessLevel;
import lombok.Getter;

public class ParsedOffer {
    @Getter(AccessLevel.PUBLIC) private long shopId;
    @Getter(AccessLevel.PUBLIC) private String offerTitle, ean, han, sku, url, categoryString;
    @Getter(AccessLevel.PUBLIC) private double price;
}