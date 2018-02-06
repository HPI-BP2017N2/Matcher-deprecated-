package de.hpi.matching.repo;

import de.hpi.restclient.clients.BPBridgeClient;
import de.hpi.restclient.dto.MatchAttributeResponse;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class OfferMatchingRepository {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private BPBridgeClient client;

    // initialization
    @Autowired
    public OfferMatchingRepository(BPBridgeClient client){
        setClient(client);
    }

    // convenience
    public List<Offer> searchEan(long shopId, String ean){
        return searchByAttribute(shopId, "ean", ean);
    }

    public List<Offer> searchSku(long shopId, String sku){
        return  searchByAttribute(shopId, "sku", sku);
    }

    public List<Offer> searchHan(long shopId, String han){
        return  searchByAttribute(shopId, "han", han);
    }

    public List<Offer> searchOfferTitle(long shopId, String offerTitle){
        /*
            TO DO: fix search for Map objects instead of String
         */

        HashMap<String, String> title = new HashMap<>();
        title.put("0", offerTitle);
        String titleString = "{ \"0\" : \"" + offerTitle + "\" }";

        return searchByAttribute(shopId, "offerTitle", titleString);
    }

    // actions
    private List<Offer> searchByAttribute(long shopId, String attribute, String value) {
        return getClient().matchAttribute(shopId, attribute, value).getOffers();
    }
}