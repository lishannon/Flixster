package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Rating;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyAQ2NE7H4JABEWyoq4fKdL7Vk6xPCLkdJw";
    private  static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    // Putting reference
    TextView tvDescription;
    TextView tvTitle;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;
    TextView tvDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //define the reference by stating the id of the item
        tvDescription = findViewById(R.id.tvDescription);
        tvTitle = findViewById(R.id.tvTitle);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.youTubePlayerView);
        tvDate = findViewById(R.id.date);

        // we dont need this anymore
        // String title = getIntent().getStringExtra("title");

        // retrieve the whole movie object
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        tvTitle.setText(movie.getTitle());
        tvDescription.setText(movie.getDescription());
        ratingBar.setRating((float)movie.getRating());
        tvDate.setText(movie.getMovieId_date());

        AsyncHttpClient client  = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0){
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", youtubeKey);
                    initializeYoutube(youtubeKey);

                } catch (JSONException e) {
                    Log.d("DetailActivity", "Failed to parse JSON",e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });


    }

    private void initializeYoutube(final String youtubeKey) {

        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                //debugging purpose
                Log.d("DetailActivity", "onInitializationSuccess");
                //this is a way to hard code a video :
                youTubePlayer.cueVideo(youtubeKey);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity","onInitializationFailure");
            }
        });
    }
}