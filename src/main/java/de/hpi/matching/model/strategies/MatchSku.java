package de.hpi.matching.model.strategies;

import de.hpi.matching.model.OfferMatchingRepository;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MatchSku implements MatchStrategy {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private static OfferMatchingRepository repo;

    public MatchSku(OfferMatchingRepository repo) {
        setRepo(repo);
    }


    @Override
    public Offer match(Offer offer) {
        if (offer.getSku() != null) {
            List<Offer> response = getRepo().searchSku(offer.getShopId().longValue(), offer.getSku());
            if (response.size() > 0) {
                return response.get(0);
            }
        }

        return null;
    }
}
