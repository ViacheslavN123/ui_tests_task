package configuration;

import org.aeonbits.owner.Config;

public interface WebConfig extends Config {
    @Key("browserVersion")
    @DefaultValue("133.0.6943.126")
    String getBrowserVersion();

    @Key("browserSize")
    @DefaultValue("2560x1440")
    String getBrowserSize();
}