package com.wirecash.api.client;

import com.wirecash.api.models.Request.AuthenticateRequest;
import com.wirecash.api.models.Request.QuoteRequest;
import com.wirecash.api.models.Request.RefreshTokenRequest;
import com.wirecash.api.models.Responses.AuthenticateResponse;
import com.wirecash.api.models.Responses.CompaniesResponse;
import com.wirecash.api.models.Responses.CountryListResponse;
import com.wirecash.api.models.Responses.StateListResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface WirecashApiService {
    @POST("authenticate")
    Call<AuthenticateResponse> authenticate(@Body AuthenticateRequest request);

    @POST("token")
    Call<AuthenticateResponse> refreshToken(@Body RefreshTokenRequest request);

    @GET("company/{id}")
    Call<CompaniesResponse> getCompany(@Path("id") Long id, @Header("Authorization") String access_token);

    @GET("company/{id}")
    Call<CompaniesResponse> getCompany(@Path("id") Long id, @Query("amount") Double amount, @Header("Authorization") String access_token);

    @GET("country/{country_code}")
    Call<CompaniesResponse> getServicesByCountry(@Path("country_code") String country_code, @Header("Authorization") String access_token);

    @GET("country/{country_code}")
    Call<CompaniesResponse> getServicesByCountry(@Path("country_code") String country_code, @Query("amount") Double amount, @Header("Authorization") String access_token);

    @GET("country/list")
    Call<CountryListResponse> getCountryList(@Header("Authorization") String access_token);

    @GET("state/{state_code}")
    Call<CompaniesResponse> getServicesByState(@Path("state_code") String state_code, @Header("Authorization") String access_token);

    @GET("state/{state_code}")
    Call<CompaniesResponse> getServicesByState(@Path("state_code") String state_code, @Query("amount") Double amount, @Header("Authorization") String access_token);

    @GET("state/{state_code}/country/{country_code}")
    Call<CompaniesResponse> getServicesByStateCountry(@Path("state_code") String state_code, @Path("country_code") String country_code, @Header("Authorization") String access_token);

    @GET("state/{state_code}/country/{country_code}")
    Call<CompaniesResponse> getServicesByStateCountry(@Path("state_code") String state_code, @Path("country_code") String country_code, @Query("amount") Double amount, @Header("Authorization") String access_token);

    @GET("state/list")
    Call<StateListResponse> getStateList(@Header("Authorization") String access_token);

    @POST("quote")
    Call<QuoteRequest> getQuote(@Body QuoteRequest quoteRequest, @Header("Authorization") String access_token);
}
