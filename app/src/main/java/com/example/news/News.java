package com.example.news;

public class News {

    private String mtextHead, mImage, mtextAuthor, mUri, mPublishedAt;


    public News(String text, String image, String Author, String Uri, String publishedAt) {
        mtextHead = text;
        mImage = image;
        mtextAuthor = Author;
        mUri = Uri;
        mPublishedAt=publishedAt;

    }


    public String getMtextHead() {
        return mtextHead;
    }

    public String getmImage() {
        return mImage;
    }

    public String getMtextAuthor() {
        return mtextAuthor;
    }

    public String getmUri() {
        return mUri;
    }

    public String getmPublishedAt() {
        return mPublishedAt;
    }


}
