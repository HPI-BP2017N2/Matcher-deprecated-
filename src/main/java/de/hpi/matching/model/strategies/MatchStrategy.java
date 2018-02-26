package de.hpi.matching.model.strategies;

import de.hpi.restclient.pojo.ExtractedDataMap;
import de.hpi.restclient.pojo.Offer;


public interface MatchStrategy {

    // convenience
    Offer match(long shopId, ExtractedDataMap extractedDataMap);
    String getMatchReason();
}
