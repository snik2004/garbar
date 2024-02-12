package com.tinkofflab.garbar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.tinkofflab.garbar.movies.listmovie.ui.ListMoviesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startPopularMovieScreen()
    }

    private fun startPopularMovieScreen() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val fragment = ListMoviesFragment()
        supportFragmentManager.commit {
            add(R.id.fragment_container, fragment, "ListMoviesFragment")
            addToBackStack(null)
        }
    }
}