package com.example.githubrepositorydetails.interfaces

import com.example.githubrepositorydetails.model.GitHubDataHolder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("repos")
    fun getGithubRepository() : Call<List<GitHubDataHolder>>

    companion object {

        var BASE_URL = "https://api.github.com/users/Facebook/"
        //https://api.github.com/search/repositories?q=facebook-android-sdk&sort=stars&order=desc
        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }


}