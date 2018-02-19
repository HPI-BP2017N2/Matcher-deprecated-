package de.hpi.matching.queue;

import de.hpi.matching.controller.MatchingController;
import de.hpi.restclient.dto.FinishedShop;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceiver {

    @Getter(AccessLevel.PRIVATE) private Logger logger = Logger.getRootLogger();
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MatchingController controller;

    @Autowired
    public RabbitReceiver(MatchingController controller) {
        setController(controller);
    }

    public void receiveMessage(FinishedShop message) {
        getLogger().info("Received <" + message + "> from queue");
        getController().startMatchingOffersForShop(message.getShopId());
    }
}