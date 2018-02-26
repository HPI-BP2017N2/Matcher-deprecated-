package de.hpi.matching.controller;

import de.hpi.matching.service.MatchingService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchingController {
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MatchingService service;


    // initialization
    @Autowired
    public MatchingController(MatchingService service){
        setService(service);
    }

    // convenience
    public void startMatchingOffersForShop(long shopId){
        getService().matchOffersForShop(shopId);
    }

}