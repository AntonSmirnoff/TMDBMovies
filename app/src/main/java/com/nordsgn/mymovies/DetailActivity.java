package com.nordsgn.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nordsgn.mymovies.adapters.ReviewAdapter;
import com.nordsgn.mymovies.adapters.TrailerAdapter;
import com.nordsgn.mymovies.data.FavouriteMovie;
import com.nordsgn.mymovies.data.MainViewModel;
import com.nordsgn.mymovies.data.Movie;
import com.nordsgn.mymovies.data.Review;
import com.nordsgn.mymovies.data.Trailer;
import com.nordsgn.mymovies.utils.JSONUtils;
import com.nordsgn.mymovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewAddToFavourite;
    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;
    private ScrollView scrollViewInfo;

    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;

    private int id;
    private Movie movie;
    private FavouriteMovie favouriteMovie;
    private MainViewModel viewModel;

    private static String lang;

    //переопределнный метод для меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //переопределяем метод для реагирования на нажатия ctrl+o
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                Intent intentToFavourite = new Intent(this, FavourityActivity.class);
                startActivity(intentToFavourite);
                break;
            case R.id.itemAboutApp:
                Intent intentToAboutApp = new Intent(this,AboutAppActivity.class);
                startActivity(intentToAboutApp);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Set system language
        lang = Locale.getDefault().getLanguage();
        imageViewAddToFavourite = findViewById(R.id.imageViewAddToFavourite);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitile);
        textViewRating = findViewById(R.id.textViewRating);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewOverview = findViewById(R.id.textViewOverview);
        scrollViewInfo = findViewById(R.id.scrollViewInfo);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        } else {
            //иначае закроем эту активность и будут открыта активность, которая вызвала эту активность
            finish();
        }
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        movie = viewModel.getMovieById(id);

        Picasso.get().load(movie.getBigPosterPath()).placeholder(R.drawable.movie_placeholder).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewRating.setText(Double.toString(movie.getVoteAverage()));
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewOverview.setText(movie.getOverView());
        setFavourite();

        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        reviewAdapter = new ReviewAdapter();
        trailerAdapter = new TrailerAdapter();
        trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {
                Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intentToTrailer);
            }
        });

        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReviews.setAdapter(reviewAdapter);
        recyclerViewTrailers.setAdapter(trailerAdapter);

        JSONObject jsonObjectTrailers = NetworkUtils.getJSONForVideos(movie.getId(),lang);
        JSONObject jsonObjectReviews = NetworkUtils.getJSONForReviews(movie.getId(), lang);
        ArrayList<Trailer> trailers = JSONUtils.getTrailerFromJSON(jsonObjectTrailers);
        ArrayList<Review> reviews = JSONUtils.getReviewsFromJSON(jsonObjectReviews);
        reviewAdapter.setReviews(reviews);
        trailerAdapter.setTrailers(trailers);
        scrollViewInfo.smoothScrollBy(0,0);
    }

    public void onClickChangeFavourite(View view) {

        if (favouriteMovie == null) {
            viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, R.string.add_favourite, Toast.LENGTH_SHORT).show();
        } else {
            viewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, R.string.remove_favorite, Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    private void setFavourite() {
        favouriteMovie = viewModel.getFavouriteMovieById(id);
        // установим необходимую звезду
        if (favouriteMovie == null) {
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_add_to);
        } else {
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_remove);
        }
    }

}