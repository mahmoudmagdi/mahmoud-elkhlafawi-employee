package com.sirmasolutions.khlafawi.di

import android.app.Application
import com.sirmasolutions.khlafawi.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class]
)
interface ProjectComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(myApplication: Application): Builder
        fun build(): ProjectComponent
    }

    fun inject(myApplication: MyApplication?)
}