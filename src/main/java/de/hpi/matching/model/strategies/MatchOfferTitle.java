package de.hpi.matching.model.strategies;

import de.hpi.matching.model.OfferMatchingRepository;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MatchOfferTitle implements MatchStrategy {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private static OfferMatchingRepository repo;

    public MatchOfferTitle(OfferMatchingRepository repo) {
        setRepo(repo);
    }


    @Override
    public Offer match(Offer offer) {
        if (offer.getOfferTitle() != null) {
            for(String title : offer.getOfferTitle().values()) {
                List<Offer> response = getRepo().searchOfferTitle(offer.getShopId().longValue(), title);
                if (response.size() > 0) {
                    return response.get(0);
                }
            }
        }

        return null;
    }
}
