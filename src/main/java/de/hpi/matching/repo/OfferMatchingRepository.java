package de.hpi.matching.repo;

import de.hpi.restclient.clients.BPBridgeClient;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OfferMatchingRepository {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private BPBridgeClient client;

    // initialization
    @Autowired
    public OfferMatchingRepository(BPBridgeClient client) {
        setClient(client);
    }

    // convenience
    public Offer searchOfferId(long shopId, String offerId) {
        return searchByStringAttribute(shopId, "offerId", offerId).get(0);
    }

    public List<Offer> searchEan(long shopId, String ean) {
        return searchByStringAttribute(shopId, "ean", ean);
    }

    public List<Offer> searchSku(long shopId, String sku) {
        return  searchByStringAttribute(shopId, "sku", sku);
    }

    public List<Offer> searchHan(long shopId, String han) {
        return  searchByStringAttribute(shopId, "han", han);
    }

    public List<Offer> searchBrand(long shopId, String brand) {
        return  searchByStringAttribute(shopId, "brandSearchtext", brand);
    }

    public List<Offer> searchCategory(long shopId, String category) {
        return  searchByStringAttribute(shopId, "categoryString", category);
    }

    public List<Offer> searchOfferTitle(long shopId, String offerTitle) {
        return searchByMapAttribute(shopId, "offerTitle", offerTitle);
    }

    public List<Offer> searchDescription(long shopId, String description) {
        return searchByMapAttribute(shopId, "description", description);
    }

    // actions
    private List<Offer> searchByStringAttribute(long shopId, String attribute, String value) {
        return getClient().matchAttribute(shopId, attribute, value).getOffers();
    }

    private List<Offer> searchByMapAttribute(long shopId, String attribute, String value) {
        return getClient().matchAttributeWithMap(shopId, attribute, value).getOffers();
    }
}