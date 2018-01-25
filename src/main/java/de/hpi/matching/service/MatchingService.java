package de.hpi.matching.service;

import de.hpi.matching.dto.MatchingResponse;
import de.hpi.matching.model.Matching;
import de.hpi.restclient.clients.BPBridgeClient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private static BPBridgeClient client;

    @Autowired
    public MatchingService (BPBridgeClient client){
        setClient(client);
    }

    public MatchingResponse match(long shopId, String offerTitle, String ean, String han, String sku, String url, double price, String categoryString) {
        MatchingResponse response = Matching.match(getClient(), shopId, offerTitle, ean, han, sku, url, price, categoryString);
        System.out.println(response.getIdealoCategory());
        return response;

    }
}


