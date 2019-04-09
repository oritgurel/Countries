package com.oritmalki.countries.testdagger;

import com.oritmalki.countries.AppExecutors;
import com.oritmalki.countries.dagger.DataModule;
import com.oritmalki.countries.data.CountriesRepo;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestDataModule extends DataModule {

//    @Override
//    protected AppExecutors provideExecutors() {
//        return Mockito.mock(AppExecutors.class);
//    }
//
//    @Override
//    protected CountriesRepo provideCountriesRepo(AppExecutors appExecutors) {
//        return Mockito.mock(CountriesRepo.class);
//    }
}
