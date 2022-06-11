package com.example.githubrepositorydetails

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.githubrepositorydetails.databinding.ActivityMainBinding
import com.example.githubrepositorydetails.model.GitHubDataHolder
import com.example.githubrepositorydetails.interfaces.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (checkForInternet(this)) {
            binding.relMain.visibility = View.VISIBLE
            binding.relInternet.visibility = View.GONE
            getGithubRepository(this)
        } else {
            binding.relMain.visibility = View.GONE
            binding.relInternet.visibility = View.VISIBLE

        }
    }

    private fun getGithubRepository(mainActivity: MainActivity) {
        val apiInterface = ApiInterface.create().getGithubRepository()
        apiInterface.enqueue(object : Callback<List<GitHubDataHolder>> {
            override fun onResponse(
                call: Call<List<GitHubDataHolder>>?,
                response: Response<List<GitHubDataHolder>>?
            ) {
                Log.d("TAG", "@@@@ Respose:$response")
                if (response != null) {
                    Log.d("res", response.body().toString())
                    val items = response.body()
                    if (items != null) {
                        for (i in 0 until items.count()) {
                            val name = items[i].name

                            if (name == "facebook-android-sdk") {
                                val id = items[i].id
                                val owner = items[i].owner
                                val avatarurl = owner.component1()
                                val forkscount = items[i].forks_count
                                val openissuescount = items[i].open_issues_count
                                val language = items[i].language
                                Log.d(
                                    "TAG",
                                    "ID: $id name:$name language:$language forks_count:$forkscount avatar_url:$avatarurl"
                                )

                                binding.tvRepositoryName.setText(name)
                                binding.tvForksValue.setText(forkscount)
                                binding.tvRepositoryLanguage.setText(language)
                                binding.tvIssuesValue.setText(openissuescount)

                                Log.d("TAG", "owner: $avatarurl")

                                Glide.with(mainActivity)
                                    .load(avatarurl)
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

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}