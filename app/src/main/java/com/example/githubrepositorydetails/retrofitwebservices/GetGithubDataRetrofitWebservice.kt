package com.example.githubrepositorydetails.retrofitwebservices

import com.example.githubrepositorydetails.model.GitHubDataHolder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GetGithubDataRetrofitWebservice {
    @GET("repos")
    fun getGithubRepository() : Call<List<GitHubDataHolder>>

    companion object {

        var BASE_URL = "https://api.github.com/users/Facebook/"

        var retrofitService: GetGithubDataRetrofitWebservice? = null

        fun getInstance() : GetGithubDataRetrofitWebservice {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(GetGithubDataRetrofitWebservice::class.java)
            }
            return retrofitService!!
        }

    }


}