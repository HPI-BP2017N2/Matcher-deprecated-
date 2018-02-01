package de.hpi.matching.controller;

import de.hpi.matching.model.data.ParsedOfferRepository;
import de.hpi.matching.service.MatchingService;
import de.hpi.restclient.dto.MatchingResponse;
import de.hpi.restclient.dto.ParsedOffer;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MatchingController {
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MatchingService service;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParsedOfferRepository parsedOfferRepository;


    @Autowired
    public MatchingController(MatchingService service, ParsedOfferRepository parsedOfferRepository){
        setService(service);
        setParsedOfferRepository(parsedOfferRepository);
    }

    @RequestMapping(value = "/matcher/match", method = RequestMethod.POST, produces = "application/json")
    public MatchingResponse startMatching(@RequestBody ParsedOffer parsedOffer){
        Offer offer = new Offer();
        Map<String, String> title = new HashMap<String, String>();
        Map<String, Number> price = new HashMap<String, Number>();
        Map<String, String> url = new HashMap<String, String>();

        offer.setShopId(parsedOffer.getShopId());
        offer.setEan(parsedOffer.getEan());
        offer.setHan(parsedOffer.getHan());
        offer.setSku(parsedOffer.getSku());
        offer.setCategoryString(parsedOffer.getCategoryString());


        title.put("0", parsedOffer.getOfferTitle());
        offer.setOfferTitle(title);
        url.put("0", parsedOffer.getUrl());
        offer.setUrl(url);
        price.put("0", parsedOffer.getPrice());
        offer.setPrice(price);
        System.out.println(getParsedOfferRepository().getFirstOffersOfShop(1362, 2, 0).get(0).getShopId());
        return getService().match(parsedOffer);
    }
}