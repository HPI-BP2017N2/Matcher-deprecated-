package de.hpi.matching.model.data;

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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MatchingResponseRepositoryImpl implements MatchingResponseRepository {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    @Autowired
    public MatchingResponseRepositoryImpl(MongoTemplate mongoTemplate) {
        setMongoTemplate(mongoTemplate);
    }

    @Override
    public void saveMatchingResponse( MatchingResponse matchingResponse) {
        long shopId = matchingResponse.getShopId().longValue();
        DBCollection collection = getCollectionForShop(shopId);
        DBObject dbObject = convertMatchingResponseToDbObject(matchingResponse);
        long id = searchResponseId(collection, matchingResponse);
        if(collection != null){
            if(id != -1){
                dbObject.put("_id", id);
            }
            collection.save(dbObject);
        }
    }

    //conversion
    private DBObject convertMatchingResponseToDbObject(MatchingResponse matchingResponse){
        DBObject dbObject = new BasicDBObject();
        getMongoTemplate().getConverter().write(matchingResponse, dbObject);
        return dbObject;
    }

    private long searchResponseId(DBCollection collection, MatchingResponse matchingResponse) {
        DBCursor offerIdCursor = collection.find(new BasicDBObject("offerId", matchingResponse.getOfferId()));
        DBCursor urlCursor = collection.find(new BasicDBObject("url", matchingResponse.getUrl()));
        if (offerIdCursor.hasNext()) { return Long.valueOf(offerIdCursor.next().get("_id").toString()); }
        if (offerIdCursor.hasNext()) { return Long.valueOf(urlCursor.next().get("_id").toString()); }
        return -1;
    }

    private DBCollection getCollectionForShop(long shopId){
        return getMongoTemplate().getCollection(Long.toString(shopId));
    }
}
