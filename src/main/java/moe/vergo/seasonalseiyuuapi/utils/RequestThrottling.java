package moe.vergo.seasonalseiyuuapi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestThrottling {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestThrottling.class);

    public static void throttleRequests() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            LOGGER.error("Thread interrupted", e);
        }
    }
}
