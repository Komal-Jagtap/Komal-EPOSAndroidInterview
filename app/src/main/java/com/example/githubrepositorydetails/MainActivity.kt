package com.example.githubrepositorydetails

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubrepositorydetails.databinding.ActivityMainBinding
import com.example.githubrepositorydetails.model.GitHubDataHolder
import com.example.githubrepositorydetails.retrofitwebservices.GetGithubDataRetrofitWebservice
import com.example.githubrepositorydetails.retrofitwebservices.GithubRepository
import com.example.githubrepositorydetails.viewmodel.GithubViewModel
import com.example.githubrepositorydetails.viewmodel.GihubDataViewModelFactory
import android.view.WindowManager

import android.app.Activity

import android.app.ProgressDialog





class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: GithubViewModel
    private val retrofitService = GetGithubDataRetrofitWebservice.getInstance()
    lateinit var mProgressDialog :ProgressDialog;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel =
            ViewModelProvider(this, GihubDataViewModelFactory(GithubRepository(retrofitService))).get(
                GithubViewModel::class.java
            )


        viewModel.githubdataList.observe(this, Observer {
            Log.d("TAG", "onCreate: $it")

            hideDialog()
            if (it != null) {
                setUI(it)
            }
        })

        viewModel.errorMessage.observe(this, Observer {

            hideDialog()
        })

        /*if internet connection available call getGetHubData function */
        if (checkForInternet(this)) {
            binding.relMain.visibility = View.VISIBLE
            binding.relInternet.visibility = View.GONE
            viewModel.getGetHubData()
            showDialog(this)
        } else {
            binding.relMain.visibility = View.GONE
            binding.relInternet.visibility = View.VISIBLE

        }
    }

    /*Set Value to UI*/
    private fun setUI(it: List<GitHubDataHolder>) {
        val items = it
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

                Glide.with(this)
                    .load(avatarurl)
                    .into(binding.ivRepositoryLogo)
                break
            }
        }

    }


    /*Check InternetConnection*/
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
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(network) ?: return false

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

    fun showDialog(context: Context)
    {
         mProgressDialog = ProgressDialog(context)
        (context as Activity).window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        mProgressDialog.setIndeterminate(true)
        mProgressDialog.setMessage("Loading...")
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()

    }


    fun hideDialog() {
        if (mProgressDialog.isShowing) mProgressDialog.dismiss()
    }

}