package com.list.movie.hyuck.movielist.movielist.model.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;

import com.list.movie.hyuck.movielist.utils.HtmlUtil;

public class MovieData implements Parcelable {

    private String requestTitle;
    private String title;
    private String link;
    private String image;
    private float userRating;
    private String pubDate;
    private String director;
    private String actor;
    private SpannableStringBuilder applyBoldBuilder;


    protected MovieData(Parcel in) {
        requestTitle = in.readString();
        title = in.readString();
        link = in.readString();
        image = in.readString();
        userRating = in.readFloat();
        pubDate = in.readString();
        director = in.readString();
        actor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(requestTitle);
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(image);
        dest.writeFloat(userRating);
        dest.writeString(pubDate);
        dest.writeString(director);
        dest.writeString(actor);
    }

    @Override
    public int describeContents() {
        return 0;
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
