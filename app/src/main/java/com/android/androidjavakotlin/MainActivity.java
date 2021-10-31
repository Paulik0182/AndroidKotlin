package com.android.androidjavakotlin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
    }

    private void initView() {
        Toolbar toolbar = initToolbar ();
        initDrawer ( toolbar );
    }

    // регистрация drawer
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById ( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close );
        drawer.addDrawerListener ( toggle );
        toggle.syncState ();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById ( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener ( item -> {
            int id = item.getItemId ();
            if (navigateFragment ( id, item )) {
                drawer.closeDrawer ( GravityCompat.START );
                return true;
            }
            return false;
        } );
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Здесь определяем меню приложения (активити)
        getMenuInflater ().inflate ( R.menu.main, menu );
        MenuItem search = menu.findItem ( R.id.action_search ); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView (); // строка поиска
        searchText.setOnQueryTextListener ( new SearchView.OnQueryTextListener () {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                String queryToSearch = searchText.getQuery ().toString ();
                Toast.makeText ( MainActivity.this, queryToSearch, Toast.LENGTH_SHORT ).show ();
                return true;
            }

            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        } );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId ();

        if (navigateFragment ( id, item )) {
            return true;
        }
        return super.onOptionsItemSelected ( item );
    }

    @SuppressLint("NonConstantResourceId")
    private boolean navigateFragment(int id, MenuItem item) {
        switch (id) {
            case R.id.action_favorite:
                Toast.makeText ( MainActivity.this, item.getTitle (), Toast.LENGTH_SHORT ).show ();
            case R.id.action_settings:
                Toast.makeText ( MainActivity.this, item.getTitle (), Toast.LENGTH_SHORT ).show ();
            case R.id.action_about:
                About aboutPage = About.newInstance ();
                FragmentManager fragmentManager = getSupportFragmentManager ();
                FragmentTransaction transaction = fragmentManager.beginTransaction ();
                transaction.addToBackStack ( null );
                transaction.replace ( R.id.remarkDetailed, aboutPage );
                transaction.setTransition ( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
                transaction.commit ();
        }
        return true;
    }
}