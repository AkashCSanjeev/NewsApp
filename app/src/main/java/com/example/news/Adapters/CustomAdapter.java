package com.example.news.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.news.News;
import com.example.news.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<News> {
    /**
     * {@link CustomAdapter} sets the value of respective items in the list view
     */
    public CustomAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }

        News news = getItem(position);


        TextView heading = (TextView) listItemView.findViewById(R.id.header);
        heading.setText(news.getMtextHead());


        TextView source = (TextView) listItemView.findViewById(R.id.source);
        source.setText(news.getMtextAuthor());


        TextView time = (TextView) listItemView.findViewById(R.id.time);

        long currentTime = System.currentTimeMillis();

        long timeAgo = currentTime - toUnix(news.getmPublishedAt());
        String published =" ";

        if(timeAgo < 600000){
            int minutes = (int) ((timeAgo / (1000*60)) % 60);
            published += ""+minutes+" min Ago";
        }else if(timeAgo > 600000 && timeAgo < 3600000){
            published += "Few min Ago";
        }else{
            int hours   = (int) ((timeAgo / (1000*60*60)) % 12);
            published += ""+hours+" hours ago";
        }
        time.setText(published);




        ImageView image = (ImageView) listItemView.findViewById(R.id.NewsImage);
        Glide
                .with(listItemView)
                .load(news.getmImage())
                .centerCrop()
                .into(image);


        return listItemView;

    }

    static long toUnix(String time){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date dt = sdf.parse(time);
            long epoch = dt.getTime()/1000;

            return epoch;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }


}

