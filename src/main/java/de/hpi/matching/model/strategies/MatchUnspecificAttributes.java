package de.hpi.matching.model.strategies;

import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.restclient.dto.ParsedOffer;
import de.hpi.restclient.pojo.Offer;
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
    public Offer match(ParsedOffer offer) {
        fetchMatchingOffers(offer);
        countOfferIDs();

        Offer result = pickBestMatchingOffer(offer.getShopId());
        cleanOffers();
        return result;
    }


    @Override
    public String getMatchReason() {
        return "brandSearchtext, description, categoryString";
    }

    // actions
    private void fetchMatchingOffers(ParsedOffer offer) {
        setCategoryOffers(getRepo().searchCategory(offer.getShopId(), offer.getCategoryString()));
        setBrandOffers(getRepo().searchBrand(offer.getShopId(),offer.getBrand()));
        setDescriptionOffers(getRepo().searchDescription(offer.getShopId(), offer.getDescription()));
    }

    private void cleanOffers() {
        setOfferIdCount(new HashMap<>());
    }

    private void countOfferIDs() {
        countOfferIDs(getBrandOffers());
        countOfferIDs(getDescriptionOffers());
        countOfferIDs(getCategoryOffers());
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