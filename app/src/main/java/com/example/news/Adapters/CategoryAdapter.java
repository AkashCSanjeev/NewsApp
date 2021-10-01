package com.example.news.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.news.FragmentActivities.BusinessNewsFragment;
import com.example.news.FragmentActivities.EntertainmentNewsFragment;
import com.example.news.FragmentActivities.HealthNewsFragment;
import com.example.news.FragmentActivities.ScienceNewsFragment;
import com.example.news.FragmentActivities.SportsNewsFragment;
import com.example.news.FragmentActivities.TechnologyNewsFragment;
import com.example.news.FragmentActivities.WorldNewsFragment;
import com.example.news.News;
import com.example.news.R;

/**
 * {@link CategoryAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for
 * each list item based on a data source which is a list of {@link News} objects.
 */
public class CategoryAdapter extends FragmentPagerAdapter {

    /**
     * Context of the app
     */
    private Context mContext;

    /**
     * Create a new {@link CategoryAdapter} object.
     *
     * @param context is the context of the app
     * @param fm      is the fragment manager that will keep each fragment's state in the adapter
     *                across swipes.
     */
    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new WorldNewsFragment();
        } else if (position == 1) {
            return new SportsNewsFragment();
        } else if (position == 2) {
            return new TechnologyNewsFragment();
        } else if (position == 3) {
            return new EntertainmentNewsFragment();
        } else if (position == 4) {
            return new ScienceNewsFragment();
        } else if (position == 5) {
            return new BusinessNewsFragment();
        } else {
            return new HealthNewsFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 7;
    }


    /**
     * Return the title of each page
     */

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.world_title);
        } else if (position == 1) {
            return mContext.getString(R.string.sports_title);
        } else if (position == 2) {
            return mContext.getString(R.string.technology_title);
        } else if (position == 3) {
            return mContext.getString(R.string.entertainment_title);
        } else if (position == 4) {
            return mContext.getString(R.string.science_title);
        } else if (position == 5) {
            return mContext.getString(R.string.business_title);
        } else {
            return mContext.getString(R.string.health_title);
        }
    }
}
