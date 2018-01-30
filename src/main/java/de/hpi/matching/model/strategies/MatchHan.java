package de.hpi.matching.model.strategies;

import de.hpi.matching.model.OfferMatchingRepository;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MatchHan implements MatchStrategy {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private static OfferMatchingRepository repo;

    public MatchHan(OfferMatchingRepository repo) {
        setRepo(repo);
    }


    @Override
    public Offer match(Offer offer) {
        if (offer.getHan() != null) {
            List<Offer> response = getRepo().searchHan(offer.getShopId().longValue(), offer.getHan());
            if (response.size() > 0) {
                return response.get(0);
            }
        }

        return null;
    }
}
