package de.hpi.matching.controller;

import de.hpi.matching.dto.MatchingResponse;
import de.hpi.matching.dto.RestMatchingConfig;
import de.hpi.matching.service.MatchingService;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MatchingController {
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MatchingService service;

    @Autowired
    public MatchingController(MatchingService service){
        setService(service);
    }

    @RequestMapping(value = "/matcher/match", method = RequestMethod.POST, produces = "application/json")
    public MatchingResponse startMatching(@RequestBody RestMatchingConfig restMatchingConfig){
        Offer offer = new Offer();
        Map<String, String> title = new HashMap<String, String>();
        Map<String, Number> price = new HashMap<String, Number>();
        Map<String, String> url = new HashMap<String, String>();

        offer.setShopId(restMatchingConfig.getShopId());
        offer.setEan(restMatchingConfig.getEan());
        offer.setHan(restMatchingConfig.getHan());
        offer.setSku(restMatchingConfig.getSku());
        offer.setCategoryString(restMatchingConfig.getCategoryString());


        title.put("0", restMatchingConfig.getOfferTitle());
        offer.setOfferTitle(title);
        url.put("0", restMatchingConfig.getUrl());
        offer.setUrl(url);
        price.put("0", restMatchingConfig.getPrice());
        offer.setPrice(price);

        return service.match(offer);
    }
}
