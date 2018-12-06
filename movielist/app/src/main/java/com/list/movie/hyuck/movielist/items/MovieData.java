package com.list.movie.hyuck.movielist.items;

public class MovieData {
    private String title;
    private String image;
    private int userRating;
    private String pubDate;
    private String director;
    private String actor;


    public MovieData(String title, String image, int userRating, String pubDate, String director, String actor) {
        this.title = title;
        this.image = image;
        this.userRating = userRating;
        this.pubDate = pubDate;
        this.director = director;
        this.actor = actor;
    }


    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getUserRating() {
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
