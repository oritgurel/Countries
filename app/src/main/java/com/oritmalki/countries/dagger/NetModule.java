package com.oritmalki.countries.dagger;

import com.oritmalki.countries.network.INetworkApi;
import com.oritmalki.countries.network.client.CountriesApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private final String BASE_URL = "https://restcountries.eu/rest/v2/";

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .build();
    }


    @Provides
    @Singleton
    INetworkApi providesINetworkApi(Retrofit retrofit) {
        return retrofit.create(INetworkApi.class);
    }

    @Provides
    @Singleton
    CountriesApiService provideCountriesApiService() {
        return new CountriesApiService();
    }

}
