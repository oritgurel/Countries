package com.oritmalki.countries.dagger;

import com.oritmalki.countries.AppExecutors;
import com.oritmalki.countries.data.CountriesRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    protected AppExecutors provideExecutors() {
        return new AppExecutors();
    }

    @Provides
    @Singleton
    protected CountriesRepo provideCountriesRepo(AppExecutors appExecutors) {
        return new CountriesRepo(appExecutors);
    }
}
