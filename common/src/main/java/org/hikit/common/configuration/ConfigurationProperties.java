package org.hikit.common.configuration;

public class ConfigurationProperties {
    public static final String VERSION = "1.0";
    public static final String MAJOR_VERSION = VERSION.split("\\.")[0];
    public static final String API_PREFIX = "api/v" + MAJOR_VERSION;
}