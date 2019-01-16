package com.ibanarriola.testapplication.dagger

import com.ibanarriola.testapplication.view.activity.MainActivity
import com.ibanarriola.testapplication.view.presenter.MainPresenter
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    abstract fun bindMainPresenter(): MainPresenter
}