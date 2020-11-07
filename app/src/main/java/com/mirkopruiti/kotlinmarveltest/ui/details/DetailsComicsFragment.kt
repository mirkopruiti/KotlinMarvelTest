package com.mirkopruiti.kotlinmarveltest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.mirkopruiti.kotlinmarveltest.MarvelApplication
import com.mirkopruiti.kotlinmarveltest.R
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelCharacter
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelComics
import com.mirkopruiti.kotlinmarveltest.ui.base.BaseViewModelFactory
import com.mirkopruiti.kotlinmarveltest.ui.home.HomeAdapter
import com.mirkopruiti.kotlinmarveltest.utils.ListItemLoading
import com.mirkopruiti.kotlinmarveltest.utils.PaginationCallback
import com.paginate.Paginate
import kotlinx.android.synthetic.main.fragment_comics.*
import kotlinx.android.synthetic.main.fragment_home.errorView
import javax.inject.Inject

class DetailsComicsFragment : Fragment() {

    private lateinit var viewModel: DetailsComicsViewModel
    private val adapter = DetailsComicsAdapter()
    @Inject
    lateinit var baseViewModelFactory: BaseViewModelFactory

    private lateinit var character: MarvelCharacter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        (activity?.application as MarvelApplication).appComponent.inject(this)

        viewModel = ViewModelProviders.of(this, baseViewModelFactory)[DetailsComicsViewModel::class.java]
        viewModel.liveData.observe(this,
                Observer<ArrayList<MarvelComics>> { comics: ArrayList<MarvelComics>? ->
                    if (comics == null) {
                        errorView.visibility = View.VISIBLE
                        return@Observer
                    }

                    val count = adapter.comics.size
                    adapter.comics = comics
                    adapter.notifyItemRangeInserted(count + 1, viewModel.count)
                })
        return inflater.inflate(R.layout.fragment_comics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        comicsList.setHasFixedSize(true)
        comicsList.adapter = adapter
        adapter.recyclerView!!.layoutManager = GridLayoutManager(context,2)

        adapter.comics = viewModel.liveData.value ?: arrayListOf()

        character = arguments?.getSerializable(HomeAdapter.EXTRA_CHARACTER) as MarvelCharacter

        val callbackComics = object : PaginationCallback(viewModel, errorView) {
            override fun onLoadMore() {
                super.onLoadMore()
                viewModel.loadMore(character.id)
            }
        }

        Paginate.with(comicsList, callbackComics)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(ListItemLoading())
                .build()

        errorView.setOnClickListener {
            callbackComics.onLoadMore()
        }

        super.onViewCreated(view, savedInstanceState)
    }
}