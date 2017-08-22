package com.wirecash.api.client;

import com.wirecash.api.models.Request.AuthenticateRequest;
import com.wirecash.api.models.Request.QuoteRequest;
import com.wirecash.api.models.Request.RefreshTokenRequest;
import com.wirecash.api.models.Responses.AuthenticateResponse;
import com.wirecash.api.models.Responses.CompaniesResponse;
import com.wirecash.api.models.Responses.CountryListResponse;
import com.wirecash.api.models.Responses.StateListResponse;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class WirecashApiServiceTest {

    private static WirecashClient getClient() {
        return WirecashClient.newBuilder().developmentBaseUrl().logLevel(HttpLoggingInterceptor.Level.BODY).build();
    }

    private static final String username = System.getenv("WIRECASH_API_USERNAME");
    private static final String password = System.getenv("WIRECASH_API_PASSWORD");

    private static Response<AuthenticateResponse> getAuthentication() throws IOException {
        System.getenv().entrySet().stream().forEach(entry -> System.out.println("Env: " + entry.getKey() + ", Val: " + entry.getValue()));
        Response<AuthenticateResponse> execute = getClient().service().authenticate(
                new AuthenticateRequest()
                        .withPassword(password)
                        .withUsername(username)
        ).execute();
        System.out.println("User: " + username + ", Pass: " + password);
        return execute;
    }

    @Test
    public void authenticate() throws Exception {
        Response<AuthenticateResponse> execute = getClient().service().authenticate(
                new AuthenticateRequest()
                        .withPassword(password)
                        .withUsername(username)
        ).execute();

        assertTrue(execute.isSuccessful());
        assertTrue(execute.code() == 200);

    }

    @Test
    public void refreshToken() throws Exception {
        Response<AuthenticateResponse> authentication = getAuthentication();
        assertTrue(authentication.isSuccessful());
        if (authentication.isSuccessful()) {
            AuthenticateResponse authenticateResponse = authentication.body();
            Response<AuthenticateResponse> response = getClient().service().refreshToken(
                    new RefreshTokenRequest()
                            .withAccess_token(authenticateResponse.getAccess_token())
                            .withRefresh_token(authenticateResponse.getRefresh_token())
                            .withUsername(username)
            ).execute();
            assertTrue(response.code() == 200);
            assertTrue(response.isSuccessful());
        }
    }

    @Test
    public void getCompany() throws Exception {
        Response<AuthenticateResponse> authentication = getAuthentication();
        assertTrue(authentication.isSuccessful());
        if (authentication.isSuccessful()) {
            String access_token = authentication.body().getAccess_token();
            Response<CompaniesResponse> response = getClient()
                    .service()
                    .getCompany(7L, access_token)
                    .execute();
            assertTrue(response.code() == 200);
            assertTrue(response.isSuccessful());

            Response<CompaniesResponse> response1 = getClient()
                    .service()
                    .getCompany(7L, 250.0, access_token)
                    .execute();
            assertTrue(response1.code() == 200);
            assertTrue(response1.isSuccessful());
        }
    }

    @Test
    public void getCompany1() throws Exception {
        Response<AuthenticateResponse> authentication = getAuthentication();
        assertTrue(authentication.isSuccessful());
        if (authentication.isSuccessful()) {
            String access_token = authentication.body().getAccess_token();

            Response<CompaniesResponse> response1 = getClient()
                    .service()
                    .getCompany(7L, 250.0, access_token)
                    .execute();
            assertTrue(response1.code() == 200);
            assertTrue(response1.isSuccessful());
        }
    }

    @Test
    public void getServicesByCountry() throws Exception {
        Response<AuthenticateResponse> authentication = getAuthentication();
        assertTrue(authentication.isSuccessful());
        if (authentication.isSuccessful()) {
            String access_token = authentication.body().getAccess_token();
            Response<CompaniesResponse> response = getClient()
                    .service()
                    .getServicesByCountry("MX", access_token)
                    .execute();

            assertTrue(response.code() == 200);
            assertTrue(response.isSuccessful());
        }
    }

    @Test
    public void getServicesByCountry1() throws Exception {
        Response<AuthenticateResponse> authentication = getAuthentication();
        assertTrue(authentication.isSuccessful());
        if (authentication.isSuccessful()) {
            String access_token = authentication.body().getAccess_token();
            Response<CompaniesResponse> response = getClient()
                    .service()
                    .getServicesByCountry("MX", 250.0, access_token)
                    .execute();

            assertTrue(response.code() == 200);
            assertTrue(response.isSuccessful());
        }
    }

    @Test
    public void getCountryList() throws Exception {
        Response<AuthenticateResponse> authentication = getAuthentication();
        assertTrue("Authenticated", authentication.isSuccessful());
        if (authentication.isSuccessful()) {
            String access_token = authentication.body().getAccess_token();
            Response<CountryListResponse> response = getClient()
                    .service()
                    .getCountryList(access_token)
                    .execute();
            assertTrue(response.code() == 200);
            assertTrue(response.isSuccessful());
        }
    }

    @Test
    public void getServicesByState() throws Exception {
        Response<AuthenticateResponse> authentication = getAuthentication();
        assertTrue(authentication.isSuccessful());
        if (authentication.isSuccessful()) {
            String access_token = authentication.body().getAccess_token();
            Response<CompaniesResponse> response = getClient()
                    .service()
                    .getServicesByState("CA", access_token)
                    .execute();
            assertTrue(response.code() == 200);
            assertTrue(response.isSuccessful());
        }
    }

    @Test
    public void getServicesByState1() throws Exception {
        Response<AuthenticateResponse> authentication = getAuthentication();
        assertTrue(authentication.isSuccessful());
        if (authentication.isSuccessful()) {
            String access_token = authentication.body().getAccess_token();
            Response<CompaniesResponse> response = getClient()
                    .service()
                    .getServicesByState("CA", 450.0, access_token)
                    .execute();
            assertTrue(response.code() == 200);
            assertTrue(response.isSuccessful());
        }
    }

    @Test
    public void getStateList() throws Exception {
        Response<AuthenticateResponse> authentication = getAuthentication();
        assertTrue(authentication.isSuccessful());
        if (authentication.isSuccessful()) {
            String access_token = authentication.body().getAccess_token();
            Response<StateListResponse> response = getClient()
                    .service()
                    .getStateList(access_token)
                    .execute();
            assertTrue(response.code() == 200);
            assertTrue(response.isSuccessful());
        }
    }

    @Test
    public void getQuote() throws Exception {
        Response<AuthenticateResponse> authentication = getAuthentication();
        assertTrue(authentication.isSuccessful());
        if (authentication.isSuccessful()) {
            String access_token = authentication.body().getAccess_token();
            Response<QuoteRequest> response = getClient()
                    .service()
                    .getQuote(new QuoteRequest()
                                    .withAmount(400.0)
                                    .withCurrencySymbol("BRL")
                                    .withServiceId(2451L),
                            access_token
                    )
                    .execute();
            System.out.println("getQuote: " + response.code() + ", " + response.isSuccessful());
            assertTrue(response.code() == 200);
            assertTrue(response.isSuccessful());
        }
    }

    @Test
    public void getServicesByStateCountry() throws Exception {
        Response<AuthenticateResponse> authentication = getAuthentication();
        assertTrue(authentication.isSuccessful());
        if (authentication.isSuccessful()) {
            String access_token = authentication.body().getAccess_token();
            Response<CompaniesResponse> response = getClient()
                    .service()
                    .getServicesByStateCountry("CA", "BR", access_token)
                    .execute();
            assertTrue(response.code() == 200);
            assertTrue(response.isSuccessful());
        }
    }

    @Test
    public void getServicesByStateCountry1() throws Exception {
        Response<AuthenticateResponse> authentication = getAuthentication();
        assertTrue(authentication.isSuccessful());
        if (authentication.isSuccessful()) {
            String access_token = authentication.body().getAccess_token();
            Response<CompaniesResponse> response = getClient()
                    .service()
                    .getServicesByStateCountry("CA", "BR", 400.0, access_token)
                    .execute();
            assertTrue(response.code() == 200);
            assertTrue(response.isSuccessful());
        }
    }
}