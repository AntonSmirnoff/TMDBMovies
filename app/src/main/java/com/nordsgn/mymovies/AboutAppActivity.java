package com.nordsgn.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class AboutAppActivity extends AppCompatActivity {

    private static final String myWebUrl = "https://www.antonsmirnov.online";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
    }

    public void onClickGotoWebsite(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(myWebUrl));
        startActivity(intent);
    }

    public void onClickBackToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
