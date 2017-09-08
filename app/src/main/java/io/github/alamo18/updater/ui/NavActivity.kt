package io.github.alamo18.updater.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import io.github.alamo18.updater.R
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
}
