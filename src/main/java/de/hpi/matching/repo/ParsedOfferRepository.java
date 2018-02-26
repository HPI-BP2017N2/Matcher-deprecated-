package de.hpi.matching.repo;


import de.hpi.restclient.pojo.ExtractedDataMap;

public interface ParsedOfferRepository {

    ExtractedDataMap getFirstOffer(long shopId);

    ExtractedDataMap popOffer(long shopId);

}