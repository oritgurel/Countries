package com.oritmalki.countries.testdagger;

import com.oritmalki.countries.CountriesApiServiceTest;
import com.oritmalki.countries.CountriesApp;
import com.oritmalki.countries.dagger.AppComponent;
import com.oritmalki.countries.dagger.NetModule;
import com.oritmalki.countries.network.client.CountriesApiService;
import com.oritmalki.countries.viewmodel.CountriesViewModel;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {TestNetModule.class, TestDataModule.class})
public interface TestComponent extends AppComponent {
    CountriesApiService countriesApiService();
//    CountriesViewModel countriesViewModel();

    void inject(CountriesApiServiceTest countriesApiServiceTest);
}
