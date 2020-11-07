package com.mirkopruiti.kotlinmarveltest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirkopruiti.kotlinmarveltest.MarvelApplication
import com.mirkopruiti.kotlinmarveltest.R
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelCharacter
import com.mirkopruiti.kotlinmarveltest.ui.base.BaseViewModelFactory
import com.mirkopruiti.kotlinmarveltest.utils.ListItemLoading
import com.mirkopruiti.kotlinmarveltest.utils.PaginationCallback
import com.paginate.Paginate
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import javax.inject.Inject

class HomeSearchFragment : Fragment() {

    var query: String = ""
    lateinit var adapter: HomeAdapter
    private lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var baseViewModelFactory: BaseViewModelFactory

    companion object {
        fun newInstance(query: String): HomeSearchFragment {
            val args = Bundle().apply {
                putSerializable(HomeSearchFragment.EXTRA_QUERY, query)
            }
            return HomeSearchFragment().apply {
                arguments = args
            }
        }

        const val EXTRA_QUERY = "extra-query"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity?.application as MarvelApplication).appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, baseViewModelFactory)[HomeViewModel::class.java]
        viewModel.liveData.observe(this,
                Observer<ArrayList<MarvelCharacter>> { characters: ArrayList<MarvelCharacter>? ->
                    if (characters == null) {
                        errorView.visibility = View.VISIBLE
                        return@Observer
                    }

                    if (characters.isEmpty()) {
                        return@Observer
                    }

                    val count = adapter.characters.size
                    adapter.characters = characters
                    adapter.notifyItemRangeInserted(count + 1, viewModel.count)
                })
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(context)
        charactersList.layoutManager = layoutManager
        charactersList.setHasFixedSize(true)

        adapter = HomeAdapter()
        adapter.characters = viewModel.liveData.value ?: arrayListOf()
        charactersList.adapter = adapter

        if (savedInstanceState == null) {
            refreshWithNewQuery()
        } else {
            query = arguments?.getString(EXTRA_QUERY) ?: ""
        }

        setupPagination()

        super.onViewCreated(view, savedInstanceState)
    }

    fun refreshWithNewQuery() {
        query = arguments?.getString(EXTRA_QUERY) ?: ""
        viewModel.reset()

        adapter.characters = arrayListOf()
        adapter.notifyDataSetChanged()
    }

    private fun setupPagination() {
        val callback = object : PaginationCallback(viewModel, errorView) {
            override fun onLoadMore() {
                super.onLoadMore()
                viewModel.loadMoreCharacters(query)
            }

        }

        Paginate.with(charactersList, callback)
                .setLoadingTriggerThreshold(2)
                .setLoadingListItemCreator(ListItemLoading())
                .build()

        errorView.setOnClickListener {
            callback.onLoadMore()
        }
    }
}