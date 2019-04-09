package com.oritmalki.countries.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.oritmalki.countries.CountriesApp;
import com.oritmalki.countries.data.CountriesRepo;
import com.oritmalki.countries.network.responses.RespCountry;

import java.util.List;

import javax.inject.Inject;

public class CountriesViewModel extends ViewModel {

    private MediatorLiveData<List<RespCountry>> mObservableCountries;

    @Inject
    CountriesRepo mCountriesRepo;

    @Inject
    public CountriesViewModel(CountriesRepo countriesRepo) {
        CountriesApp.getComponent().inject(this);
        mCountriesRepo = countriesRepo;
        mObservableCountries = new MediatorLiveData<>();
        mObservableCountries.postValue(null);
        mObservableCountries.addSource(mCountriesRepo.getCountries(), new Observer<List<RespCountry>>() {
            @Override
            public void onChanged(@Nullable List<RespCountry> countries) {
                mObservableCountries.postValue(countries);
            }
        });
    }

    public LiveData<List<RespCountry>> getCountries() {
        return mObservableCountries;
    }
}
