package de.hpi.matching.model;

import de.hpi.matching.dto.MatchingResponse;
import de.hpi.restclient.clients.BPBridgeClient;
import de.hpi.restclient.dto.MatchAttributeResponse;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class Matching {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private static BPBridgeRepository repo;

    public Matching (BPBridgeRepository repo) {
        setRepo(repo);
    }


        public MatchingResponse match(long shopId, String offerTitle, String ean, String han, String sku, String url, double price, String categoryString){
            if (ean != null) {
                List<Offer> response = repo.searchEan(shopId, ean);
                if (response.size() > 0) {
                    return createMatchingResponse(response.get(0), categoryString);
                    
                }
            }
            return new MatchingResponse();
        }


        private MatchingResponse createMatchingResponse(Offer offer, String parsedCategory){

            MatchingResponse response = new MatchingResponse();
            response.setIdealoCategory(offer.getCategoryString());
            response.setIdealoOffer(true);
            response.setShopId(offer.getShopId());
            response.setOfferId(offer.getOfferId());
            response.setParsedCategory(parsedCategory);


            return response;
        }
    }



