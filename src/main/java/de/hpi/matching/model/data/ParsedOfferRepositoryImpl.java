package de.hpi.matching.model.data;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import de.hpi.restclient.dto.ParsedOffer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class ParsedOfferRepositoryImpl implements ParsedOfferRepository {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    //initialization
    @Autowired
    public ParsedOfferRepositoryImpl(MongoTemplate mongoTemplate) {
        setMongoTemplate(mongoTemplate);
    }


    @Override
    public List<ParsedOffer> getFirstOffersOfShop(long shopID, int maxOffers, int offset) {
        List<ParsedOffer> offers = new LinkedList<ParsedOffer>();
        DBCollection collection = getMongoTemplate().getCollection(Long.toString(shopID));
        if (collection != null) {
            DBCursor cursor = collection.find(new BasicDBObject("shopId", shopID)).skip(offset).limit(maxOffers);
            while (cursor.hasNext()) {
                offers.add(convertDBObjectToParsedOffer(cursor.next()));
            }
        }
        return offers;
    }

    //conversion
    private ParsedOffer convertDBObjectToParsedOffer(DBObject offerDbObject){
        return getMongoTemplate().getConverter().read(ParsedOffer.class, offerDbObject);
    }
}