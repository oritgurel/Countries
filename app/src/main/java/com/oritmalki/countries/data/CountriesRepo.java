package com.oritmalki.countries.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.oritmalki.countries.AppExecutors;
import com.oritmalki.countries.CountriesApp;
import com.oritmalki.countries.network.INetworkResponseListener;
import com.oritmalki.countries.network.client.CountriesApiService;
import com.oritmalki.countries.network.responses.RespCountry;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import retrofit2.Response;

public class CountriesRepo {

    private List<RespCountry> mCountries;
    private AppExecutors mAppExecutors;
    private final String FIELDS_QUERY_PARAMS = "name;capital;region;subregion;latlng;languages;translations;flag;nativeName";

    private MediatorLiveData mObservableCountries;


    @Inject
    CountriesApiService mApiService;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public CountriesRepo(AppExecutors appExecutors) {
        CountriesApp.getComponent().inject(this);
        mAppExecutors = appExecutors;
        mObservableCountries = new MediatorLiveData<>();
        mObservableCountries.postValue(null);

        try {
            mCountries = getCountriesFromNetwork(FIELDS_QUERY_PARAMS).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mCountries != null) {
            mObservableCountries.postValue(mCountries);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<List<RespCountry>> getCountriesFromNetwork(final String fields) {
        return mApiService.getAllCountries(fields);
    }

    public LiveData<List<RespCountry>> getCountries() {
        return mObservableCountries;
    }

//    INetworkResponseListener mListener = new INetworkResponseListener() {
//        @Override
//        public void onSuccess(Response response) {
//            mCountries = ((List<RespCountry>)((Response<RespCountry>) response).body());
//
//            //todo observe countries
//            mObservableCountries.postValue(mCountries);
//        }
//
//        @Override
//        public void onError(String message) {
//            Log.e("getCountries - Error", message);
//        }
//    };
}
