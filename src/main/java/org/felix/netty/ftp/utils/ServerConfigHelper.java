package org.felix.netty.ftp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * server config context
 */
public class ServerConfigHelper {

    private static final Logger LOG = LoggerFactory.getLogger(ServerConfigHelper.class);

    private static final Properties CONFIG_CONTEXT = new Properties();

    private static final String CONFIG_FILE_PATH = "server.properties";

    private static final String DEFAULT_FTP_ROOT_PATH = "ftp_root";

    static {
        init();
    }

    private static void init() {
        try {
            CONFIG_CONTEXT.load(new FileInputStream(Tools.getResource(CONFIG_FILE_PATH).getFile()));
        } catch (IOException e) {
            LOG.warn("[{} not exists]-[Uses default config]", CONFIG_FILE_PATH);
        }
        CONFIG_CONTEXT.put(ServerConfigConstants.FTP_ROOT, DEFAULT_FTP_ROOT_PATH);
    }

    public static String getConfig(String configKey) {
        return (String) CONFIG_CONTEXT.get(configKey);
    }
}
