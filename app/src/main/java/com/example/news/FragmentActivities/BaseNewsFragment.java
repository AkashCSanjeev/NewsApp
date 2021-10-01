package com.example.news.FragmentActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.news.Adapters.CustomAdapter;
import com.example.news.News;
import com.example.news.NewsLoader;
import com.example.news.R;

import java.util.ArrayList;
import java.util.List;


public class BaseNewsFragment extends Fragment implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<News>> {


    public final String NEWS_URL = "https://gnews.io/api/v4/top-headlines?token=80dcb639d85b8525240cb09318e6cd21";
    private CustomAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View mloadingIndicator;
    private SwipeRefreshLayout mSwipeRefresh;
    private int LOADER_ID = 1;

    /**
     * Parent for all the fragments
     */
    public BaseNewsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list, container, false);
        ListView newsList = (ListView) rootView.findViewById(R.id.listView);
        mAdapter = new CustomAdapter(getActivity(), new ArrayList<News>());
        newsList.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
        newsList.setEmptyView(mEmptyStateTextView);

        mloadingIndicator = rootView.findViewById(R.id.loading_indicator);

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news = mAdapter.getItem(i);

                Uri newsWebUri = Uri.parse(news.getmUri());
                Intent website = new Intent(Intent.ACTION_VIEW, newsWebUri);
                startActivity(website);
            }
        });
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            androidx.loader.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
            Log.v("MainActivity: ", "loader initialised");

        } else {
            View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);

        }
        mSwipeRefresh = rootView.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restartLoader();
                Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();

            }

        });


        return rootView;

    }

    private void restartLoader() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            androidx.loader.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(LOADER_ID, null, this);
            Log.v("MainActivity: ", "loader restart");

        } else {

            mloadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);

        }


    }


    @Override
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

        return new NewsLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(androidx.loader.content.Loader<List<News>> loader, List<News> news) {
        mAdapter.clear();
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        } else {
            mEmptyStateTextView.setText(R.string.no_news);
        }
        mloadingIndicator.setVisibility(View.GONE);
        mSwipeRefresh.setRefreshing(false);


    }

    @Override
    public void onLoaderReset(androidx.loader.content.Loader<List<News>> loader) {
        mAdapter.clear();

    }


}

