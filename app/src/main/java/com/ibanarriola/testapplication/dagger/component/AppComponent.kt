package com.ibanarriola.testapplication.dagger.component

import com.ibanarriola.testapplication.App
import com.ibanarriola.testapplication.dagger.ActivityBuilder
import com.ibanarriola.testapplication.dagger.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class,
    ActivityBuilder::class, AndroidSupportInjectionModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}