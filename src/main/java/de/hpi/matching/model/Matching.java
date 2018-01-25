package de.hpi.matching.model;

import de.hpi.matching.dto.MatchingResponse;
import de.hpi.restclient.clients.BPBridgeClient;
import de.hpi.restclient.dto.MatchAttributeResponse;


public class Matching {

    public static MatchingResponse match(BPBridgeClient client, long shopId, String offerTitle, String ean, String han, String sku, String url, double price, String categoryString) {

        if (ean != null) {
            MatchAttributeResponse response = client.matchAttribute(shopId, "ean", ean);
            if (response.getOffers().size() > 0) {
                MatchingResponse response1 = new MatchingResponse();

                response1.setIdealoOffer(true);
                response1.setShopId(shopId);
                response1.setOfferId(response.getOffers().get(0).getOfferId());
                response1.setParsedCategory(categoryString);
                response1.setIdealoCategory(response.getOffers().get(0).getCategoryString());
                System.out.println(response1.getIdealoCategory());
                return response1;
            }
        }
        return new MatchingResponse();
    }

}



