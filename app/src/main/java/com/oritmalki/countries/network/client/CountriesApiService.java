package com.oritmalki.countries.network.client;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.oritmalki.countries.CountriesApp;
import com.oritmalki.countries.network.INetworkApi;
import com.oritmalki.countries.network.INetworkResponseListener;
import com.oritmalki.countries.network.responses.RespCountry;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

import javax.inject.Inject;

import retrofit2.Callback;
import retrofit2.Response;

public class CountriesApiService {

    @Inject
    INetworkApi mINetworkApi;

    public CountriesApiService() {
        CountriesApp.getComponent().inject(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<List<RespCountry>> getAllCountries(String fields) {
        final CompletableFuture<List<RespCountry>> countriesFuture = mINetworkApi.getAllCountries(fields);
        return countriesFuture.handle(new BiFunction<List<RespCountry>, Throwable, List<RespCountry>>() {
            @Override
            public List<RespCountry> apply(List<RespCountry> result, Throwable throwable) {
                if (result != null) {
                    return result;
                }
                try {
                    throw new Exception(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        });
    }

//    public void getAllCountries(String fields, final INetworkResponseListener listener) {
//        retrofit2.Call<List<RespCountry>> respCountryCall = mINetworkApi.getAllCountries(fields);
//        respCountryCall.enqueue(new Callback<List<RespCountry>>() {
//            @Override
//            public void onResponse(retrofit2.Call<List<RespCountry>> call, Response<List<RespCountry>> response) {
//                    listener.onSuccess(response);
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<List<RespCountry>> call, Throwable t) {
//                    listener.onError(t.getMessage());
//            }
//        });
//    }

}
