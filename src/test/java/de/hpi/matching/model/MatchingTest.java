package de.hpi.matching.model;

import de.hpi.matching.repo.OfferMatchingRepository;
import de.hpi.restclient.dto.ParsedOffer;
import de.hpi.restclient.pojo.MatchingResponse;
import de.hpi.restclient.pojo.Offer;
import de.hpi.restclient.pojo.SuccessfulMatchingResponse;
import de.hpi.restclient.pojo.UnsuccessfulMatchingResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

class MatchingTest {


    private final OfferConstruction offerConstruction = new OfferConstruction();

    @Test
     void matchFirstParameterEqual () {

        OfferMatchingRepository repository = mock(OfferMatchingRepository.class);

        Matching matcher = new Matching(repository);

        List<Offer> testOffers= new ArrayList<>();
        testOffers.add(offerConstruction.constructIdealoOffer());

        List<Offer> emptyList = new ArrayList<>();

        when(repository.searchEan(anyLong(), eq(constructParsedOffer().getEan()))).thenReturn(testOffers);

        when(repository.searchBrand(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchHan(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchOfferTitle(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchSku(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchCategory(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchDescription(anyLong(), anyString())).thenReturn(emptyList);


        MatchingResponse response = matcher.match(offerConstruction.constructParsedOffer());

        assertTrue(response instanceof SuccessfulMatchingResponse);
    }

    @Test
    void matchLastParameterEqual () {

        OfferMatchingRepository repository = mock(OfferMatchingRepository.class);

        Matching matcher = new Matching(repository);

        List<Offer> testOffers= new ArrayList<>();
        testOffers.add(offerConstruction.constructIdealoOffer());
        List<Offer> emptyList = new ArrayList<>();

        when(repository.searchOfferTitle(anyLong(), eq(constructParsedOffer().getOfferTitle()))).thenReturn(testOffers);

        when(repository.searchEan(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchBrand(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchHan(anyLong(), anyString())).thenReturn(emptyList);

        when(repository.searchSku(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchCategory(anyLong(), anyString())).thenReturn(emptyList);
        when(repository.searchDescription(anyLong(), anyString())).thenReturn(emptyList);


        MatchingResponse response = matcher.match(offerConstruction.constructParsedOffer());

        assertTrue(response instanceof SuccessfulMatchingResponse);
    }

    @Test
    void matchNoParameterEqual () {

        OfferMatchingRepository repository = mock(OfferMatchingRepository.class);

        Matching matcher = new Matching(repository);

        List<Offer> testOffers= new ArrayList<>();
        testOffers.add(offerConstruction.constructIdealoOffer());
        List<Offer> emptyList = new ArrayList<>();

        when(repository.searchOfferTitle(anyLong(), eq(constructParsedOffer().getOfferTitle()))).thenReturn(emptyList);

        when(repository.searchEan(anyLong(), eq(constructParsedOffer().getEan()))).thenReturn(emptyList);
        when(repository.searchBrand(anyLong(), eq(constructParsedOffer().getBrand()))).thenReturn(emptyList);
        when(repository.searchHan(anyLong(), eq(constructParsedOffer().getHan()))).thenReturn(emptyList);

        when(repository.searchSku(anyLong(), eq(constructParsedOffer().getSku()))).thenReturn(emptyList);
        when(repository.searchCategory(anyLong(), eq(constructParsedOffer().getCategoryString()))).thenReturn(emptyList);
        when(repository.searchDescription(anyLong(), eq(constructParsedOffer().getDescription()))).thenReturn(emptyList);

        MatchingResponse response = matcher.match(offerConstruction.constructParsedOffer());

        assertTrue(response instanceof UnsuccessfulMatchingResponse);
    }


    private ParsedOffer constructParsedOffer() {
        return offerConstruction.constructParsedOffer();
    }

    private Offer constructIdealoOffer(){
        return offerConstruction.constructIdealoOffer();
    }

    private ParsedOffer constructParsedOfferParameter(String brand, String categoryString, String description, String offerTitle, String sku, String han, String ean) {
        return offerConstruction.constructParsedOfferParameter(brand, categoryString, description, offerTitle, sku, han, ean);
    }

    private Offer constructOfferParameter(String brand, String categoryString, String description, String offerTitle, String sku, String han, String ean){

        return offerConstruction.constructOfferParameter(brand, categoryString, description, offerTitle, sku, han, ean);
    }
}
