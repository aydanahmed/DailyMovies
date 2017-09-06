package com.sevendailymovies.android.sevendailymovies.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sevendailymovies.android.sevendailymovies.R;
import com.sevendailymovies.android.sevendailymovies.activity.MovieInfoActivity;
import com.sevendailymovies.android.sevendailymovies.model.database.DBManager;
import com.sevendailymovies.android.sevendailymovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieFavouriteAdapter extends RecyclerView.Adapter<MovieFavouriteAdapter.MovieVH> {
    private Activity activity;
    private List<Movie> movies;

    public MovieFavouriteAdapter(ArrayList<Movie> movies, Activity activity) {
        this.movies = movies;
        this.activity = activity;
    }

    @Override
    public MovieFavouriteAdapter.MovieVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.adapter_favourite, parent, false);
        return new MovieFavouriteAdapter.MovieVH(row);
    }

    @Override
    public void onBindViewHolder(final MovieFavouriteAdapter.MovieVH holder, final int position) {
        final Movie movie = movies.get(holder.getAdapterPosition());
        holder.title.setText(movie.getTitle());
        Glide
                .with(activity)
                .load(movie.getPosterURL())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .override(400,300)
                .into(holder.poster);
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.title.setVisibility(View.GONE);
                holder.poster.setVisibility(View.GONE);
                holder.like.setVisibility(View.GONE);
                notifyItemRemoved(holder.getAdapterPosition());
                DBManager.getInstance(activity).removeMovie(movie.getTitle());
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MovieInfoActivity.class);
                intent.putExtra("Title", movie.getTitle());
                intent.putExtra("Director", movie.getDirector());
                intent.putExtra("Plot", movie.getPlot());
                intent.putExtra("Stars", movie.getStars());
                intent.putExtra("Rate", movie.getRate());
                intent.putExtra("movieLink", movie.getVideoLink());
                intent.putExtra("movieYear", movie.getYear());
                intent.putExtra("movieGross", movie.getGross());
                intent.putExtra("movieWeekend", movie.getWeekend());
                intent.putExtra("movieCountry", movie.getCountry());
                intent.putExtra("movieGenre", movie.getGenre());
                if (movie.checkMovieWatchLinks()) {
                    intent.putExtra("movieWatchLinks", movie.getMovieWatchlinks());
                }
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    class MovieVH extends RecyclerView.ViewHolder {
        TextView title;
        ImageView poster;
        CardView cardView;
        ImageView like;
        final ProgressBar progressBar;
        MovieVH(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_favourite_TV);
            poster = (ImageView) itemView.findViewById(R.id.movie_favourite_poster2);
            cardView = (CardView) itemView.findViewById(R.id.card_view_favourite);
            like = (ImageView) itemView.findViewById(R.id.like_favourite);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);

        }
    }
}
