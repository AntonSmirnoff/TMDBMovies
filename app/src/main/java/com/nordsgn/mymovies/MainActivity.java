package com.nordsgn.mymovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nordsgn.mymovies.adapters.MovieAdapter;
import com.nordsgn.mymovies.data.MainViewModel;
import com.nordsgn.mymovies.data.Movie;
import com.nordsgn.mymovies.utils.JSONUtils;
import com.nordsgn.mymovies.utils.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {


    private RecyclerView recyclerViewPosters;
    private MovieAdapter movieAdapter;
    private Switch switchSort;
    private TextView textViewPopularity;
    private TextView textViewTopRated;
    private ProgressBar progressBarLoading;

    private MainViewModel viewModel;

    private static final int LOADER_ID = 133;
    private LoaderManager loaderManager;

    private static int page = 1;
    private static int methodOfSort;
    private static boolean isLoading = false;

    private static String lang;


    //переопределнный метод для меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //переопределяем метод для реагирования на нажатия в меню ctrl+o
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

    private int getColumnCount() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //ширина в аппартно независимых пикселях
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        //тернарная операция
        return width / 185 > 2 ? width / 185 : 2;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Получаем язык устройства
        lang = Locale.getDefault().getLanguage();
        loaderManager = LoaderManager.getInstance(this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        switchSort = findViewById(R.id.switchSort);
        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewTopRated = findViewById(R.id.textViewTopRated);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
        movieAdapter = new MovieAdapter();
        recyclerViewPosters.setAdapter(movieAdapter);
        //чтобы фильмы сразу загрузились мы сделаем свитч тру
        switchSort.setChecked(true);
        //слушатель для switchSort
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                page = 1;
                setMethodOfSort(isChecked);
            }
        });
        switchSort.setChecked(false);
        //код срабатывающий при нажатии на постеры в ресайклерВью
        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                //Toast.makeText(MainActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
                //получаем фильм на который нажали
                Movie movie = movieAdapter.getMovies().get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", movie.getId());
                startActivity(intent);
            }
        });
        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                if (!isLoading) {
                    // Toast.makeText(MainActivity.this, "Конец списка", Toast.LENGTH_SHORT).show();
                    downloadData(methodOfSort, page);
                }
            }
        });

        LiveData<List<Movie>> moviesFromLiveData = viewModel.getMovies();
        moviesFromLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (page == 1)
                    movieAdapter.setMovies(movies);
            }
        });
    }


    public void onClickSetPopelarity(View view) {
        setMethodOfSort(false);
        switchSort.setChecked(false);

    }

    public void onClickSetTopRated(View view) {
        setMethodOfSort(true);
        switchSort.setChecked(true);
    }

    private void setMethodOfSort(boolean isTopRated) {
        if (isTopRated) {
            // если в положени включен, то мы методу сортировки влючен присваиваем
            // значание TOP_RATED
            methodOfSort = NetworkUtils.TOP_RATED;
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewPopularity.setTextColor(getResources().getColor(R.color.white_color));
        } else {
            methodOfSort = NetworkUtils.POPULARITY;
            textViewPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewTopRated.setTextColor(getResources().getColor(R.color.white_color));
        }
        downloadData(methodOfSort, page);
    }

    private void downloadData(int methodOFSort, int page) {
        URL url = NetworkUtils.buildURL(methodOFSort, page, lang);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(LOADER_ID, bundle, this);
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        NetworkUtils.JSONLoader jsonLoader = new NetworkUtils.JSONLoader(this, args);
        jsonLoader.setOnStartLoadingListener(new NetworkUtils.JSONLoader.OnStartLoadingListener() {
            @Override
            public void onStartLoading() {
                progressBarLoading.setVisibility(View.VISIBLE);
                isLoading = true;
            }
        });
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJson(data);
        if (movies != null && !movies.isEmpty()) {
            if (page == 1) {
                viewModel.deleteAllMovies();
                movieAdapter.clear();
            }
            for (Movie movie : movies) {
                viewModel.insertMovie(movie);
            }
            movieAdapter.addMovies(movies);
            page++;
        }
        //когда загрузка завершена присваем false
        isLoading = false;
        progressBarLoading.setVisibility(View.INVISIBLE);
        //после того как загрузчик отработает, пердаем ему Дестрой и значение ЛоадерИД
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}
