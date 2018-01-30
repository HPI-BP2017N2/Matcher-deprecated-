package de.hpi.matching.model;

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
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private static BPBridgeClient client;

    @Autowired
    public OfferMatchingRepository(BPBridgeClient client){
        setClient(client);
    }

    public List<Offer> searchEan(long shopId, String ean){
        return getClient().matchAttribute(shopId, "ean", ean).getOffers();

    }

    public List<Offer> searchSku(long shopId, String sku){
        return getClient().matchAttribute(shopId, "sku", sku).getOffers();

    }

    public List<Offer> searchHan(long shopId, String han){
        return getClient().matchAttribute(shopId, "han", han).getOffers();

    }

    public List<Offer> searchOfferTitle(long shopId, String offerTitle){
        HashMap<String, String> title = new HashMap<String, String>();
        title.put("0", offerTitle);
        return getClient().matchAttribute(shopId, "offerTitle", title.toString()).getOffers();
    }
}
