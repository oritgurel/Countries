package com.oritmalki.countries.adapters;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.oritmalki.countries.R;
import com.oritmalki.countries.network.responses.RespCountry;
import com.oritmalki.countries.ui.MainActivity;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryViewHolder> {

    private List<RespCountry> mCountries;
    private ICountryClicked mListener;

    private final int TYPE_SVG = 1;
    private final int TYPE_PNG = 2;

    public CountriesAdapter(List<RespCountry> countries, ICountryClicked listener) {
        mCountries = countries;
        mListener = listener;
    }

    public void setCountries(List<RespCountry> countries) {
        mCountries = countries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_country, viewGroup, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder countryViewHolder, int i) {
        RespCountry country = mCountries.get(i);
        countryViewHolder.mCapitalTv.setText(country.getCapital());
        countryViewHolder.mCountryNameTv.setText(country.getName());

        loadFlagImage(country.getFlag(), countryViewHolder);

    }

    private void loadFlagImage(String flagUrl, CountryViewHolder holder) {
        Uri uri = Uri.parse(flagUrl);
        int type = flagUrl.endsWith("svg") ? TYPE_SVG : TYPE_PNG;

        if (type == TYPE_SVG) {
            RequestBuilder<PictureDrawable> requestBuilder = GlideToVectorYou
                    .init()
                    .with((MainActivity)holder.mFlagIv.getContext())
//                   todo .setPlaceHolder()
                    .getRequestBuilder();

            requestBuilder
                    .load(uri)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(new RequestOptions()
                            .centerCrop().override(64, 42))
                    .into(holder.mFlagIv);
        } else {
            Glide.with(holder.mFlagIv.getContext()).load(flagUrl).into(holder.mFlagIv);
        }

    }

    @Override
    public int getItemCount() {
        return mCountries.size();
    }


    public class CountryViewHolder extends RecyclerView.ViewHolder {

        private ImageView mFlagIv;
        private TextView mCountryNameTv;
        private TextView mCapitalTv;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            mFlagIv = itemView.findViewById(R.id.li_flag_iv);
            mCountryNameTv = itemView.findViewById(R.id.li_country_name);
            mCapitalTv = itemView.findViewById(R.id.li_capital_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onCountryClicked(mCountries.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface ICountryClicked {
        void onCountryClicked(RespCountry country);
    }
}
