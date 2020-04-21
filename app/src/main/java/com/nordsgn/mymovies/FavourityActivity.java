package com.nordsgn.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nordsgn.mymovies.adapters.MovieAdapter;
import com.nordsgn.mymovies.data.FavouriteMovie;
import com.nordsgn.mymovies.data.MainViewModel;
import com.nordsgn.mymovies.data.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavourityActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavouriteMovies;
    private MovieAdapter adapter;
    private MainViewModel viewModel;

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
        switch (id) {
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
        setContentView(R.layout.activity_favourity);
        recyclerViewFavouriteMovies = findViewById(R.id.recyclerViewFafouriteMovies);
        recyclerViewFavouriteMovies.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MovieAdapter();
        recyclerViewFavouriteMovies.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<List<FavouriteMovie>> favouriteMovies = viewModel.getFavouriteMovies();
        favouriteMovies.observe(this, new Observer<List<FavouriteMovie>>() {
            @Override
            public void onChanged(List<FavouriteMovie> favouriteMovies) {
                List<Movie> movies = new ArrayList<>();
                if (favouriteMovies != null) {
                    movies.addAll(favouriteMovies);
                    adapter.setMovies(movies);
                }
            }
        });
        adapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                //Toast.makeText(MainActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
                //получаем фильм на который нажали
                Movie movie = adapter.getMovies().get(position);
                Intent intent = new Intent(FavourityActivity.this, DetailActivity.class);
                intent.putExtra("id", movie.getId());
                startActivity(intent);
            }
        });

    }
}
