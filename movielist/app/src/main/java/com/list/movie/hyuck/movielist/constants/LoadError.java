package com.list.movie.hyuck.movielist.constants;

public enum LoadError {
    SE01("Incorrect query request"),
    SE02("Invalid display value"),
    SE03("Invalid start value"),
    SE04("Invalid sort value"),
    SE05("Invalid search api"),
    SE06("Malformed encoding"),
    SE99("System Error"),
    UNKNOWN_ERROR("Uncheckable error");

    private String errorMessage;

    LoadError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
