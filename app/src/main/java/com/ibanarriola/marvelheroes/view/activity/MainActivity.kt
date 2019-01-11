package com.ibanarriola.marvelheroes.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ibanarriola.marvelheroes.R
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.ibanarriola.marvelheroes.view.fragments.HeroesListFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
