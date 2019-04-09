package com.oritmalki.countries;

import com.oritmalki.countries.adapters.CountriesAdapter;
import com.oritmalki.countries.data.CountriesRepo;
import com.oritmalki.countries.network.client.CountriesApiService;
import com.oritmalki.countries.network.responses.RespCountry;
import com.oritmalki.countries.testdagger.DaggerTestComponent;
import com.oritmalki.countries.testdagger.TestComponent;
import com.oritmalki.countries.testdagger.TestDataModule;
import com.oritmalki.countries.testdagger.TestNetModule;
import com.oritmalki.countries.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CountriesApiServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

//    @Rule
//    public DaggerMockRule<TestComponent> netRule =
//            new DaggerMockRule<>(TestComponent.class, new TestNetModule())
//            .set(new DaggerMockRule.ComponentSetter<TestComponent>() {
//                @Override
//                public void setComponent(TestComponent component) {
//                    countriesApiService = component.countriesApiService();
//                }
//            });

//    @Rule
//    public DaggerMockRule<TestComponent> viewmodelRule =
//            new DaggerMockRule<>(TestComponent.class, TestViewModelModule.class)
//            .set(new DaggerMockRule.ComponentSetter<TestComponent>() {
//                @Override
//                public void setComponent(TestComponent component) {
//                    viewmodel = component.countriesViewModel();
//                }
//            });
//    @Inject
//    CountriesViewModel viewmodel;
//    @Inject
//    CountriesRepo mCountriesRepo;

    @Inject
    CountriesApiService countriesApiService;


    @Mock
    CompletableFuture<List<RespCountry>> countries;


    @Mock
    CountriesRepo countriesRepo;

    @Before
    public void setup() {
        TestComponent testComponent = DaggerTestComponent.builder()
                .testNetModule(new TestNetModule())
                .testDataModule(new TestDataModule())
                .build();
        testComponent.inject(this);
    }


    @Test
    public void getCountries() throws ExecutionException, InterruptedException {
        when(countriesApiService.getAllCountries("name;")).thenReturn(countries);

        countriesApiService.getAllCountries("name;");

        verify(countriesApiService).getAllCountries("name;").equals(countries.get());

    }
}
