package com.mirkopruiti.kotlinmarveltest.ui.details

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mirkopruiti.kotlinmarveltest.R
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelCharacter
import com.mirkopruiti.kotlinmarveltest.ui.home.HomeAdapter
import kotlinx.android.synthetic.main.fragment_description.*

class DetailsDescriptionFragment() : Fragment() {

    private lateinit var character: MarvelCharacter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        character = arguments!!.getSerializable(HomeAdapter.EXTRA_CHARACTER) as MarvelCharacter

        if (character != null) {

            if (TextUtils.isEmpty(character.description)) {
                descriptionText.text = "No description available"
            } else {
                descriptionText.text = character.description
            }

        }
        super.onViewCreated(view, savedInstanceState)
    }

}