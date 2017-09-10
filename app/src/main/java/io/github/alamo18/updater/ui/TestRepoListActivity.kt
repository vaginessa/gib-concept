package io.github.alamo18.updater.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar

import java.util.ArrayList
import java.util.Arrays

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

/**
 * Created by alamo with his pizza on 7/9/2017.
 */

class TestRepoListActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var data: ArrayList<Repo>? = null
    private var adapter: PackageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        initViews()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.card_recycler_view)
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        adapter = PackageAdapter()
        recyclerView!!.adapter = adapter
        loadJSON()
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
                data = ArrayList<Repo>(Arrays.asList(*if (jsonResponse != null) jsonResponse.repolist else arrayOfNulls<Repo>(0)))
                adapter!!.setRepoList(data!!)
                adapter!!.loading = false
            }

            override fun onFailure(call: Call<JSONResponse>, died: Throwable) {
                adapter!!.error = getString(R.string.cannot_load_packages)
                adapter!!.loading = false
            }
        })
    }
}
