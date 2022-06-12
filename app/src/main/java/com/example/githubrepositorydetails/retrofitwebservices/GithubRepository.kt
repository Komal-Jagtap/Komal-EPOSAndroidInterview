package com.example.githubrepositorydetails.retrofitwebservices

class GithubRepository constructor(private val retrofitService: GetGithubDataRetrofitWebservice) {

    fun getGithubRepository() = retrofitService.getGithubRepository()
}