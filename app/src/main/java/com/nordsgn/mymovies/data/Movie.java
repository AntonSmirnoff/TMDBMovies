package com.nordsgn.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import kotlin.experimental.ExperimentalTypeInference;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int uniqueid;
    private int id;
    private int voteCount;
    private String title;
    private String originalTitle;
    private String overView;
    private String posterPath;
    private String bigPosterPath;
    private String backDropPath;
    private double voteAverage;
    private String releaseDate;

    public Movie(int uniqueid, int id, int voteCount, String title, String originalTitle, String overView, String posterPath, String bigPosterPath, String backDropPath, double voteAverage, String releaseDate) {
        this.uniqueid = uniqueid;
        this.id = id;
        this.voteCount = voteCount;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overView = overView;
        this.posterPath = posterPath;
        this.bigPosterPath = bigPosterPath;
        this.backDropPath = backDropPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    @Ignore
    public Movie(int id, int voteCount, String title, String originalTitle, String overView, String posterPath, String bigPosterPath, String backDropPath, double voteAverage, String releaseDate) {
        this.id = id;
        this.voteCount = voteCount;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overView = overView;
        this.posterPath = posterPath;
        this.bigPosterPath = bigPosterPath;
        this.backDropPath = backDropPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public int getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(int uniqueid) {
        this.uniqueid = uniqueid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBigPosterPath() {
        return bigPosterPath;
    }

    public void setBigPosterPath(String bigPosterPath) {
        this.bigPosterPath = bigPosterPath;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
