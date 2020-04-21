package com.nordsgn.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;

@Entity(tableName = "favourite_movies")
public class FavouriteMovie extends Movie {
    public FavouriteMovie(int uniqueid, int id, int voteCount, String title, String originalTitle, String overView, String posterPath, String bigPosterPath, String backDropPath, double voteAverage, String releaseDate) {
        super(uniqueid, id, voteCount, title, originalTitle, overView, posterPath, bigPosterPath, backDropPath, voteAverage, releaseDate);
    }
    @Ignore
    public FavouriteMovie(Movie movie) {
        super(movie.getUniqueid(), movie.getId(), movie.getVoteCount(), movie.getTitle(), movie.getOriginalTitle(), movie.getOverView(), movie.getPosterPath(), movie.getBigPosterPath(), movie.getBackDropPath(), movie.getVoteAverage(), movie.getReleaseDate());
    }

}
