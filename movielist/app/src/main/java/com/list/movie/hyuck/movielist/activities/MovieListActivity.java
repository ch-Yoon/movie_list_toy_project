package com.list.movie.hyuck.movielist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.contracts.MovieListContract;
import com.list.movie.hyuck.movielist.presenters.MovieListPresenter;

public class MovieListActivity extends AppCompatActivity implements MovieListContract.View{
    private MovieListContract.Presenter presenter;

    private void initPresenter() {
        presenter = new MovieListPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        initPresenter();
    }
}
