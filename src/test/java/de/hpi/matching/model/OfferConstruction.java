/*package de.hpi.matching.model;

import de.hpi.restclient.dto.ParsedOffer;
import de.hpi.restclient.pojo.Offer;

import java.util.HashMap;
import java.util.Map;

public class OfferConstruction {
    public OfferConstruction() {
    }

    public ParsedOffer constructParsedOffer() {
        return constructParsedOfferParameter(
                "Apple",
                "Elektronik / Smartphones",
                "Dein Gesicht ist jetzt dein Passwort",
                "iPhone 7 32GB white",
                "123456",
                "777888999",
                "654321"
        );
    }

    public Offer constructIdealoOffer() {
        return constructOfferParameter(
                "Apple",
                "Elektronik / Smartphones",
                "Dein Gesicht ist jetzt dein Passwort",
                "iPhone 7 32GB white",
                "123456",
                "777888999",
                "65432881"
        );
    }

    public ParsedOffer constructParsedOfferParameter(String brand, String categoryString, String description, String offerTitle, String sku, String han, String ean) {
        ParsedOffer testOffer = new ParsedOffer();
        testOffer.setBrand(brand);
        testOffer.setCategoryString(categoryString);
        testOffer.setDescription(description);
        testOffer.setSku(sku);
        testOffer.setOfferTitle(offerTitle);
        testOffer.setHan(han);
        testOffer.setEan(ean);
        return testOffer;
    }

    public Offer constructOfferParameter(String brand, String categoryString, String description, String offerTitle, String sku, String han, String ean) {
        Offer testOffer = new Offer();

        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("0", description);
        testOffer.setDescription(descriptionMap);

        Map<String, String> titleMap = new HashMap<>();
        titleMap.put("0", offerTitle);
        testOffer.setOfferTitle(titleMap);

        testOffer.setBrandSearchtext(brand);
        testOffer.setCategoryString(categoryString);
        testOffer.setDescription(descriptionMap);
        testOffer.setHan(han);
        testOffer.setSku(sku);
        testOffer.setEan(ean);
        return testOffer;
    }
}*/