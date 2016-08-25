package com.akshaykant.com.twom_2.processor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.akshaykant.com.twom_2.R;
import com.akshaykant.com.twom_2.data.MovieProvider;
import com.akshaykant.com.twom_2.data.ReviewColumns;
import com.akshaykant.com.twom_2.data.ReviewCursor;
import com.akshaykant.com.twom_2.data.TrailerColumns;
import com.akshaykant.com.twom_2.data.TrailerCursor;
import com.akshaykant.com.twom_2.entity.Movie;
import com.akshaykant.com.twom_2.entity.MovieDetails;
import com.akshaykant.com.twom_2.entity.Review;
import com.akshaykant.com.twom_2.entity.Trailer;
import com.akshaykant.com.twom_2.rest.MovieClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads MovieDetails (trailers & reviews) in the background.
 */
public class MovieDetailsLoader extends AsyncTaskLoader<MovieDetails> {
    private static final String LOG_TAG = MovieDetailsLoader.class.getSimpleName();

    private MovieClient movieClient = new MovieClient();

    private Context context;
    private final Movie movie;
    private MovieDetails movieDetails;

    public MovieDetailsLoader(Context context, Movie movie) {
        super(context);
        this.context = context;
        this.movie = movie;
    }

    @Override
    public MovieDetails loadInBackground() {
        boolean fromFavorites = isLoadFromFavorites();
        long startTime = System.currentTimeMillis();
        if (fromFavorites) {
            loadFromFavorites();
        } else {
            loadFromWeb();
        }
        long duration = System.currentTimeMillis() - startTime;
        String from = fromFavorites ? "database" : "web";
        Log.i(LOG_TAG, "loaded details for movie " + movie.title + " from " + from + " in " + duration + " ms (" +
                movieDetails.trailers.size() + " trailers, " + movieDetails.reviews.size() + " reviews)");

        return movieDetails;
    }

    private void loadFromFavorites() {
        movieDetails = new MovieDetails(movie);

        TrailerCursor trailerCursor = new TrailerCursor(context.getContentResolver().query(MovieProvider.Trailer.findByMovie(movie._id), null, null, null, TrailerColumns._ID + " asc"));
        movieDetails.trailers = loadTrailers(trailerCursor);
        trailerCursor.close();

        ReviewCursor reviewCursor = new ReviewCursor(context.getContentResolver().query(MovieProvider.Review.findByMovie(movie._id), null, null, null, ReviewColumns._ID + " asc"));
        movieDetails.reviews = loadReviews(reviewCursor);
        reviewCursor.close();
    }

    private List<Trailer> loadTrailers(TrailerCursor trailerCursor) {
        List<Trailer> trailers = new ArrayList<>(trailerCursor.getCount());
        while (trailerCursor.moveToNext()) {
            trailers.add(trailerCursor.getTrailer());
        }
        return trailers;
    }

    private List<Review> loadReviews(ReviewCursor reviewCursor) {
        List<Review> reviews = new ArrayList<>(reviewCursor.getCount());
        while (reviewCursor.moveToNext()) {
            reviews.add(reviewCursor.getReview());
        }
        return reviews;
    }

    private void loadFromWeb() {
        movieDetails = movieClient.fetchMovieDetails(movie);
    }

    private boolean isLoadFromFavorites() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String movieSelection = prefs.getString(context.getString(R.string.movie_selection_key), context.getString(R.string.movie_selection_entry_value_most_popular_first));

        return movieSelection.equals(context.getString(R.string.movie_selection_entry_value_favorites_only));
    }

    @Override
    protected void onStartLoading() {
        if (movieDetails == null) {
            forceLoad();
        }
    }
}
