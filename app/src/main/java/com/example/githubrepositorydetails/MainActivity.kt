package com.example.githubrepositorydetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.githubrepositorydetails.databinding.ActivityMainBinding
import com.example.githubrepositorydetails.model.GitHubDataHolder
import com.example.githubrepositorydetails.interfaces.ApiInterface

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: GitHubDataHolder
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getGithubRepository(this);
    }

    fun getGithubRepository(mainActivity: MainActivity) {
        val apiInterface = ApiInterface.create().getGithubRepository()
        apiInterface.enqueue(object : Callback<List<GitHubDataHolder>> {
            override fun onResponse(
                call: Call<List<GitHubDataHolder>>?,
                response: Response<List<GitHubDataHolder>>?
            ) {
                Log.d("TAG", "@@@@ Respose:$response")
                if (response != null) {
                    Log.d("res", response.body().toString());
                    val items = response.body()
                    if (items != null) {
                        for (i in 0 until items.count()) {
                            val name = items[i].name ?: "N/A"

                            if (name.equals("facebook-android-sdk")) {
                                val id = items[i].id ?: "N/A"
                                val owner = items[i].owner
                                val avatar_url = owner.component1()
                                val forks_count = items[i].forks_count ?: "N/A"
                                val open_issues_count = items[i].open_issues_count ?: "N/A"
                                val language = items[i].language ?: "N/A"
                                Log.d(
                                    "TAG",
                                    "ID: $id name:$name language:$language forks_count:$forks_count avatar_url:$avatar_url"
                                )

                                binding.tvRepositoryName.setText(name)
                                binding.tvForks.setText(forks_count)
                                binding.tvRepositoryLanguage.setText(language)
                                binding.tvIssuesValue.setText(open_issues_count)

                                Log.d("TAG", "owner: $avatar_url")

                                Glide.with(mainActivity)
                                    .load(avatar_url)
                                    .into(binding.ivRepositoryLogo)
                                break
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<GitHubDataHolder>>?, t: Throwable?) {

            }
        })
    }
}