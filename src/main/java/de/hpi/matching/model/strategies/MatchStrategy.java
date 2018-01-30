package de.hpi.matching.model.strategies;

import de.hpi.restclient.pojo.Offer;


public interface MatchStrategy {

    // initialization
    Offer match(Offer offer);
}
