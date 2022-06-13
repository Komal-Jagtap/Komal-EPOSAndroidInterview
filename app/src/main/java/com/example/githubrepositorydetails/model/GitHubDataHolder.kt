package com.example.githubrepositorydetails.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

//data class GitHubDataHolder(val repository_name: String, val repository_name_count_imageUrl: String, val repository_language_count: String, val forks_count: String,val issues_count:String,val starredby_count:String)

data class GitHubDataHolder(val id: String, val name: String,val stargazers_count:String,val  avatar_url:String,val language:String,val forks_count:String,val open_issues_count:String,  @SerializedName("owner")
val owner : Owner
)

data class Owner (
    val avatar_url : String
)


