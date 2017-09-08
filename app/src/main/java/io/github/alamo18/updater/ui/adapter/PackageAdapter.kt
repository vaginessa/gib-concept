package io.github.alamo18.updater.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import io.github.alamo18.updater.R
import io.github.alamo18.updater.model.json.Repo

class PackageAdapter : RecyclerView.Adapter<PackageAdapter.ViewHolder>() {

    private val repoList = ArrayList<Repo>()

    var loading = true
        get() = field
        set(value) {
            if (value == field) return
            field = value
            notifyDataSetChanged()
        }

    var error: String? = null
        get() = field
        set(value) {
            if (value == field) return
            field = value
            notifyDataSetChanged()
        }

    private val hasError: Boolean get() = error != null

    fun setRepoList(repos: ArrayList<Repo>) {
        repoList.clear()
        repoList.addAll(repos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): PackageAdapter.ViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(getLayout(itemType), viewGroup, false)
        return ViewHolder(itemView)
    }

    private fun getLayout(itemType: Int) = when (itemType) {
        TYPE_LOADING -> R.layout.loading
        TYPE_ERROR -> R.layout.error
        else -> R.layout.card_row
    }

    override fun onBindViewHolder(viewHolder: PackageAdapter.ViewHolder, i: Int) {
        if (loading) return
        if (hasError) {
            viewHolder.title.text = error
            return
        }
        viewHolder.title.text = repoList[i].name
        viewHolder.location.text = repoList[i].jsonLocation
        viewHolder.dev.text = repoList[i].dev
    }

    override fun getItemViewType(position: Int): Int {
        return if (loading) TYPE_LOADING else if (hasError) TYPE_ERROR else TYPE_REPO
    }

    override fun getItemCount(): Int {
        return if (loading || hasError) 1 else repoList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView by lazy { itemView.findViewById<TextView>(android.R.id.title) }
        val location: TextView by lazy { itemView.findViewById<TextView>(R.id.json_location) }
        val dev: TextView by lazy { itemView.findViewById<TextView>(R.id.dev) }
    }

    companion object {

        const val TYPE_LOADING = 0
        const val TYPE_REPO = 1
        const val TYPE_ERROR = 2
    }

}