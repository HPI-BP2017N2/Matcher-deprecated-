package de.hpi.matching.model.strategies;

import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.restclient.dto.ParsedOffer;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MatchHan implements MatchStrategy {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private OfferMatchingRepository repo;

    // initialization
    public MatchHan(OfferMatchingRepository repo) {
        setRepo(repo);
    }

    // convenience
    @Override
    public Offer match(ParsedOffer offer) {
        if (offer.getHan() != null) {
            List<Offer> response = getRepo().searchHan(offer.getShopId(), offer.getHan());
            if (response.size() > 0) {
                return response.get(0);
            }
        }

        return null;
    }

    @Override
    public String getMatchReason() {
        return "HAN";
    }
}