package com.list.movie.hyuck.movielist.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.adapters.MovieListAdapter;
import com.list.movie.hyuck.movielist.contracts.MovieListAdapterContract;
import com.list.movie.hyuck.movielist.contracts.MovieListContract;
import com.list.movie.hyuck.movielist.listeners.OnMovieDataItemClickListener;
import com.list.movie.hyuck.movielist.presenters.MovieListPresenter;
import com.list.movie.hyuck.movielist.utils.KeyboardUtil;

public class MovieListActivity extends AppCompatActivity implements MovieListContract.View{
    private MovieListContract.Presenter presenter;
    private MovieListAdapterContract.View adapterView;
    private EditText movieTitleEditText;


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


    private void initPresenter() {
        presenter = new MovieListPresenter(this);
        presenter.setView(this);
    }

    private void initViews() {
        movieTitleEditText = findViewById(R.id.movieTitleEditText);

        Button movieDataRequestButton = findViewById(R.id.movieDataRequestButton);
        movieDataRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestInitialMovieDataToPresenter();
            }
        });
    }

    private void initAdapterView() {
        RecyclerView movieListRecyclerView = findViewById(R.id.movieListRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        movieListRecyclerView.setLayoutManager(layoutManager);

        MovieListAdapter movieListAdapter = new MovieListAdapter(this);
        movieListRecyclerView.setAdapter(movieListAdapter);
        presenter.setAdapterModel(movieListAdapter);

        adapterView = movieListAdapter;
        adapterView.setOnMovieDataItemClickListener(new OnMovieDataItemClickListener() {
            @Override
            public void onMovieDataItemClick(int position) {
                requestHandlingOfItemClickToPresenter(position);
            }
        });
    }


    @Override
    public void refreshMovieList() {
        handlingMovieListRefresh();
    }

    @Override
    public void hideKeyboard() {
        handlingHideKeyboard();
    }

    @Override
    public void moveToMovieWeb(String uri) {
        handlingMoveToMovieWeb(uri);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        handlingShowErrorMessage(errorMessage);
    }


    private void requestInitialMovieDataToPresenter() {
        String movieTitle = movieTitleEditText.getText().toString();

        presenter.requestInitialMovieData(movieTitle);
    }

    private void requestHandlingOfItemClickToPresenter(int position) {
        presenter.requestHandlingOfItemClick(position);
    }

    private void handlingMovieListRefresh() {
        adapterView.refresh();
    }

    private void handlingHideKeyboard() {
        KeyboardUtil.hideKeyboard(this);
    }

    private void handlingMoveToMovieWeb(String uri) {
        Uri movieWebUri = Uri.parse(uri);
        Intent movieWebIntent = new Intent(Intent.ACTION_VIEW, movieWebUri);

        startActivity(movieWebIntent);
    }

    private void handlingShowErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
}
