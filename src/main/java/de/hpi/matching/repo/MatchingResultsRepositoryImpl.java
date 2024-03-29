package de.hpi.matching.repo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import de.hpi.restclient.pojo.MatchingResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class MatchingResultsRepositoryImpl implements MatchingResultsRepository {

    @Autowired
    @Qualifier(value = "matchingResultTemplate")
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    // convenience
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
        if (matchingResponseDbObject != null) {
            return getMongoTemplate().getConverter().read(MatchingResponse.class, matchingResponseDbObject);
        }
        return null;
    }

    // actions
    private String searchResponseId(DBCollection collection, MatchingResponse matchingResponse) {
        String id = searchIdByOfferId(collection, matchingResponse.getOfferId());
        if(id != null) { return id; }
        return searchIdByUrl(collection, matchingResponse.getUrl());
    }

    private DBCollection getCollectionForShop(long shopId){
        if(!collectionExists(shopId)) {
            createCollection(shopId);
        }
        return getMongoTemplate().getCollection(Long.toString(shopId));
    }

    private DBObject searchByIdentifier(DBCollection collection, String identifier, Object value){
        DBCursor cursor = collection.find(new BasicDBObject(identifier, value));
        if(cursor.size() == 1) {
            DBObject dbObject = cursor.next();
            if (dbObject.get(identifier).equals(value)) {
                return dbObject;
            }
        }
        return null;
    }

    private String searchIdByOfferId(DBCollection collection, Number offerId) {
        if(offerId == null) return null;
        DBObject response = searchByIdentifier(collection, "offerId", offerId);
        if(response != null) { return response.get("_id").toString(); }
        return null;
    }

    private String searchIdByUrl(DBCollection collection, String url) {
        DBObject response = searchByIdentifier(collection, "url", url);
        if(response != null) { return response.get("_id").toString(); }
        return null;
    }

    private void createCollection(long shopId) {
        getMongoTemplate().createCollection(Long.toString(shopId));
    }

    // conditional
    private boolean collectionExists(long shopId) {
        Set<String> collections = getMongoTemplate().getCollectionNames();
        return collections.contains(Long.toString(shopId));
    }

}