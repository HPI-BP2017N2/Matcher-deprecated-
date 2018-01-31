package de.hpi.matching.model.data;

import de.hpi.restclient.dto.MatchingResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MatchingResponseRepositoryImpl implements MatchingResponseRepository {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    @Autowired
    public MatchingResponseRepositoryImpl(MongoTemplate mongoTemplate) {
        setMongoTemplate(mongoTemplate);
    }

    @Override
    public void saveMatchingResponse(MatchingResponse matchingResponse) {

        getMongoTemplate().insert(matchingResponse);
    }

    @Override
    public MatchingResponse findByOfferId(Number offerId, String collection) {
        return getMongoTemplate().findById(offerId, MatchingResponse.class, collection);
    }


}
