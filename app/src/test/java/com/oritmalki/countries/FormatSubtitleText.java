package com.oritmalki.countries;

import com.oritmalki.countries.network.responses.RespCountry;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class FormatSubtitleText {

    @Mock
    RespCountry mRespCountry;

    @Before
    public void setCountry() {
        mRespCountry = new RespCountry();
        mRespCountry.setRegion("region");
        mRespCountry.setSubregion("subregion");
        mRespCountry.setCapital("capital");
        mRespCountry.setNativeName("native name");
    }

    @Test
    public void formatSubtitle() {
        String subtitle = Utils.formatSubtitle(mRespCountry).trim();
        assert (subtitle).equals("region, subregion, capital (native name)");

        mRespCountry.setSubregion("");
        mRespCountry.setCapital("");
        subtitle = Utils.formatSubtitle(mRespCountry).trim();
        assert (subtitle).equals("region (native name)");

        mRespCountry.setSubregion("");
        mRespCountry.setNativeName("");
        subtitle = Utils.formatSubtitle(mRespCountry).trim();
        assert (subtitle).equals("region");
    }
}
