package com.oritmalki.countries.ui;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.Guideline;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.oritmalki.countries.CountriesApp;
import com.oritmalki.countries.R;
import com.oritmalki.countries.Utils;
import com.oritmalki.countries.adapters.CountriesAdapter;
import com.oritmalki.countries.data.CountriesRepo;
import com.oritmalki.countries.maps.MapFragment;
import com.oritmalki.countries.network.model.Language;
import com.oritmalki.countries.network.model.Translations;
import com.oritmalki.countries.network.responses.RespCountry;
import com.oritmalki.countries.viewmodel.CountriesViewModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements CountriesAdapter.ICountryClicked, SearchView.OnQueryTextListener, View.OnClickListener {

    private MapFragment mMapFragment;

    private RecyclerView mRecyclerView;
    private TextView mCountryTv, mRegionSubtitleTv, mLanguagesTv, mTranslationsTv;
    private CountriesAdapter mAdapter;
    private android.support.v7.widget.Toolbar mToolbar;

    private SearchView mSearchView;
    private MenuItem mSearchItem;

    private List<RespCountry> mCountries;

    private RespCountry mSelectedCountry;


    @Inject
    CountriesRepo mCountriesRepo;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    CountriesViewModel mCountriesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CountriesApp.getComponent().inject(this);
        mCountries = new ArrayList<>();
        initViews();
        initActionBar();
        setupRecyclerview();

        initObserver();

        addMapFragment();
    }

    protected void hideSoftKeyboard(SearchView input) {
        if (input == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }



    private void initObserver() {
        mCountriesViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CountriesViewModel.class);
        //observe
        mCountriesViewModel.getCountries().observe(this, new Observer<List<RespCountry>>() {
            @Override
            public void onChanged(@Nullable List<RespCountry> respCountries) {
                if (respCountries == null || respCountries.isEmpty()) {
                    return;
                }

                mCountries.addAll(respCountries);
                setupAdapter(mCountries);
            }
        });
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.act_countries_recyclerview);
        mCountryTv = findViewById(R.id.act_country_tv);
        mRegionSubtitleTv = findViewById(R.id.act_region_details_subtitle_tv);
        mToolbar = findViewById(R.id.act_main_toolbar);
        mLanguagesTv = findViewById(R.id.act_languages_tv);
        mTranslationsTv = findViewById(R.id.act_Translations_tv);

        mLanguagesTv.setOnClickListener(this);
        mTranslationsTv.setOnClickListener(this);
        mLanguagesTv.setVisibility(View.INVISIBLE);
        mTranslationsTv.setVisibility(View.INVISIBLE);
    }

    private void initActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupRecyclerview() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setSmoothScrollbarEnabled(true);
    }

    private void setupAdapter(List<RespCountry> countryList) {
        if (mRecyclerView.getAdapter() == null) {
            mAdapter = new CountriesAdapter(countryList, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCountries(countryList);
        }

    }

    private void addMapFragment() {
        if (mMapFragment == null) {
            mMapFragment = new MapFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.map_container, mMapFragment).commitAllowingStateLoss();
    }


    @Override
    public void onCountryClicked(RespCountry country) {
        mSelectedCountry = country;
        clearSearchView();
        updateCountryInfo(country);
        updateMap(country);
    }

    private void updateCountryInfo(RespCountry country) {
        showInfoViews(true);
        mCountryTv.setText(country.getName());
        String subtitle = Utils.formatSubtitle(country);
        mRegionSubtitleTv.setText(subtitle);
    }

    private void clearSearchView() {
        hideSoftKeyboard(mSearchView);
        if (mSearchView != null && mSearchItem != null) {
            mSearchView.clearFocus();
            mSearchItem.collapseActionView();
        }
    }



    private void updateMap(RespCountry country) {
        //extract latlng
        if (country.getLatlng() == null || country.getLatlng().isEmpty()) {
            return;
        }
        double lat = country.getLatlng().get(0);
        double lon = country.getLatlng().get(1);

        mMapFragment.updateMapParams(lat, lon, country.getCapital());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);

        mSearchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        mSearchView = null;
        if (mSearchItem != null) {
            mSearchView = (SearchView) mSearchItem.getActionView();
        }
        if (mSearchView != null) {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }

        mSearchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                showInfoViews(false);
                return true;
            }
        });

        mSearchView.setQueryHint("Search countries");
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setIconified(false);

        return super.onCreateOptionsMenu(menu);
    }

    public void showPopupMenu(List<String> items, View view) {
        Context wrapper = new ContextThemeWrapper(this, R.style.PopupMenu);
        PopupMenu popupMenu = new PopupMenu(wrapper, view);
        forceShowIcon(popupMenu);
        for (String item : items) {
            popupMenu.getMenu().add(item);
        }
        popupMenu.show();
    }

    private void forceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        for (RespCountry country : mCountries) {
            if (country.getName().toLowerCase().startsWith(s)) {
                mRecyclerView.scrollToPosition(mCountries.indexOf(country));
                break;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_languages_tv:
                displayLanguagesPopup();
                break;
            case R.id.act_Translations_tv:
                displayTranslationsPopup();
                break;
        }
    }

    private void displayLanguagesPopup() {
        if (mSelectedCountry == null) {
            return;
        }

        List<Language> languages = mSelectedCountry.getLanguages();
        if (languages == null || languages.isEmpty()) {
            return;
        }

        List<String> items = new ArrayList<>();
        for (Language language : languages) {
            items.add(language.getName());
        }

        showPopupMenu(items, mLanguagesTv);
    }

    private void displayTranslationsPopup() {
        if (mSelectedCountry == null) {
            return;
        }

        Translations translations = mSelectedCountry.getTranslations();
        if (translations == null) {
            return;
        }

        List<String> items = new ArrayList<>();
        items.add(translations.getBr());
        items.add(translations.getDe());
        items.add(translations.getEs());
        items.add(translations.getFr());
        items.add(translations.getIt());
        items.add(translations.getJa());
        items.add(translations.getPt());

        showPopupMenu(items, mTranslationsTv);

    }

    private void showInfoViews(boolean show) {
        mCountryTv.setVisibility(show ? View.VISIBLE : View.GONE);
        mRegionSubtitleTv.setVisibility(show ? View.VISIBLE : View.GONE);
        mLanguagesTv.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        mTranslationsTv.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

}
