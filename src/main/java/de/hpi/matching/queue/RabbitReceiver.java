package de.hpi.matching.queue;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceiver {

    @Getter(AccessLevel.PRIVATE) private Logger logger = Logger.getRootLogger();

    public void receiveMessage(String message) {
        getLogger().info("Received <" + message + ">");
    }
}
