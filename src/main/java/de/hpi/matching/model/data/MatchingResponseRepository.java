package de.hpi.matching.model.data;

import de.hpi.restclient.dto.MatchingResponse;
import org.springframework.stereotype.Repository;


public interface MatchingResponseRepository {

    void saveMatchingResponse(MatchingResponse matchingResponse);

}