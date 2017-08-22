Wirecash api-client
==========

Java Bindings for the Wirecash API.

The whole available Wirecash API is defined in the `WirecashApiService` interface.

Uses [Retrofit](https://github.com/square/retrofit) and [OkHttp](https://github.com/square/okhttp) under
the hood. You may want to take a look at those libraries if you need to do anything out of the ordinary.

### Basic Usage

```xml
<dependency>
    <groupId>com.wirecash</groupId>
    <artifactId>api-client</artifactId>
    <version>1.1.0</version>
</dependency>
```

```java

// Use builder to create a client 
WirecashClient wirecashClient = WirecashClient.newBuilder()  
  .developmentBaseUrl() // or equivalent, depending on which environment you're calling into
  .build();

//our API requires that you authenticate using your given username and password
Response<AuthenticateResponse> auth = client.service()
    .authenticate(
            new AuthenticateRequest()
                    .withUsername("YOUR USERNAME")
                    .withPassword("YOUR PASSWORD")
    ).execute();

if(auth.isSuccessful()){
    //all calls to our api with the exception to /authenticate and /token require an access_token
    Call<CountryListResponse> countryListResponse = client.service().getCountryList(auth.body().getAccess_token());
    //do something with the country list
}
```