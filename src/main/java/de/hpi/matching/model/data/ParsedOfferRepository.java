package de.hpi.matching.model.data;


import de.hpi.restclient.dto.ParsedOffer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParsedOfferRepository {

    List<ParsedOffer> getFirstOffersOfShop(long shopID, int maxOffers, int offset);

}