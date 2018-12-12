package com.list.movie.hyuck.movielist.movielist.presenter.manager;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestLog implements Parcelable{
    private String movieTitle;
    private int dataSizeOfPreviousRequest;
    private int expectedDataSizeAfterRequest;

    RequestLog() {
    }

    protected RequestLog(Parcel in) {
        movieTitle = in.readString();
        dataSizeOfPreviousRequest = in.readInt();
        expectedDataSizeAfterRequest = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieTitle);
        dest.writeInt(dataSizeOfPreviousRequest);
        dest.writeInt(expectedDataSizeAfterRequest);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RequestLog> CREATOR = new Creator<RequestLog>() {
        @Override
        public RequestLog createFromParcel(Parcel in) {
            return new RequestLog(in);
        }

        @Override
        public RequestLog[] newArray(int size) {
            return new RequestLog[size];
        }
    };

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getDataSizeOfPreviousRequest() {
        return dataSizeOfPreviousRequest;
    }

    public void setDataSizeOfPreviousRequest(int dataSizeOfPreviousRequest) {
        this.dataSizeOfPreviousRequest = dataSizeOfPreviousRequest;
    }

    public int getExpectedDataSizeAfterRequest() {
        return expectedDataSizeAfterRequest;
    }

    public void setExpectedDataSizeAfterRequest(int expectedDataSizeAfterRequest) {
        this.expectedDataSizeAfterRequest = expectedDataSizeAfterRequest;
    }
}
