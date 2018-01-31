package de.hpi.matching.model.data;

import de.hpi.restclient.dto.MatchingResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingResponseRepository {

    void saveMatchingResponse(MatchingResponse matchingResponse);

    MatchingResponse findByOfferId(Number offerId, String collection);
}