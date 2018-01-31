package de.hpi.matching.service;

import de.hpi.matching.model.Matching;
import de.hpi.matching.model.OfferMatchingRepository;
import de.hpi.restclient.dto.MatchingResponse;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private static OfferMatchingRepository repo;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private static Matching matching;

    // initialization
    @Autowired
    public MatchingService (OfferMatchingRepository repository){
        setRepo(repository);
        setMatching(new Matching(getRepo()));
    }

    // convenience
    public MatchingResponse match(Offer offer) {
        return getMatching().match(offer);
    }
}