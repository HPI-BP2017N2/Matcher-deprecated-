package de.hpi.matching.repo;

import com.mongodb.*;
import de.hpi.restclient.dto.MatchingResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MatchingResultsRepositoryImpl implements MatchingResultsRepository {

    @Autowired
    @Qualifier(value = "matchingResultTemplate")
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    @Override
    public void save(MatchingResponse matchingResponse) {
        DBCollection collection = getCollectionForShop(matchingResponse.getShopId().longValue());
        DBObject dbObject = convertMatchingResponseToDbObject(matchingResponse);
        String id = searchResponseId(collection, matchingResponse);
        if(id != null){
            dbObject.put("_id", new ObjectId(id));
        }
        collection.save(dbObject);
    }

    @Override
    public MatchingResponse searchByUrl(long shopId, String url) {
        return convertDBObjectToMatchingResponse(searchByIdentifier(getCollectionForShop(shopId), "url", url));
    }

    // conversion
    private DBObject convertMatchingResponseToDbObject(MatchingResponse matchingResponse){
        DBObject dbObject = new BasicDBObject();
        getMongoTemplate().getConverter().write(matchingResponse, dbObject);
        return dbObject;
    }

    private MatchingResponse convertDBObjectToMatchingResponse(DBObject matchingResponseDbObject){
        if (matchingResponseDbObject!= null) {
            return getMongoTemplate().getConverter().read(MatchingResponse.class, matchingResponseDbObject);
        }
        return null;
    }


    //actions
    private String searchResponseId(DBCollection collection, MatchingResponse matchingResponse) {
        DBObject offerIdResponse = searchByIdentifier(collection, "offerId", matchingResponse.getOfferId());
        DBObject urlResponse = searchByIdentifier(collection, "url", matchingResponse.getUrl());
        if (offerIdResponse != null) { return offerIdResponse.get("_id").toString(); }
        if (urlResponse != null) { return urlResponse.get("_id").toString(); }
        return null;
    }

    private DBCollection getCollectionForShop(long shopId){
        return getMongoTemplate().getCollection(Long.toString(shopId));
    }

    private DBObject searchByIdentifier(DBCollection collection, String identifier, Object value){
        DBCursor cursor = collection.find(new BasicDBObject(identifier, value));
        if (cursor.hasNext() && cursor.size() == 1) {
            DBObject dbObject = cursor.next();
            if (dbObject.get(identifier).equals(value)) {
                return dbObject;
            }
        }
        return null;
    }
}