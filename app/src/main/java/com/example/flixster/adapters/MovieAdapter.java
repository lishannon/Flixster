package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // inflating a layout from XMl and returning the holder
    // inflating: taking the layout XML and parsing it to create the view and viewgroup...
    // ...objects from the elements and their attributes specified within
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter:", "onCreateViewHolder");
        // this will return a view
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        // wrap the view inside the view holder
        return new ViewHolder(movieView);
    }

    // involve populating data into the holder
    // put information to the viewHolder class
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter:", "onBindViewHolder" + position);

        // get the movie at the passed in position
        Movie movie = movies.get(position);

        // bind the data in the list
        holder.bind(movie);

    }

    // return the number of item in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // define the viewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ivTitle;
        TextView ivDescription;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // to link the layout and activity
            ivTitle = itemView.findViewById(R.id.ivTitle);
            ivDescription = itemView.findViewById(R.id.ivDescription);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            ivTitle.setText(movie.getTitle());
            ivDescription.setText(movie.getDescription());
            String imageURL;
            // if phone is in landscape mode
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                //then equal to backdrop img
                imageURL = movie.getLandscapeIMG();
            } else {
                //else imgURL is poster img
                imageURL = movie.getPosterIMG();
            }
            Glide.with(context).load(imageURL).into(ivPoster);
        }
    }
}
