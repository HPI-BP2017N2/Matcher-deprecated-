package de.hpi.matching.repo;

import de.hpi.restclient.dto.MatchingResponse;
import org.springframework.stereotype.Repository;


public interface MatchingResultsRepository {

    void saveMatchingResponse(MatchingResponse matchingResponse);

}