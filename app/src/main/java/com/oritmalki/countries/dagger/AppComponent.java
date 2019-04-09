package com.oritmalki.countries.dagger;

import com.oritmalki.countries.data.CountriesRepo;
import com.oritmalki.countries.network.client.CountriesApiService;
import com.oritmalki.countries.ui.MainActivity;
import com.oritmalki.countries.viewmodel.CountriesViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetModule.class, DataModule.class, ViewModelModule.class})
public interface AppComponent {

    void inject(CountriesApiService countriesApiService);

    void inject(CountriesRepo countriesRepo);

    void inject(CountriesViewModel countriesViewModel);

    void inject(MainActivity mainActivity);


    //tests



}
