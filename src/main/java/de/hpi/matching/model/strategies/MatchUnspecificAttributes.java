package de.hpi.matching.model.strategies;

import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.restclient.pojo.ExtractedDataEntry;
import de.hpi.restclient.pojo.ExtractedDataMap;
import de.hpi.restclient.pojo.Offer;
import de.hpi.restclient.pojo.OfferAttribute;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class MatchUnspecificAttributes implements MatchStrategy{

    private OfferMatchingRepository repo;
    private List<Offer> brandOffers, descriptionOffers, categoryOffers;
    private Map<Number, Integer> offerIdCount;
    private int matchingThreshold;

    // initialization
    public MatchUnspecificAttributes(OfferMatchingRepository repo, int threshold) {
        setRepo(repo);
        cleanOffers();
        setMatchingThreshold(threshold);
    }

    // convenience
    @Override
    public Offer match(long shopId, ExtractedDataMap extractedDataMap) {
        String brand = getExtractedDataMapValue(extractedDataMap, OfferAttribute.BRAND_SEARCHTEXT);
        String category = getExtractedDataMapValue(extractedDataMap, OfferAttribute.CATEGORY_STRING);
        String description = getExtractedDataMapValue(extractedDataMap, OfferAttribute.DESCRIPTION);
        fetchMatchingOffers(shopId, brand, category, description);
        countOfferIDs();

        Offer result = pickBestMatchingOffer(shopId);
        cleanOffers();
        return result;
    }


    @Override
    public String getMatchReason() {
        return "brandSearchtext, description, categoryString";
    }

    // actions
    private void fetchMatchingOffers(long shopId, String brand, String category, String description) {
        setCategoryOffers(getRepo().searchCategory(shopId, category));
        setBrandOffers(getRepo().searchBrand(shopId, brand));
        setDescriptionOffers(getRepo().searchDescription(shopId, description));
    }

    private void cleanOffers() {
        setOfferIdCount(new HashMap<>());
    }

    private void countOfferIDs() {
        countOfferIDs(getBrandOffers());
        countOfferIDs(getDescriptionOffers());
        countOfferIDs(getCategoryOffers());
    }

    private String getExtractedDataMapValue(ExtractedDataMap extractedDataMap, Object attribute) {
        ExtractedDataEntry entry = extractedDataMap.getData().get(attribute);
        if(entry != null) {
            return entry.getValue();
        }
        return null;
    }

    private void countOfferIDs(List<Offer> offers) {
        Number offerId;
        for (Offer offer : offers) {
            offerId = offer.getOfferId();
            if(getOfferIdCount().containsKey(offerId)) {
                getOfferIdCount().replace(offerId, getOfferIdCount().get(offerId) + 1);
            } else {
                getOfferIdCount().put(offerId, 1);
            }
        }
    }

    private Offer pickBestMatchingOffer(long shopId) {
        if(getOfferIdCount().isEmpty()) return null;
        Number bestOfferId = searchBestScoredOfferId();
        if(bestOfferId != null) return getRepo().searchOfferId(shopId, bestOfferId.toString());
        return null;
    }

    private Number searchBestScoredOfferId() {
        Number bestOfferId = null;
        Integer highestOccurrence = 0;
        for(Number offerId : getOfferIdCount().keySet()) {
            if(getOfferIdCount().get(offerId) > highestOccurrence) {
                highestOccurrence = getOfferIdCount().get(offerId);
                bestOfferId = offerId;
            }
        }
        if(highestOccurrence >= getMatchingThreshold()) return bestOfferId;
        return null;
    }

}