package de.hpi.matching.model.strategies;

import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.restclient.pojo.ExtractedDataEntry;
import de.hpi.restclient.pojo.ExtractedDataMap;
import de.hpi.restclient.pojo.Offer;
import de.hpi.restclient.pojo.OfferAttribute;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MatchSku implements MatchStrategy {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private OfferMatchingRepository repo;

    // initialization
    public MatchSku(OfferMatchingRepository repo) {
        setRepo(repo);
    }

    // convenience
    @Override
    public Offer match(long shopId, ExtractedDataMap extractedDataMap) {
        ExtractedDataEntry sku = extractedDataMap.getData().get(OfferAttribute.SKU);
        if (sku != null) {
            List<Offer> response = getRepo().searchSku(shopId, sku.getValue());
            if (response.size() > 0) {
                return response.get(0);
            }
        }

        return null;
    }

    @Override
    public String getMatchReason() {
        return "SKU";
    }
}