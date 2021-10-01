package com.example.news;


import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import java.util.List;


public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return Query.fetchNewsData(mUrl);

    }
}
