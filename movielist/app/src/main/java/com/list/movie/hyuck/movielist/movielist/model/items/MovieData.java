package com.list.movie.hyuck.movielist.movielist.model.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;

import com.list.movie.hyuck.movielist.utils.HtmlUtil;

public class MovieData implements Parcelable {
    private String title;
    private String link;
    private String image;
    private float userRating;
    private String pubDate;
    private String director;
    private String actor;
    private SpannableStringBuilder applyBoldBuilder;

    public MovieData(String title, String link, String image, float userRating, String pubDate, String director, String actor) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.userRating = userRating;
        this.pubDate = pubDate;
        this.director = director;
        this.actor = actor;
    }

    protected MovieData(Parcel in) {
        title = in.readString();
        link = in.readString();
        image = in.readString();
        userRating = in.readFloat();
        pubDate = in.readString();
        director = in.readString();
        actor = in.readString();
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(link);
        parcel.writeString(image);
        parcel.writeFloat(userRating);
        parcel.writeString(pubDate);
        parcel.writeString(director);
        parcel.writeString(actor);
    }

    public void movieTitleApplyToBold() {
        applyBoldBuilder = HtmlUtil.applyBoldToText(title);
    }

    public void ratingMaximumConversion(float maximumRating) {
        final float defaultMaximumRating = 10f;

        userRating = userRating * (maximumRating / defaultMaximumRating);
    }

    public SpannableStringBuilder getApplyBoldBuilder() {
        return applyBoldBuilder;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public float getUserRating() {
        return userRating;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDirector() {
        return director;
    }

    public String getActor() {
        return actor;
    }

}
