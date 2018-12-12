package com.list.movie.hyuck.movielist.movielist.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.movielist.adapter.MovieListAdapter;
import com.list.movie.hyuck.movielist.movielist.adapter.MovieListAdapterView;
import com.list.movie.hyuck.movielist.movielist.adapter.viewholder.OnMovieDataDisplayPositionListener;
import com.list.movie.hyuck.movielist.movielist.adapter.viewholder.OnMovieDataItemClickListener;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;
import com.list.movie.hyuck.movielist.movielist.presenter.MovieListPresenter;
import com.list.movie.hyuck.movielist.movielist.presenter.MovieListPresenterImpl;
import com.list.movie.hyuck.movielist.widzets.CustomProgressDialog;
import com.list.movie.hyuck.movielist.widzets.CyanToast;
import com.list.movie.hyuck.movielist.utils.KeyboardUtil;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity implements MovieListView{
    private static final String MOVIE_TITLE_EDIT_TEXT_KEY = "MOVIE_TITLE_EDIT_TEXT_KEY";

    private MovieListPresenter presenter;
    private MovieListAdapterView adapterView;
    private CustomProgressDialog customProgressDialog;
    private EditText movieTitleEditText;
    private RecyclerView movieListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        initPresenter();
        initViews();
        initAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        attachViewToPresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();

        detachViewToPresenter();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        handlingSaveInstance(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        handlingRestoreInstanceState(savedInstanceState);
    }

    private void initPresenter() {
        presenter = new MovieListPresenterImpl(getApplicationContext());
    }

    private void initViews() {
        customProgressDialog = new CustomProgressDialog(this);

        movieTitleEditText = findViewById(R.id.movieTitleEditText);

        movieListRecyclerView = findViewById(R.id.movieListRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        movieListRecyclerView.setLayoutManager(layoutManager);
        movieListRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Button movieDataRequestButton = findViewById(R.id.movieDataRequestButton);
        movieDataRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestInitialMovieDataToPresenter();
            }
        });
    }

    private void initAdapter() {
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

        adapterView.setOnMovieDataDisplayPositionListener(new OnMovieDataDisplayPositionListener() {
            @Override
            public void onMovieDataDisplayPosition(int position) {
                requestPossibleAdditionalMovieData(position);
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
    public void showApplicationError(String errorMessage) {
        String message = String.format(getString(R.string.movie_data_load_application_error), errorMessage);
        showToastMessage(message);
    }

    @Override
    public void showNetworkNotConnectingError() {
        String message = getString(R.string.movie_data_network_not_connecting_error);
        showToastMessage(message);
    }

    @Override
    public void showServerSystemError(String errorMessage) {
        String message = String.format(getString(R.string.movie_data_load_system_error), errorMessage);
        showToastMessage(message);
    }

    @Override
    public void showNoMoreData() {
        String message = getString(R.string.movie_data_no_more_data_error);
        showToastMessage(message);
    }

    @Override
    public void showNonExistentWordError() {
        String message = getString(R.string.movie_data_non_existent_word_error);
        showToastMessage(message);
    }

    @Override
    public void showProgressDialog() {
        customProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        customProgressDialog.hide();
    }

    private void attachViewToPresenter() {
        presenter.attachView(this);
    }

    private void detachViewToPresenter() {
        presenter.detachView();
    }

    private void requestInitialMovieDataToPresenter() {
        String movieTitle = movieTitleEditText.getText().toString();

        presenter.requestInitialMovieData(movieTitle);
    }

    private void requestPossibleAdditionalMovieData(int displayPosition) {
        presenter.requestPossibleAdditionalMovieData(displayPosition);
    }

    private void requestHandlingOfItemClickToPresenter(int position) {
        presenter.requestHandlingOfItemClick(position);
    }

    private void handlingRestoreInstanceState(Bundle savedInstanceState) {
        String movieTile = savedInstanceState.getString(MOVIE_TITLE_EDIT_TEXT_KEY);
        movieTitleEditText.setText(movieTile);

        presenter.onRestoreInstanceState(savedInstanceState);
    }

    private void handlingSaveInstance(Bundle outState) {
        String movieTitle = movieTitleEditText.getText().toString();
        outState.putString(MOVIE_TITLE_EDIT_TEXT_KEY, movieTitle);

        presenter.onSaveInstanceState(outState);
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

    private void showToastMessage(String message) {
        CyanToast.showToastMessage(this, message);
    }
}
