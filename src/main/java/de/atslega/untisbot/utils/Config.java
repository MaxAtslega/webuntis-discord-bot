package de.atslega.untisbot.utils;

import com.google.gson.annotations.Since;

public class Config {
    //Discord
    public String token;

    //Sentry
    public String sentryDSN;


    public Config() {}

    public Config(Config copy) {
        this.token = copy.token;
        this.sentryDSN = copy.sentryDSN;
    }

    public String getSentryDSN() {
        return sentryDSN;
    }

    public String getToken() {
        return token;
    }

}
