package de.hpi.matching.repo;

import de.hpi.restclient.dto.MatchingResponse;

import java.util.List;


public interface MatchingResultsRepository {

    void save(MatchingResponse matchingResponse);

    List<MatchingResponse> searchByUrl(long shopId, String url);

}