package de.hpi.matching.controller;

import de.hpi.matching.service.MatchingService;
import de.hpi.restclient.dto.MatchingResponse;
import de.hpi.restclient.dto.ParsedOffer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchingController {
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MatchingService service;


    @Autowired
    public MatchingController(MatchingService service){
        setService(service);
    }

    @RequestMapping(value = "/matcher/match", method = RequestMethod.POST, produces = "application/json")
    public MatchingResponse startMatchingSingleOffer(@RequestBody ParsedOffer parsedOffer){
        return getService().matchSingleOffer(parsedOffer);
    }

    public void startMatchingOffersForShop(long shopId){
        getService().matchOffersForShop(shopId);
    }
}