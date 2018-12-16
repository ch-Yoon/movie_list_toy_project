package com.list.movie.hyuck.movielist.movielist.view;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.movielist.adapter.MovieListAdapter;
import com.list.movie.hyuck.movielist.movielist.adapter.MovieListAdapterView;
import com.list.movie.hyuck.movielist.movielist.adapter.viewholder.OnMovieDataDisplayPositionListener;
import com.list.movie.hyuck.movielist.movielist.adapter.viewholder.OnMovieDataItemClickListener;
import com.list.movie.hyuck.movielist.movielist.presenter.MovieListPresenter;
import com.list.movie.hyuck.movielist.movielist.presenter.MovieListPresenterImpl;
import com.list.movie.hyuck.movielist.widzets.CustomProgressDialog;
import com.list.movie.hyuck.movielist.widzets.CyanToast;
import com.list.movie.hyuck.movielist.utils.KeyboardUtil;


public class MovieListActivity extends AppCompatActivity implements MovieListView{

    private static final String MOVIE_TITLE_EDIT_TEXT_KEY = "MOVIE_TITLE_EDIT_TEXT_KEY";
    private static final int MOVIE_WEB_REQUEST_CODE = 1;

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

        handlingSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        handlingRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MOVIE_WEB_REQUEST_CODE) {
            overridePendingTransition(R.anim.anim_slide_move_to_insdie_from_left, R.anim.anim_slide_move_to_right_from_inside);
        }
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
                requestFirstMovieDataOfNowTitle();
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
                requestHandlingOfItemClick(position);
            }
        });

        adapterView.setOnMovieDataDisplayPositionListener(new OnMovieDataDisplayPositionListener() {
            @Override
            public void onMovieDataDisplayPosition(int position) {
                requestPossibleMoreMovieData(position);
            }
        });
    }


    @Override
    public void refreshMovieList() {
        refreshAdapterView();
    }

    @Override
    public void moveToMovieWeb(String uri) {
        Uri movieWebUri = Uri.parse(uri);
        Intent movieWebIntent = new Intent(Intent.ACTION_VIEW, movieWebUri);

        startActivityForResult(movieWebIntent, MOVIE_WEB_REQUEST_CODE);
        overridePendingTransition(R.anim.anim_slide_move_to_insdie_from_right, R.anim.anim_slide_move_to_left_from_inside);
    }

    @Override
    public void showApplicationError(String errorMessage) {
        String applicationErrorMessage = String.format(getString(R.string.movie_data_load_application_error), errorMessage);
        showToastMessage(applicationErrorMessage);
    }

    @Override
    public void showNetworkNotConnectingError() {
        String notConnectingErrorMessage = getString(R.string.movie_data_network_not_connecting_error);
        showToastMessage(notConnectingErrorMessage);
    }

    @Override
    public void showServerSystemError(String errorMessage) {
        String serverSystemErrorMessage = String.format(getString(R.string.movie_data_load_system_error), errorMessage);
        showToastMessage(serverSystemErrorMessage);
    }

    @Override
    public void showNoMoreData() {
        String noMoreDataMessage = getString(R.string.movie_data_no_more_data_error);
        showToastMessage(noMoreDataMessage);
    }

    @Override
    public void showNonExistentWordError() {
        String nonExistentWordErrorMessage = getString(R.string.movie_data_non_existent_word_error);
        showToastMessage(nonExistentWordErrorMessage);
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtil.hideKeyboard(this);
    }

    @Override
    public void showProgressDialog() {
        customProgressDialog.show(true);
    }

    @Override
    public void hideProgressDialog() {
        customProgressDialog.show(false);
    }


    private void attachViewToPresenter() {
        presenter.attachView(this);
    }

    private void detachViewToPresenter() {
        presenter.detachView();
    }

    private void requestFirstMovieDataOfNowTitle() {
        String movieTitle = movieTitleEditText.getText().toString();
        presenter.loadFirstMovieDataOfNowTitle(movieTitle);
    }

    private void requestPossibleMoreMovieData(int displayPosition) {
        presenter.loadPossibleMoreMovieData(displayPosition);
    }

    private void requestHandlingOfItemClick(int position) {
        presenter.handlingOfItemClick(position);
    }

    private void handlingSaveInstanceState(Bundle outState) {
        saveInstanceStateOfView(outState);
        presenter.onSaveInstanceState(outState);
    }

    private void handlingRestoreInstanceState(Bundle savedInstanceState) {
        restoreInstanceStateOfView(savedInstanceState);
        presenter.onRestoreInstanceState(savedInstanceState);
    }

    private void saveInstanceStateOfView(Bundle outState) {
        String movieTitle = movieTitleEditText.getText().toString();
        outState.putString(MOVIE_TITLE_EDIT_TEXT_KEY, movieTitle);
    }

    private void restoreInstanceStateOfView(Bundle savedInstanceState) {
        String movieTile = savedInstanceState.getString(MOVIE_TITLE_EDIT_TEXT_KEY);
        movieTitleEditText.setText(movieTile);
    }

    private void refreshAdapterView() {
        adapterView.refresh();
    }

    private void showToastMessage(String message) {
        CyanToast.showToastMessage(this, message);
    }

}
