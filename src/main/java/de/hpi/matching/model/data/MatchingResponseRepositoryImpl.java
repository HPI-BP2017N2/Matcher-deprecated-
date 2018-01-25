package de.hpi.matching.model.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
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
    public void saveMatchingResponse(long shopId, String parsedCategory, String idealoCategory, boolean isIdealoOffer, Number offerId) {

        // TODO: save the data into mongo!

    }


}
