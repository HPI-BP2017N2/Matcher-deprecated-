package de.hpi.matching.repo.parsedOffers;


import de.hpi.restclient.dto.ParsedOffer;

public interface ParsedOfferRepository {

    ParsedOffer getFirstOffer(long shopId);

    ParsedOffer popOffer(long shopId);

}