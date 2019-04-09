package com.oritmalki.countries.network;

import com.oritmalki.countries.network.responses.RespCountry;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface INetworkApi {

     String PATH_ALL = "all";
     String FIELDS_QUERY = "fields";


    @GET(PATH_ALL)
    CompletableFuture<List<RespCountry>> getAllCountries(@Query(FIELDS_QUERY) String fields);

    @GET(PATH_ALL)
    CompletableFuture<List<RespCountry>> testGetAllCountries(@Query(FIELDS_QUERY) String fields);

}
