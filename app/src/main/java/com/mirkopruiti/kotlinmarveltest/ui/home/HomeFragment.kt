package com.mirkopruiti.kotlinmarveltest.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.mirkopruiti.kotlinmarveltest.MarvelApplication
import com.mirkopruiti.kotlinmarveltest.R
import com.mirkopruiti.kotlinmarveltest.remote.model.MarvelCharacter
import com.mirkopruiti.kotlinmarveltest.ui.base.BaseViewModelFactory
import com.mirkopruiti.kotlinmarveltest.utils.ListItemLoading
import com.mirkopruiti.kotlinmarveltest.utils.PaginationCallback
import com.paginate.Paginate
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private val adapter = HomeAdapter()
    @Inject
    lateinit var baseViewModelFactory: BaseViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        (activity?.application as MarvelApplication).appComponent.inject(this)

        viewModel = ViewModelProviders.of(this, baseViewModelFactory)[HomeViewModel::class.java]
        viewModel.liveData.observe(this,
                Observer<ArrayList<MarvelCharacter>> { characters: ArrayList<MarvelCharacter>? ->
                    if (characters == null) {
                        errorView.visibility = View.VISIBLE
                        return@Observer
                    }

                    val count = adapter.characters.size
                    adapter.characters = characters
                    adapter.notifyItemRangeInserted(count + 1, viewModel.count)
                })
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.order) {
            val reverse = reverseList(adapter.characters)
            adapter.characters = reverse
            adapter.notifyDataSetChanged()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        charactersList.setHasFixedSize(true)
        charactersList.adapter = adapter
        adapter.characters = viewModel.liveData.value ?: arrayListOf()
        adapter.recyclerView!!.layoutManager = GridLayoutManager(context,2)

        val callback = object : PaginationCallback(viewModel, errorView) {
            override fun onLoadMore() {
                super.onLoadMore()
                viewModel.loadMoreCharacters()
            }
        }

        Paginate.with(charactersList, callback)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(ListItemLoading())
                .build()

        errorView.setOnClickListener {
            callback.onLoadMore()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun <T> reverseList(list: List<T>): ArrayList<T> {
        return (list.indices)
                .map { i: Int -> list[list.size - 1 - i] }
                .toCollection(ArrayList())
    }

}
