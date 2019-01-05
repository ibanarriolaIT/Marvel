package com.ibanarriola.testapplication.dagger.module

import android.content.Context
import com.ibanarriola.testapplication.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideApplication(app: App): Context = app
}