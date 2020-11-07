package com.mirkopruiti.kotlinmarveltest.ui.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mirkopruiti.kotlinmarveltest.R
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelCharacter
import com.mirkopruiti.kotlinmarveltest.ui.home.HomeAdapter
import com.mirkopruiti.kotlinmarveltest.utils.GlideApp
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailsActivity : AppCompatActivity() {

    private var _isDestroyed = false
    var isAvailable: Boolean
        get() = _isDestroyed
        set(value) {
            _isDestroyed = value
        }

    companion object {
        fun startActivity(context: Context, character: MarvelCharacter, characterImageView: ImageView, characterNameView: TextView) {

            val characterImageTransitionName = ViewCompat.getTransitionName(characterImageView)
            val characterNameTransitionName = ViewCompat.getTransitionName(characterNameView)

            val intent = Intent(context, DetailsActivity::class.java).apply {
                putExtra(HomeAdapter.EXTRA_CHARACTER, character)
                putExtra(HomeAdapter.EXTRA_CHARACTER_IMAGE_TRANSITION_NAME,
                        characterImageTransitionName)
                putExtra(HomeAdapter.EXTRA_CHARACTER_NAME_TRANSITION_NAME,
                        characterNameTransitionName)
            }

            val sharedElements = arrayOf(
                    Pair<View, String>(characterImageView, characterImageTransitionName)
            )
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, *sharedElements)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.window.sharedElementsUseOverlay = true
                context.startActivity(intent, options.toBundle())
            } else {
                context.startActivity(intent)
            }
        }
    }

    private lateinit var character: MarvelCharacter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)
        toggleHomeAsUp(true)

        if (savedInstanceState == null) {

            character = intent.getSerializableExtra(HomeAdapter.EXTRA_CHARACTER) as MarvelCharacter

            GlideApp.with(this)
                    .load(character.thumbnail?.path + '.' + character.thumbnail?.extension)
                    .dontTransform()
                    .into(characterImage)

            characterName.text = character.name

        }

        setupViewPager(pager)
        tabLayout!!.setupWithViewPager(pager)
    }

    private fun setupViewPager(viewPager: ViewPager) {

        val adapter = DetailsPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

        val descriptionFragment = DetailsDescriptionFragment()
        var comicsFragment = DetailsComicsFragment()
        val bundle = Bundle()
        bundle.putSerializable(HomeAdapter.EXTRA_CHARACTER, character)
        descriptionFragment.arguments = bundle
        comicsFragment.arguments = bundle

        adapter.addFragment(descriptionFragment,"Description")
        adapter.addFragment(comicsFragment,"Comics List")

        viewPager.adapter = adapter
    }

    override fun onDestroy() {
        isAvailable = false
        super.onDestroy()
    }

    private fun toggleHomeAsUp(active: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(active)
        supportActionBar?.setDisplayShowHomeEnabled(active)

        if (active) {
            toolbar.navigationIcon = resources.getDrawable(R.mipmap.baseline_close_white_24)
        }
    }
}