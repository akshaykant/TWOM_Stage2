package com.akshaykant.com.twom_2.rest;



import com.akshaykant.com.twom_2.entity.Movie;
import com.akshaykant.com.twom_2.entity.Review;
import com.akshaykant.com.twom_2.entity.Trailer;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Example rest calls:
 *
 * <pre>
 * Movies: http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=YOUR_API_KEY_HERE
 *
 * Trailers: http://api.themoviedb.org/3/movie/206647/videos?api_key=YOUR_API_KEY_HERE
 *
 * Reviews: http://api.themoviedb.org/3/movie/206647/reviews?api_key=YOUR_API_KEY_HERE
 * </pre>
 */
public interface MovieService {

    @GET("/3/discover/movie")
    Call<MovieDbResponse<Movie>> loadMovies(@Query("sort_by") String sortBy, @Query("api_key") String apiKey);

    @GET("/3/movie/{movieId}/videos")
    Call<MovieDbResponse<Trailer>> loadTrailers(@Path("movieId") int movieId, @Query("api_key") String apiKey);

    @GET("/3/movie/{movieId}/reviews")
    Call<MovieDbResponse<Review>> loadReviews(@Path("movieId") int movieId, @Query("api_key") String apiKey);

}
