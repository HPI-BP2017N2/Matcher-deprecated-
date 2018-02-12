package de.hpi.matching.model;

import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.restclient.clients.BPBridgeClient;
import de.hpi.restclient.dto.ParsedOffer;
import de.hpi.restclient.pojo.Offer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class MatchingTest {

    @Test
    void match() {
        OfferMatchingRepository repository = spy(new OfferMatchingRepository(mock(BPBridgeClient.class)));

        Matching match = new Matching(repository);
        

        ParsedOffer offer = constructParsedOffer();
        //TODO Test different strategies


    }

    private ParsedOffer constructParsedOffer() {
        return constructParsedOfferParameter(
            "Apple",
            "Elektronik / Smartphones",
            "Dein Gesicht ist jetzt dein Passwort",
            "iPhone 7 32GB white",
            "123456",
            "777888999" );
    }

    private Offer constructIdealoOffer(){
        return constructOfferParameter(
            "Apple",
            "Elektronik / Smartphones",
            "Dein Gesicht ist jetzt dein Passwort",
            "iPhone 7 32GB white",
            "123456",
            "777888999" );
    }

    private ParsedOffer constructParsedOfferParameter(String brand, String categoryString, String description, String offerTitle, String sku, String han) {
        ParsedOffer testOffer = new ParsedOffer();
        testOffer.setBrand(brand);
        testOffer.setCategoryString(categoryString);
        testOffer.setDescription(description);
        testOffer.setSku(sku);
        testOffer.setOfferTitle(offerTitle);
        testOffer.setHan(han);
        return testOffer;
    }

    private Offer constructOfferParameter(String brand, String categoryString, String description, String offerTitle, String sku, String han){
        Offer testOffer = new Offer();

        Map<String, String> descriptionMap = new HashMap<>();
        descriptionMap.put("0", description);
        testOffer.setDescription(descriptionMap);

        Map<String, String> titleMap = new HashMap<>();
        descriptionMap.put("0", offerTitle);
        testOffer.setOfferTitle(titleMap);

        testOffer.setBrandSearchtext(brand);
        testOffer.setCategoryString(categoryString);
        testOffer.setDescription(descriptionMap);
        testOffer.setHan(han);
        testOffer.setSku(sku);
        return testOffer;
    }
}
