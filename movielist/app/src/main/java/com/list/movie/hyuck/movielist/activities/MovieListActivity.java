package com.list.movie.hyuck.movielist.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.adapters.MovieListAdapter;
import com.list.movie.hyuck.movielist.contracts.MovieListAdapterContract;
import com.list.movie.hyuck.movielist.contracts.MovieListContract;
import com.list.movie.hyuck.movielist.presenters.MovieListPresenter;

public class MovieListActivity extends AppCompatActivity implements MovieListContract.View{
    private MovieListContract.Presenter presenter;
    private MovieListAdapterContract.View adapterView;
    private EditText movieTitleEditText;


    private void initPresenter() {
        presenter = new MovieListPresenter(this);
    }

    private void initViews() {
        movieTitleEditText = findViewById(R.id.movieTitleEditText);

        Button movieDataRequestButton = findViewById(R.id.movieDataRequestButton);
        movieDataRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processingMovieDataRequestToPresenter();
            }
        });
    }

    private void initAdapterView() {
        RecyclerView movieListRecyclerView = findViewById(R.id.movieListRecyclerView);
        MovieListAdapter movieListAdapter = new MovieListAdapter(this);
        movieListRecyclerView.setAdapter(movieListAdapter);
        adapterView = movieListAdapter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        initPresenter();
        initViews();
        initAdapterView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void refreshView() {
        adapterView.refresh();
    }

    @Override
    public void moveToMovieWeb(String uri) {
        processingMoveToMovieWeb(uri);
    }


    private void processingMovieDataRequestToPresenter() {
        String movieTitle = movieTitleEditText.getText().toString();

        // ToDo request movie data using movie title to presenter
    }

    private void processingMoveToMovieWeb(String uri) {
        Uri movieWebUri = Uri.parse(uri);
        Intent movieWebIntent = new Intent(Intent.ACTION_VIEW, movieWebUri);

        startActivity(movieWebIntent);
    }
}
