package com.mirkopruiti.kotlinmarveltest.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.mirkopruiti.kotlinmarveltest.R
import com.mirkopruiti.kotlinmarveltest.ui.home.HomeFragment
import com.mirkopruiti.kotlinmarveltest.ui.home.HomeSearchFragment
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.red_marvel)))

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, HomeFragment())
            transaction.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return true
    }

    override fun onNewIntent(intent: Intent?) {
        if (intent != null && Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            val currentFragment = supportFragmentManager.findFragmentById(R.id.container)

            if (currentFragment !is HomeSearchFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container, HomeSearchFragment.newInstance(query))
                transaction.addToBackStack("")
                transaction.commit()
            } else {
                currentFragment.arguments?.putString(HomeSearchFragment.EXTRA_QUERY, query)
                currentFragment.refreshWithNewQuery()
            }
        }
        super.onNewIntent(intent)
    }
}
