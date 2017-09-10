package io.github.alamo18.updater.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import io.github.alamo18.updater.R
import io.github.alamo18.updater.ui.fragment.InstalledFragment
import io.github.alamo18.updater.ui.fragment.NavFragment
import io.github.alamo18.updater.ui.fragment.PackageListFragment
import io.github.alamo18.updater.ui.fragment.StoreFragment


class NavActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {

    lateinit var toolbar: Toolbar
    lateinit var bottomNav: BottomNavigationView

    var selected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        initViews(savedInstanceState)
    }

    private fun initViews(savedInstanceState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener(this)
        bottomNav.setOnNavigationItemReselectedListener(this)
        if (savedInstanceState == null)
            onNavigationItemSelected(bottomNav.menu.getItem(0))
        else
            selected = savedInstanceState.getInt("selected")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("selected", selected)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (selected == item.itemId) return false
        selected = item.itemId
        title = item.title
        showFragment(getFragment(item))
        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        val fragment = supportFragmentManager.findFragmentByTag("content")
        if (fragment != null && fragment is NavFragment) {
            fragment.onReselected()
        }
    }

    private fun getFragment(item: MenuItem) = when (item.itemId) {
        R.id.navigation_home -> PackageListFragment.newInstance()
        R.id.navigation_store -> StoreFragment.newInstance()
        R.id.navigation_installed -> InstalledFragment.newInstance()
        else -> throw IllegalStateException()
    }

    fun showFragment(fragment: Fragment) {
        if (fragment !is NavFragment)
            throw IllegalArgumentException("Child fragment must implement NavFragment")

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, "content")
                .commit()
    }

    // Inflate main menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Menu item handlers
        when (item.itemId) {
            R.id.action_search ->
                //morph the search
                return true

            R.id.menu_help -> {
                showHelp()
                return true
            }

            R.id.menu_change_device -> return true

            R.id.menu_settings ->
                //launch settings
                return true

            R.id.menu_about ->
                // launch aboutActivity
                return true

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showHelp() {
        // intent to startActivity(helpActivity)
    }
}
