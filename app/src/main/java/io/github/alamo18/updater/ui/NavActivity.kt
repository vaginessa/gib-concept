package io.github.alamo18.updater.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import io.github.alamo18.updater.R
import io.github.alamo18.updater.ui.fragment.HomeFragment
import io.github.alamo18.updater.ui.fragment.InstalledFragment
import io.github.alamo18.updater.ui.fragment.PackageListFragment
import io.github.alamo18.updater.ui.fragment.StoreFragment


class NavActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var bottomNav: BottomNavigationView

    var selected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        initViews()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener(this)
        onNavigationItemSelected(bottomNav.menu.getItem(0))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (selected == item.itemId) return false
        selected = item.itemId
        title = item.title
        showFragment(getFragment(item))
        return true
    }

    private fun getFragment(item: MenuItem) = when (item.itemId) {
        // Packagelistfragment is just a repo parser test for nao, the real one will be HomeFragment
        R.id.navigation_home -> PackageListFragment.newInstance()
        R.id.navigation_store -> StoreFragment.newInstance()
        R.id.navigation_installed -> InstalledFragment.newInstance()
        else -> throw IllegalStateException()
    }

    fun showFragment(fragment: Fragment) {
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
