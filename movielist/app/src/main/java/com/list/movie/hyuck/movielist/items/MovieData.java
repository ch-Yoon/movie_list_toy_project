package com.list.movie.hyuck.movielist.items;

public class MovieData {
    private String title;
    private String link;
    private String image;
    private float userRating;
    private String pubDate;
    private String director;
    private String actor;


    public MovieData(String title, String link, String image, float userRating, String pubDate, String director, String actor) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.userRating = userRating;
        this.pubDate = pubDate;
        this.director = director;
        this.actor = actor;
    }


    public void manufactureUserRating(float maximumRating) {
        final float defaultMaximumRating = 10f;

        userRating = userRating * (maximumRating / defaultMaximumRating);
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
