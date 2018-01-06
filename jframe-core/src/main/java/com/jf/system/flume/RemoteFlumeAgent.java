package com.jf.system.flume;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2017-12-20
 * Time: 14:04
 */
public class RemoteFlumeAgent {

    private static final Logger logger = LoggerFactory.getLogger(RemoteFlumeAgent.class);

    private final String hostname;

    private final int port;

    public RemoteFlumeAgent(final String hostname, final int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public static RemoteFlumeAgent fromString(String input) {
        if (StringUtils.isNotEmpty(input)) {
            String[] parts = input.split(":");
            if (parts.length == 2) {
                String portString = parts[1];
                try {
                    int port = Integer.parseInt(portString);
                    return new RemoteFlumeAgent(parts[0], port);
                } catch (NumberFormatException nfe) {
                    logger.error("Not a valid int: " + portString);
                }
            } else {
                logger.error("Not a valid [host]:[port] configuration: " + input);
            }
        } else {
            logger.error("Empty flume agent entry, an extra comma?");
        }
        return null;
    }

}
