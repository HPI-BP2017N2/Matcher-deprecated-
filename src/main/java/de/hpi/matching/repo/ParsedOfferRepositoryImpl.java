package de.hpi.matching.repo;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import de.hpi.restclient.pojo.ExtractedDataMap;
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
    public ExtractedDataMap getFirstOffer(long shopId) {
        DBCollection collection = getCollectionForShop(shopId);
        return convertDBObjectToExtractedDataMap(collection.findOne());
    }

    @Override
    public ExtractedDataMap popOffer(long shopId) {
        DBCollection collection = getCollectionForShop(shopId);
        DBObject firstOffer = collection.findOne();

        if(firstOffer != null) {
            collection.remove(firstOffer);
            return convertDBObjectToExtractedDataMap(firstOffer);
        }
        return null;
    }

    // conversion
    private ExtractedDataMap convertDBObjectToExtractedDataMap(DBObject extractedDataMapDbObject) {
        return getMongoTemplate().getConverter().read(ExtractedDataMap.class, extractedDataMapDbObject);
    }

    // actions
    private DBCollection getCollectionForShop(long shopId){
        return getMongoTemplate().getCollection(Long.toString(shopId));
    }
}