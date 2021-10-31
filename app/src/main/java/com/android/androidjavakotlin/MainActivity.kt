package com.android.androidjavakotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import java.lang.reflect.Array.newInstance
import java.net.URLClassLoader.newInstance

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun initView() {
        val toolbar = initToolbar()
        initDrawer(toolbar)
    }

    // регистрация drawer
    private fun initDrawer(toolbar: Toolbar) {
        val drawer: DrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        // Обработка навигационного меню
        val navigationView: NavigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            if (navigateFragment(id, item)) {
                drawer.closeDrawer(GravityCompat.START)
                return@OnNavigationItemSelectedListener true
            }
            false
        })
    }

    private fun initToolbar(): Toolbar {
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        return toolbar
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Здесь определяем меню приложения (активити)
        getMenuInflater().inflate(R.menu.main, menu)
        val search = menu.findItem(R.id.action_search) // поиск пункта меню поиска
        val searchText = search.actionView as SearchView // строка поиска
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // реагирует на конец ввода поиска
            override fun onQueryTextSubmit(query: String): Boolean {
                val queryToSearch = searchText.query.toString()
                Toast.makeText(this@MainActivity, queryToSearch, Toast.LENGTH_SHORT).show()
                return true
            }

            // реагирует на нажатие каждой клавиши
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (navigateFragment(id, item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    @SuppressLint("NonConstantResourceId")
    private fun navigateFragment(id: Int, item: MenuItem): Boolean {
        val i = when (id) {
            R.id.action_favorite -> {
                Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()
                val aboutPage: About = About.newInstance()
                val fragmentManager: FragmentManager = getSupportFragmentManager()
                val transaction = fragmentManager.beginTransaction()
                transaction.addToBackStack(null)
                transaction.replace(R.id.remarkDetailed, aboutPage)
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction.commit()
            }
            R.id.action_settings -> {
                Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()
                val aboutPage: About = About.newInstance()
                val fragmentManager: FragmentManager = getSupportFragmentManager()
                val transaction = fragmentManager.beginTransaction()
                transaction.addToBackStack(null)
                transaction.replace(R.id.remarkDetailed, aboutPage)
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction.commit()
            }
            R.id.action_about -> {
                val aboutPage: About = About.newInstance()
                val fragmentManager: FragmentManager = getSupportFragmentManager()
                val transaction = fragmentManager.beginTransaction()
                transaction.addToBackStack(null)
                transaction.replace(R.id.remarkDetailed, aboutPage)
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction.commit()
            }
        }
        return true
    }
}