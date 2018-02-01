package de.hpi.matching.repo.parsedOffers;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import de.hpi.restclient.dto.ParsedOffer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ParsedOfferRepositoryImpl implements ParsedOfferRepository {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    //initialization
    @Autowired
    public ParsedOfferRepositoryImpl(MongoTemplate mongoTemplate) {
        setMongoTemplate(mongoTemplate);
    }

    // convenience
    @Override
    public ParsedOffer getFirstOffer(long shopId) {
        DBCollection collection = getMongoTemplate().getCollection(Long.toString(shopId));
        return convertDBObjectToParsedOffer(collection.findOne());
    }

    @Override
    public void removeFirstOffer(long shopId) {
        DBCollection collection = getMongoTemplate().getCollection(Long.toString(shopId));
        DBObject firstElement = collection.findOne();
        collection.remove(firstElement);

    }

    //conversion
    private ParsedOffer convertDBObjectToParsedOffer(DBObject parsedOfferDbObject){
        return getMongoTemplate().getConverter().read(ParsedOffer.class, parsedOfferDbObject);
    }
}