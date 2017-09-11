package io.github.alamo18.updater.utils

import io.github.alamo18.updater.model.json.JSONResponse
import retrofit2.Call
import retrofit2.http.GET

interface RequestInterface {
    @get:GET("/alamo18/gib/master/repo_data/repo.json")
    val json: Call<JSONResponse>
}
