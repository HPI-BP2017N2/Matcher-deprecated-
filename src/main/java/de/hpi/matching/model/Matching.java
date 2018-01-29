package de.hpi.matching.model;

import de.hpi.matching.dto.MatchingResponse;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class Matching {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private static OfferMatchingRepository repo;

    public Matching (OfferMatchingRepository repo) {
        setRepo(repo);
    }


        public MatchingResponse match(Offer offer){
            if (offer.getEan() != null) {
                List<Offer> response = repo.searchEan(offer.getShopId().longValue(), offer.getEan());
                if (response.size() > 0) {
                    return createMatchingResponse( offer, response.get(0));
                }
            }
            return new MatchingResponse();
        }


        private MatchingResponse createMatchingResponse(Offer parsedOffer, Offer idealoOffer){
            MatchingResponse response = new MatchingResponse();
            response.setIdealoOffer(true);
            response.setIdealoCategory(idealoOffer.getCategoryString());
            response.setShopId(parsedOffer.getShopId());
            response.setOfferId(idealoOffer.getOfferId());
            response.setParsedCategory(parsedOffer.getCategoryString());
            return response;
        }
    }



