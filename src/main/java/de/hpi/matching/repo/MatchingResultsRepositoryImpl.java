package de.hpi.matching.repo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import de.hpi.restclient.dto.MatchingResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
            System.out.println(matchingResponse.getOfferId());
            System.out.println(id);
            dbObject.put("_id", id);
        }
        collection.save(dbObject);
    }

    @Override
    public List<MatchingResponse> searchByUrl(long shopId, String url) {
        List<MatchingResponse> result = new ArrayList<>();
        DBCollection collection = getCollectionForShop(shopId);
        DBCursor urlCursor = collection.find(new BasicDBObject("url", url));
        while (urlCursor.hasNext()){
            result.add(convertDBObjectToMatchingResponse(urlCursor.next()));

        }
        return result;
    }

    // conversion
    private DBObject convertMatchingResponseToDbObject(MatchingResponse matchingResponse){
        DBObject dbObject = new BasicDBObject();
        getMongoTemplate().getConverter().write(matchingResponse, dbObject);
        return dbObject;
    }

    private MatchingResponse convertDBObjectToMatchingResponse(DBObject matchingResponseDbObject){
        return getMongoTemplate().getConverter().read(MatchingResponse.class, matchingResponseDbObject);
    }


    //actions
    private String searchResponseId(DBCollection collection, MatchingResponse matchingResponse) {
        DBCursor offerIdCursor = collection.find(new BasicDBObject("offerId", matchingResponse.getOfferId()));
        DBCursor urlCursor = collection.find(new BasicDBObject("url", matchingResponse.getUrl()));
        if (offerIdCursor.hasNext()) { return offerIdCursor.next().get("_id").toString(); }
        if (urlCursor.hasNext()) { return urlCursor.next().get("_id").toString(); }
        return null;
    }

    private DBCollection getCollectionForShop(long shopId){
        return getMongoTemplate().getCollection(Long.toString(shopId));
    }
}