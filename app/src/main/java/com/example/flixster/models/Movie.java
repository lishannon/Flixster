package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    String landscapeIMG;
    String posterIMG;
    String title;
    String description;
    String movieId_date;
    int movieId;
    double rating;


    public Movie (JSONObject jsonObject) throws JSONException {
        landscapeIMG = jsonObject.getString("backdrop_path");
        posterIMG = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        description = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movieId_date = jsonObject.getString("release_date");
    }

    // empty constructor needed by the Parceler library
    public Movie() {
    }

    // return a list of movie; construct a movie for each element in this array
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0 ; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterIMG() {
        // format the size as poster by hard coding it
        // complete the img URL
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterIMG);
    }

    public String getLandscapeIMG() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", landscapeIMG);
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() { return rating; }

    public int getMovieId() { return movieId; }

    public String getMovieId_date() {
        return movieId_date;
    }

}
