package com.wirecash.api.client;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wirecash.api.models.Request.AuthenticateRequest;
import com.wirecash.api.models.Responses.AuthenticateResponse;
import com.wirecash.api.models.Responses.CountryListResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WirecashClient {

    private final Retrofit retrofit;
    private final WirecashApiService wirecashApiService;

    public WirecashClient(Retrofit retrofit, WirecashApiService wirecashApiService) {
        this.retrofit = retrofit;
        this.wirecashApiService = wirecashApiService;
    }

    public WirecashApiService service() {
        return wirecashApiService;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        public static final String DEFAULT_PRODUCTION_BASE_URL = "https://api.wirecash.com/production/";
        public static final String DEFAULT_DEVELOPMENT_BASE_URL = "https://api.wirecash.com/sandbox/";
        public static long DEFAULT_READ_TIMEOUT_SECONDS = 300;
        public static long DEFAULT_CONNECT_TIMEOUT_SECONDS = 60;

        private final OkHttpClient.Builder okHttpClientBuilder;
        private String baseUrl;
        private HttpLoggingInterceptor.Level httpLogLevel;
        private long readTimeoutSeconds;
        private long connectTimeoutSeconds;

        private Builder() {
            this.okHttpClientBuilder = new OkHttpClient.Builder();
            this.readTimeoutSeconds = DEFAULT_READ_TIMEOUT_SECONDS;
            this.connectTimeoutSeconds = DEFAULT_CONNECT_TIMEOUT_SECONDS;
        }

        public WirecashClient build() {
            if (baseUrl == null) {
                throw new IllegalArgumentException("must set baseUrl. You probably want to call productionBaseUrl(), or developmentBaseUrl().");
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .validateEagerly(true)
                    .addConverterFactory(GsonConverterFactory.create(buildGson()))
                    .client(buildOkHttpClient())
                    .build();

            return new WirecashClient(retrofit, retrofit.create(WirecashApiService.class));
        }

        private Gson buildGson() {
            return new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
        }

        private OkHttpClient buildOkHttpClient() {
            okHttpClientBuilder
                    .readTimeout(readTimeoutSeconds, TimeUnit.SECONDS)
                    .connectTimeout(connectTimeoutSeconds, TimeUnit.SECONDS);

            if (httpLogLevel != null) {
                okHttpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(httpLogLevel));
            }

            return okHttpClientBuilder.build();
        }

        public OkHttpClient.Builder okHttpClientBuilder() {
            return okHttpClientBuilder;
        }

        public Builder logLevel(HttpLoggingInterceptor.Level level) {
            this.httpLogLevel = level;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder productionBaseUrl() {
            return baseUrl(DEFAULT_PRODUCTION_BASE_URL);
        }

        public Builder developmentBaseUrl() {
            return baseUrl(DEFAULT_DEVELOPMENT_BASE_URL);
        }
    }
}
