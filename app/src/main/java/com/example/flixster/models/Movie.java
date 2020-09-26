package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    String landscapeIMG;
    String posterIMG;
    String title;
    String description;

    public Movie (JSONObject jsonObject) throws JSONException {
        landscapeIMG = jsonObject.getString("backdrop_path");
        posterIMG = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        description = jsonObject.getString("overview");
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
}
