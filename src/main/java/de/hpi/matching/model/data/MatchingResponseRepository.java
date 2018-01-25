package de.hpi.matching.model.data;

import org.springframework.stereotype.Repository;

@Repository
public interface MatchingResponseRepository {

    public void saveMatchingResponse(long shopId, String parsedCategory, String idealoCategory, boolean isIdealoOffer, Number offerId);


}
