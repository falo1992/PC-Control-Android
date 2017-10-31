package com.znajde.gdzie.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainContent extends AppCompatActivity {

    private PowerFragment powerFragment = new PowerFragment();
    private MultimediaFragment multimediaFragment = new MultimediaFragment();
    private FileFragment fileFragment = new FileFragment();
    private android.app.FragmentManager fragmentManager = getFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_power:
                    fragmentManager.beginTransaction().replace(R.id.mainContent, powerFragment, powerFragment.getTag()).commit();
                    return true;
                case R.id.navigation_multimedia:
                    fragmentManager.beginTransaction().replace(R.id.mainContent, multimediaFragment, multimediaFragment.getTag()).commit();
                    return true;
                case R.id.navigation_file:
                    fragmentManager.beginTransaction().replace(R.id.mainContent, fileFragment, fileFragment.getTag()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager.beginTransaction().replace(R.id.mainContent, powerFragment, powerFragment.getTag()).commit();
        //navigation.getMenu().getItem(0).setChecked(true);
    }

}
