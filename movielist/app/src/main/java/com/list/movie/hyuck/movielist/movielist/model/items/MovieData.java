package com.list.movie.hyuck.movielist.movielist.model.items;

import android.text.SpannableStringBuilder;

import com.list.movie.hyuck.movielist.utils.HtmlUtil;

public class MovieData {
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

    public void movieTitleApplyToBold() {
        applyBoldBuilder = HtmlUtil.applyBoldToText(title);
    }

    public void ratingMaximumConversion(float maximumRating) {
        final float defaultMaximumRating = 10f;

        userRating = userRating * (maximumRating / defaultMaximumRating);
    }

    public SpannableStringBuilder getBoldApliedTitle() {
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
