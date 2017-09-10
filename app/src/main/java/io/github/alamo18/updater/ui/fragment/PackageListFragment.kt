package io.github.alamo18.updater.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.alamo18.updater.R
import io.github.alamo18.updater.model.json.JSONResponse
import io.github.alamo18.updater.model.json.Repo
import io.github.alamo18.updater.ui.adapter.PackageAdapter
import io.github.alamo18.updater.utils.RequestInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class PackageListFragment : Fragment(), NavFragment {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: PackageAdapter

    private val packageList = ArrayList<Repo>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.package_list_fragment, container, false)
        bindViews(view, savedInstanceState)
        return view
    }


    private fun bindViews(layout: View, savedInstanceState: Bundle?) {
        swipeRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        recyclerView = layout.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PackageAdapter()
        recyclerView.adapter = adapter
        swipeRefreshLayout.setOnRefreshListener {
            loadJSON()
        }

        if (savedInstanceState == null) {
            loadJSON()
        } else {
            onPackageListLoaded(savedInstanceState.getParcelableArrayList<Repo>("packageList"))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList("packageList", packageList)
    }

    private fun loadJSON() {
        val retrofit = Retrofit.Builder()
                // repo json
                .baseUrl("https://gist.githubusercontent.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val request = retrofit.create(RequestInterface::class.java)
        val call = request.json
        call.enqueue(object : Callback<JSONResponse> {
            override fun onResponse(call: Call<JSONResponse>, response: Response<JSONResponse>) {
                val jsonResponse = response.body()
                val data = ArrayList(Arrays.asList(*if (jsonResponse != null) jsonResponse.repolist else arrayOf<Repo>()))
                onPackageListLoaded(data)
            }

            override fun onFailure(call: Call<JSONResponse>, died: Throwable) {
                adapter.error = getString(R.string.cannot_load_packages)
                adapter.loading = false
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun onPackageListLoaded(data: ArrayList<Repo>) {
        packageList.clear()
        packageList.addAll(data)
        adapter.setRepoList(packageList)
        adapter.loading = false
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onReselected() {
        recyclerView.smoothScrollToPosition(0)
    }

    companion object {

        fun newInstance() = PackageListFragment()
    }
}