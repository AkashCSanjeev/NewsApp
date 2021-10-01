package com.example.news.FragmentActivities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.loader.content.Loader;

import com.example.news.News;
import com.example.news.NewsLoader;
import com.example.news.R;

import java.util.List;


public class BusinessNewsFragment extends BaseNewsFragment {


    public BusinessNewsFragment() {

    }


    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String minItem = sharedPrefs.getString(
                getString(R.string.settings_min_item_key),
                getString(R.string.settings_min_item_default));
        String Language = sharedPrefs.getString(
                getString(R.string.settings_Language_key),
                getString(R.string.settings_Language_default));
        String country = sharedPrefs.getString(
                getString(R.string.settings_Country_key),
                getString(R.string.settings_Country_default));
        Uri baseUri = Uri.parse(NEWS_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();


        uriBuilder.appendQueryParameter("lang", Language);
        uriBuilder.appendQueryParameter("max", minItem);
        uriBuilder.appendQueryParameter("country", country);
        uriBuilder.appendQueryParameter("sortby", "publishedAt");
        uriBuilder.appendQueryParameter("topic", "business");

        return new NewsLoader(getActivity(), uriBuilder.toString());
    }

}