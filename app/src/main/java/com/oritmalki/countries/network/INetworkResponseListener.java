package com.oritmalki.countries.network;

import com.oritmalki.countries.network.responses.RespCountry;

import retrofit2.Response;

public interface INetworkResponseListener<T> {

    void onSuccess(Response<T> response);
    void onError(String message);
}
