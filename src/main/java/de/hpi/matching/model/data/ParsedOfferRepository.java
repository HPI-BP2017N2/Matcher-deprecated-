package de.hpi.matching.model.data;


import de.hpi.restclient.dto.ParsedOffer;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ParsedOfferRepository {

    ParsedOffer getFirstOffer(long shopId);
    void removeFirstOffer(long shopId);

}