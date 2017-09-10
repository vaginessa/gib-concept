package io.github.alamo18.updater.utils

import io.github.alamo18.updater.model.json.JSONResponse
import retrofit2.Call
import retrofit2.http.GET

interface RequestInterface {
    @get:GET("/alamo18/3222f298b315e39df55afe8b0154a650/raw/fb0035d59aadc29bfc718c6290f8d529b1eb63a9/gistfile1.txt")
    val json: Call<JSONResponse>
}
