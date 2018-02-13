package de.hpi.matching.queue;

import de.hpi.matching.controller.MatchingController;
import de.hpi.restclient.dto.FinishedShop;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitReceiver {

    @Getter(AccessLevel.PRIVATE) private Logger logger = Logger.getRootLogger();
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MatchingController controller;

    @Autowired
    public RabbitReceiver(MatchingController controller) {
        setController(controller);
    }

    public void receiveMessage(String message) {
        getLogger().info("Received <" + message + "> from queue");
        FinishedShop shop = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            shop = mapper.readValue(message, FinishedShop.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(shop != null) getController().startMatchingOffersForShop(shop.getShopId());
    }
}
