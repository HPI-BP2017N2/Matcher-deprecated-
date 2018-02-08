package de.hpi.matching.model.strategies;

import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.restclient.dto.ParsedOffer;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MatchOfferTitle implements MatchStrategy {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private OfferMatchingRepository repo;

    // initialization
    public MatchOfferTitle(OfferMatchingRepository repo) {
        setRepo(repo);
    }

    // convenience
    @Override
    public Offer match(ParsedOffer offer) {
        if (offer.getOfferTitle() != null) {
            List<Offer> response = getRepo().searchOfferTitle(offer.getShopId(), offer.getOfferTitle());
            if (response.size() > 0) {
                return response.get(0);
            }
        }

        return null;
    }

    @Override
    public String getMatchReason() {
        return "offerTitle";
    }
}