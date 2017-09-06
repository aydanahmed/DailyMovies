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

public class AllMovieAdapter extends RecyclerView.Adapter<AllMovieAdapter.MovieVH> {

    private Activity activity;
    private List<Movie> movies;

    public AllMovieAdapter(ArrayList<Movie> movies, Activity activity) {
        this.movies = movies;
        this.activity = activity;
    }


    @Override
    public AllMovieAdapter.MovieVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.adapter_all_movie, parent, false);
        return new AllMovieAdapter.MovieVH(row);
    }

    @Override
    public void onBindViewHolder(final AllMovieAdapter.MovieVH holder, int position) {
        final Movie movie = movies.get(holder.getAdapterPosition());

        if (DBManager.getInstance(activity).checkMovieExist(movie.getTitle())) {
            holder.dontLike.setVisibility(View.GONE);
            holder.like.setVisibility(View.VISIBLE);
        } else {
            holder.dontLike.setVisibility(View.VISIBLE);
            holder.like.setVisibility(View.GONE);
        }
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

                .into(holder.poster);


        holder.dontLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager.getInstance(activity).addMovie(movie.getTitle(), movie.getGenre(), movie.getYear(), movie.getRate(), movie.getCountry(), movie.getPlot(), movie.getStars(), movie.getDirector(), movie.getPosterURL(), movie.getVideoLink());
                addMovieLinksToDB(movie.getMovieWatchlinks().size(),movie);
                holder.dontLike.setVisibility(View.GONE);
                holder.like.setVisibility(View.VISIBLE);
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager.getInstance(activity).removeMovie(movie.getTitle());
                holder.dontLike.setVisibility(View.VISIBLE);
                holder.like.setVisibility(View.GONE);
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

    private void addMovieLinksToDB(int size, Movie movie) {
        if (size == 1) {
            DBManager.getInstance(activity).addLinks(movie.getMovieWatchlinks().get(0), "nolink", "nolink", movie.getTitle());
        }
        if (size == 2) {
            DBManager.getInstance(activity).addLinks(movie.getMovieWatchlinks().get(0), movie.getMovieWatchlinks().get(1), "nolink", movie.getTitle());
        }
        if (size == 3) {
            DBManager.getInstance(activity).addLinks(movie.getMovieWatchlinks().get(0), movie.getMovieWatchlinks().get(1), movie.getMovieWatchlinks().get(2), movie.getTitle());
        }

    }

    class MovieVH extends RecyclerView.ViewHolder {
        TextView title;
        ImageView poster;
        CardView cardView;
        ImageView dontLike;
        ImageView like;
        final ProgressBar progressBar;

        MovieVH(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_all_TV);
            poster = (ImageView) itemView.findViewById(R.id.movie_all_poster2);
            cardView = (CardView) itemView.findViewById(R.id.card_view_all);
            dontLike = (ImageView) itemView.findViewById(R.id.dontLike);
            like = (ImageView) itemView.findViewById(R.id.like);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressAll);
        }
    }
}
