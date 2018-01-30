package de.hpi.matching.model.data;

import de.hpi.matching.dto.ParsedOffer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParsedOfferRepository {

    public List<ParsedOffer> getFirstOffersOfShop(long shopID, int maxOffers, int offset);

}