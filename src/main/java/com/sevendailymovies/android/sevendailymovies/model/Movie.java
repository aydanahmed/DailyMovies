package com.sevendailymovies.android.sevendailymovies.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Movie implements Serializable {
    private String title;
    private String director;
    private String plot;
    private String posterURL;
    private String rate;
    private String genre;
    private String weekend;
    private String gross;
    private String stars;
    private String videoLink;
    private String year;
    private String country;
    private ArrayList<String> links;
    private static HashMap<String, ArrayList<Movie>> categories = new HashMap<>();

    public Movie(String title, String genre, String year, String rate, String country, String description, String casts, String director, String posterURL, String trailer) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rate = rate;
        this.stars = casts;
        this.director = director;
        this.posterURL = posterURL;
        this.videoLink = trailer;
        this.plot = description;
        this.country = country;
        links = new ArrayList<>();

    }

    public String getCountry() {
        return country;
    }

    public String getYear() {
        return year;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getWeekend() {
        return weekend;
    }

    public String getGross() {
        return gross;
    }

    public String getRate() {
        return rate;
    }

    public String getGenre() {
        return genre;
    }

    public String getStars() {
        return stars;
    }


    public String getPlot() {
        return plot;
    }


    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }


    public String getPosterURL() {
        return posterURL;
    }


    public static void addMovieToCategory(Movie movie) {
        switch (movie.getGenre()) {
            case "Action":
                createCategoryAndAddMovie("Action", movie);
                break;

            case "Adventure":
                createCategoryAndAddMovie("Adventure", movie);
                break;

            case "Fantasy":
                createCategoryAndAddMovie("Fantasy", movie);
                break;

            case "History":
                createCategoryAndAddMovie("History", movie);
                break;

            case "Horror":
                createCategoryAndAddMovie("Horror", movie);
                break;

            case "Mystery":
                createCategoryAndAddMovie("Mystery", movie);
                break;

            case "Comedy":
                createCategoryAndAddMovie("Comedy", movie);
                break;

            case "Crime":
                createCategoryAndAddMovie("Crime", movie);
                break;

            case "Drama":
                createCategoryAndAddMovie("Drama", movie);
                break;

            case "Musical":
                createCategoryAndAddMovie("Musical", movie);
                break;

            case "Biography":
                createCategoryAndAddMovie("Biography", movie);
                break;

            case "Documentary":
                createCategoryAndAddMovie("Documentary", movie);
                break;

            case "Thriller":
                createCategoryAndAddMovie("Thriller", movie);
                break;
            case "Sci-Fi":
                createCategoryAndAddMovie("Sci-Fi", movie);
                break;
        }
    }

    private static void createCategoryAndAddMovie(String categoryName, Movie movie) {
        if (!categories.containsKey(categoryName)) {
            categories.put(categoryName, new ArrayList<Movie>());
        }

        ArrayList<Movie> movies = categories.get(categoryName);
        int moviesSize = movies.size() - 1;

        for (int movieVal = 0; movieVal < moviesSize; movieVal++) {
            String movieTitle = movies.get(movieVal).getTitle();

            if (movieTitle.equals(movie.getTitle())) {
                movies.remove(movieVal);
            }
        }

        categories.get(categoryName).add(movie);
    }

    public static ArrayList<Movie> getMoviesFromCategory(String categoryName) {
        ArrayList<Movie> retVal = null;

        for (String categoryN : categories.keySet()) {
            if (categoryName.equals(categoryN)) {
                retVal = categories.get(categoryN);
            }
        }
        return retVal;
    }

    public void addMovieWatchLink(String link) {

        if (link != null && !link.equals("nolink")) {
            this.links.add(link);
        }
    }

    public boolean checkMovieWatchLinks() {

        return this.links.size() != 0;
    }

    public ArrayList<String> getMovieWatchlinks() {
       ArrayList<String> retVal = null;

        if (links.size() != 0) {
            retVal = this.links;
        }

        return retVal;
    }



}
