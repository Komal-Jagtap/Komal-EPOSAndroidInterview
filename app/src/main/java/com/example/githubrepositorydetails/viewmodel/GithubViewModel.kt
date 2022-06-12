package com.example.githubrepositorydetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubrepositorydetails.model.GitHubDataHolder
import com.example.githubrepositorydetails.retrofitwebservices.GithubRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubViewModel constructor(private val repository: GithubRepository)  : ViewModel() {

    val githubdataList = MutableLiveData<List<GitHubDataHolder>>()
    val errorMessage = MutableLiveData<String>()

    fun getGetHubData() {

        val response = repository.getGithubRepository()
        response.enqueue(object : Callback<List<GitHubDataHolder>> {
            override fun onResponse(call: Call<List<GitHubDataHolder>>, response: Response<List<GitHubDataHolder>>) {
                githubdataList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<GitHubDataHolder>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}