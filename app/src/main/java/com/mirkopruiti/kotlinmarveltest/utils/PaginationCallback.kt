package com.mirkopruiti.kotlinmarveltest.utils

import android.view.View
import com.mirkopruiti.kotlinmarveltest.ui.base.BaseViewModel
import com.paginate.Paginate

abstract class PaginationCallback(private val viewModel: BaseViewModel<out Any>,
                                  private val errorView: View?) : Paginate.Callbacks {

    override fun isLoading() = viewModel.loading

    override fun hasLoadedAllItems(): Boolean {
        return if (viewModel.isFirstRequest) {
            false
        } else {
            viewModel.offset >= viewModel.total
        }
    }

    override fun onLoadMore() {
        errorView?.visibility = View.GONE
    }
}