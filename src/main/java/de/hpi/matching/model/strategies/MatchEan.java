package de.hpi.matching.model.strategies;

import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.restclient.pojo.ExtractedDataMap;
import de.hpi.restclient.pojo.Offer;
import de.hpi.restclient.pojo.OfferAttribute;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MatchEan implements MatchStrategy {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private OfferMatchingRepository repo;

    // initialization
    public MatchEan(OfferMatchingRepository repo) {
        setRepo(repo);
    }

    // convenience
    @Override
    public Offer match(long shopId, ExtractedDataMap extractedDataMap) {
        String ean = extractedDataMap.getData().get(OfferAttribute.EAN).getValue();
        if (ean != null) {
            List<Offer> response = getRepo().searchEan(shopId, ean);
            if (response.size() > 0) {
                return response.get(0);
            }
        }

        return null;
    }

    @Override
    public String getMatchReason() {
        return "EAN";
    }

}