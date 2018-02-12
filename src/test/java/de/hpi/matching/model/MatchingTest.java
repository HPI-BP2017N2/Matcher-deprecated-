package de.hpi.matching.model;

import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.restclient.clients.BPBridgeClient;
import de.hpi.restclient.dto.ParsedOffer;
import de.hpi.restclient.pojo.MatchingResponse;
import de.hpi.restclient.pojo.Offer;
import de.hpi.restclient.pojo.SuccessfulMatchingResponse;
import de.hpi.restclient.pojo.UnsuccessfulMatchingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

class MatchingTest {

    

    @Test
    public void matchFirstParameterEqual () {

        OfferMatchingRepository repository = mock(OfferMatchingRepository.class);

        Matching matcher = new Matching(repository);

        List<Offer> testOffers= new ArrayList<Offer>();
        testOffers.add(constructIdealoOffer());

        List<Offer> emptyList = new ArrayList<Offer>();

        when(repository.searchEan(anyLong(), anyString())).thenReturn(testOffers);

        when(repository.searchBrand(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchHan(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchOfferTitle(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchSku(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchCategory(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchDescription(anyLong(), anyString())).thenReturn(emptyList);


        MatchingResponse response = matcher.match(constructParsedOffer());

        assertTrue(response instanceof SuccessfulMatchingResponse);
    }

    @Test
    public void matchLastParameterEqual () {

        OfferMatchingRepository repository = mock(OfferMatchingRepository.class);

        Matching matcher = new Matching(repository);

        List<Offer> testOffers= new ArrayList<Offer>();
        testOffers.add(constructIdealoOffer());
        List<Offer> emptyList = new ArrayList<Offer>();

        when(repository.searchOfferTitle(anyLong(), anyString())).thenReturn(testOffers);

        when(repository.searchEan(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchBrand(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchHan(anyLong(), anyString())).thenReturn(emptyList);

        when(repository.searchSku(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchCategory(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchDescription(anyLong(), anyString())).thenReturn(emptyList);


        MatchingResponse response = matcher.match(constructParsedOffer());

        assertTrue(response instanceof SuccessfulMatchingResponse);
    }

    @Test
    public void matchNoParameterEqual () {

        OfferMatchingRepository repository = mock(OfferMatchingRepository.class);

        Matching matcher = new Matching(repository);

        List<Offer> testOffers= new ArrayList<Offer>();
        testOffers.add(constructIdealoOffer());
        List<Offer> emptyList = new ArrayList<Offer>();

        when(repository.searchOfferTitle(anyLong(), anyString())).thenReturn(emptyList);

        when(repository.searchEan(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchBrand(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchHan(anyLong(), anyString())).thenReturn(emptyList);

        when(repository.searchSku(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchCategory(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchDescription(anyLong(), anyString())).thenReturn(emptyList);

        MatchingResponse response = matcher.match(constructParsedOffer());

        assertTrue(response instanceof UnsuccessfulMatchingResponse);
    }



    private ParsedOffer constructParsedOffer() {
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

    private Offer constructIdealoOffer(){
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

    private ParsedOffer constructParsedOfferParameter(String brand, String categoryString, String description, String offerTitle, String sku, String han, String ean) {
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

    private Offer constructOfferParameter(String brand, String categoryString, String description, String offerTitle, String sku, String han, String ean){
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
}
