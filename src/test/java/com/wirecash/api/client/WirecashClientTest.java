package com.wirecash.api.client;

import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Test;

public class WirecashClientTest {

    private WirecashClient.Builder getBuild() {
        return WirecashClient.newBuilder().logLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoBaseURL() throws Exception {
        getBuild().build();
    }

    @Test
    public void testWithBaseURL() {
        getBuild().developmentBaseUrl().build();
    }
}