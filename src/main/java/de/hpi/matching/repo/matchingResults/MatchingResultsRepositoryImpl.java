package de.hpi.matching.repo.matchingResults;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import de.hpi.restclient.dto.MatchingResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MatchingResultsRepositoryImpl implements MatchingResultsRepository {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    @Autowired
    public MatchingResultsRepositoryImpl(MongoTemplate matchingResultsTemplate) {
        setMongoTemplate(matchingResultsTemplate);
    }

    @Override
    public void saveMatchingResponse( MatchingResponse matchingResponse) {
        long shopId = matchingResponse.getShopId().longValue();
        DBCollection collection = getCollectionForShop(shopId);
        System.out.println(collection);
        DBObject dbObject = convertMatchingResponseToDbObject(matchingResponse);
        String id = searchResponseId(collection, matchingResponse);
        if(collection != null){
            if(id != null){
                dbObject.put("_id", id);
            }
            collection.save(dbObject);
        }
        System.out.println("saved object");
    }

    //conversion
    private DBObject convertMatchingResponseToDbObject(MatchingResponse matchingResponse){
        DBObject dbObject = new BasicDBObject();
        getMongoTemplate().getConverter().write(matchingResponse, dbObject);
        return dbObject;
    }

    private String searchResponseId(DBCollection collection, MatchingResponse matchingResponse) {
        DBCursor offerIdCursor = collection.find(new BasicDBObject("offerId", matchingResponse.getOfferId()));
        DBCursor urlCursor = collection.find(new BasicDBObject("url", matchingResponse.getUrl()));
        if (offerIdCursor.hasNext()) { return offerIdCursor.next().get("_id").toString(); }
        if (offerIdCursor.hasNext()) { return urlCursor.next().get("_id").toString(); }
        return null;
    }

    private DBCollection getCollectionForShop(long shopId){
        return getMongoTemplate().getCollection(Long.toString(shopId));
    }
}
