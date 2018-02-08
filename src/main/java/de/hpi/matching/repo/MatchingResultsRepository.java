package de.hpi.matching.repo;

import de.hpi.restclient.pojo.MatchingResponse;


public interface MatchingResultsRepository {

    void save(MatchingResponse matchingResponse);

    MatchingResponse searchByUrl(long shopId, String url);

}