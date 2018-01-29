package de.hpi.matching.model;

import de.hpi.restclient.clients.BPBridgeClient;
import de.hpi.restclient.dto.MatchAttributeResponse;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BPBridgeRepository {
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private static BPBridgeClient client;

    @Autowired
    public BPBridgeRepository (BPBridgeClient client){
        setClient(client);
    }

    public List<Offer> searchEan(long shopId, String ean){
        return client.matchAttribute(shopId, "ean", ean).getOffers();

    }

    public List<Offer> searchSku(long shopId, String sku){
        return client.matchAttribute(shopId, "sku", sku).getOffers();

    }

    public List<Offer> searchHan(long shopId, String han){
        return client.matchAttribute(shopId, "han", han).getOffers();

    }
}
