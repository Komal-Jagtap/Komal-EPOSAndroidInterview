package com.example.githubrepositorydetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var iv_repository_logo: ImageView
    lateinit var tv_repository_name: TextView
    lateinit var tv_repository_language: TextView
    lateinit var tv_forks_value: TextView
    lateinit var tv_issues_value: TextView
    lateinit var tv_starredby: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_repository_logo = findViewById(R.id.tv_starredby_value)
        tv_repository_name = findViewById(R.id.tv_repository_name)
        tv_repository_language = findViewById(R.id.tv_repository_language)
        tv_forks_value = findViewById(R.id.tv_forks_value)
        tv_issues_value = findViewById(R.id.tv_issues_value)
        tv_starredby = findViewById(R.id.tv_starredby)

    }
}