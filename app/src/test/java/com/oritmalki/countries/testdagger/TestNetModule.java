package com.oritmalki.countries.testdagger;

import com.oritmalki.countries.dagger.NetModule;
import com.oritmalki.countries.network.INetworkApi;
import com.oritmalki.countries.network.client.CountriesApiService;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class TestNetModule extends NetModule {

//    @Override
//    protected Retrofit provideRetrofit() {
//        return Mockito.mock(Retrofit.class);
//    }
//
//    @Override
//    protected INetworkApi providesINetworkApi(Retrofit retrofit) {
//        return Mockito.mock(INetworkApi.class);
//    }
//
//    @Override
//    protected CountriesApiService provideCountriesApiService() {
//        return Mockito.mock(CountriesApiService.class);
//    }
}
