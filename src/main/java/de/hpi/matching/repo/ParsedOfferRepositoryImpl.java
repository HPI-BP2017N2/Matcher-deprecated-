package de.hpi.matching.repo;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import de.hpi.restclient.dto.ParsedOffer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ParsedOfferRepositoryImpl implements ParsedOfferRepository {

    @Autowired
    @Qualifier(value = "parsedOfferTemplate")
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    // convenience
    @Override
    public ParsedOffer getFirstOffer(long shopId) {
        DBCollection collection = getCollectionForShop(shopId);
        return convertDBObjectToParsedOffer(collection.findOne());
    }

    @Override
    public ParsedOffer popOffer(long shopId) {
        DBCollection collection = getCollectionForShop(shopId);
        DBObject firstOffer = collection.findOne();
        collection.remove(firstOffer);
        return convertDBObjectToParsedOffer(firstOffer);
    }

    // conversion
    private ParsedOffer convertDBObjectToParsedOffer(DBObject parsedOfferDbObject){
        return getMongoTemplate().getConverter().read(ParsedOffer.class, parsedOfferDbObject);
    }

    // actions
    private DBCollection getCollectionForShop(long shopId){
        return getMongoTemplate().getCollection(Long.toString(shopId));
    }
}