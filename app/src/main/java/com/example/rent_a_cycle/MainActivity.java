package com.example.rent_a_cycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.rent_a_cycle.Fragments.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.nav_drawer_toggle)
    ImageView toggler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        navigationView.setNavigationItemSelectedListener(this);
        checkFriendsItem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkFriendsItem();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home : {
                navigationView.setCheckedItem(R.id.home);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            }
            case R.id.ride_history : {
                navigationView.setCheckedItem(R.id.ride_history);
                startActivity(new Intent(MainActivity.this, RideHistoryActivity.class));
                break;
            }
            case R.id.tariff : {
                navigationView.setCheckedItem(R.id.tariff);
                startActivity(new Intent(MainActivity.this, TariffActivity.class));
                break;
            }
            case R.id.settings : {
                navigationView.setCheckedItem(R.id.settings);
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnTouch(R.id.nav_drawer_toggle)
    void drawertoggler(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void checkFriendsItem(){
        navigationView.setCheckedItem(R.id.home);
    }
}
