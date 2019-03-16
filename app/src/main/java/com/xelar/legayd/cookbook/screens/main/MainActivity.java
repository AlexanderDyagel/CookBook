package com.xelar.legayd.cookbook.screens.main;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.xelar.legayd.cookbook.R;
import com.xelar.legayd.cookbook.screens.add.AdditionFragment;
import com.xelar.legayd.cookbook.screens.favorites.FavoriteFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigationBottom);
        contentView = findViewById(R.id.contentView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.addRecipes: {
                        Toast.makeText(MainActivity.this, "Добавить", Toast.LENGTH_SHORT).show();
                        selectFragment = new AdditionFragment();
                        break;
                    }
                    case R.id.favorites: {
                        Toast.makeText(MainActivity.this, "Избранное", Toast.LENGTH_SHORT).show();
                        selectFragment = new FavoriteFragment();
                        break;
                    }
                    case R.id.listRecipes: {
                        Toast.makeText(MainActivity.this, "Избранное", Toast.LENGTH_SHORT).show();
                        selectFragment = new MainFragment();
                        break;
                    }
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectFragment).commit();
                return true;
            }
        });

        // Скрытие нижней навигации, пока клавиатура активна
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                contentView.getWindowVisibleDisplayFrame(r);
                int screenHeight = contentView.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    if (bottomNavigationView.getVisibility() == NavigationView.VISIBLE)
                        bottomNavigationView.setVisibility(NavigationView.GONE);
                } else {
                    if (bottomNavigationView.getVisibility() == NavigationView.GONE)
                        bottomNavigationView.setVisibility(NavigationView.VISIBLE);
                }
            }
        });
    }
}
