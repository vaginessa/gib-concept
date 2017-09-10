package io.github.alamo18.updater.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.alamo18.updater.R

/**
 * Created by alamo with his pizza on 7/9/2017.
 */

class StoreFragment : Fragment(), NavFragment {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.bottomtab_store_fragment, container, false)
        bindViews(view, savedInstanceState)
        return view
    }

    private fun bindViews(layout: View, savedInstanceState: Bundle?) {
        //put dispenser here
    }

    override fun onReselected() {

    }

    companion object {

        fun newInstance() = StoreFragment()
    }
}