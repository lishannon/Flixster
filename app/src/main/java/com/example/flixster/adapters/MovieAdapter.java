package com.example.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


// MovieAdapter is the extension of RecyclerView
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

    // define the viewHolder: has the reference for the title, image and the description of the movie
    // it is the inner class
    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvTitle;
        TextView tvDescription;
        ImageView ivPoster;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // to link the layout and activity
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);

        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvDescription.setText(movie.getDescription());
            String imageURL;
            // if phone is in landscape mode
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                //then equal to backdrop img
                imageURL = movie.getLandscapeIMG();
            } else {
                //else imgURL is poster img
                imageURL = movie.getPosterIMG();
            }
            int radius = 35; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop

            //Glide.with(context).load(imageURL).into(ivPoster);
            Glide.with(context).load(imageURL).transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);
            // 1. Register click listener to the whole container
            //      1.1 find the reference of the whole contain er

            // Making the container (refer to container) clickable to the next activity and screen
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Debugging method, because once we click on the title there is a bubble at the bottom that display the title of the movie
                    // Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();

                    // 2. Navigate to the next screen on one tap - make the intent
                    Intent startDetailActivityIntent = new Intent(context, DetailActivity.class);
                    //startDetailActivityIntent.putExtra("title", movie.getTitle());
                    startDetailActivityIntent.putExtra("movie", Parcels.wrap(movie));

                    //transition purpose
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                           makeSceneTransitionAnimation((Activity) context, (View)tvTitle, "title");

                            //startDetailActivityIntent.putExtra("Description", movie.getDescription());
                    context.startActivity(startDetailActivityIntent);
                }
            });
            
        }
    }
}
