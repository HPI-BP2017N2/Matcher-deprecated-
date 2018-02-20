package de.hpi.matching.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hpi.matching.controller.MatchingController;
import de.hpi.restclient.dto.FinishedShop;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
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

    public void receiveMessage(byte[] message) {
        FinishedShop shop = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            shop = mapper.readValue(new String(message), FinishedShop.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (shop != null) {
            getLogger().info("Received <" + shop.getShopId() + "> from queue");
            getController().startMatchingOffersForShop(shop.getShopId());
        }
    }
}