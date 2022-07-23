package com.dc.tast1.di

import android.content.Context
import androidx.activity.ComponentActivity
import com.dc.tast1.data.remote.LocalRepositoryImp
import com.dc.tast1.data.remote.ServerRepositoryImp
import com.dc.tast1.domain.repositoy.LocalRepository
import com.dc.tast1.domain.repositoy.ServerRepository
import com.dc.tast1.screen.viewmodel.SharedViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLocalRepository(
        @ApplicationContext appContext: Context
    ): LocalRepository {
        return LocalRepositoryImp(
            appContext.getSharedPreferences("main", ComponentActivity.MODE_PRIVATE)
        )
    }



    @Singleton
    @Provides
    fun provideServerRepository(): ServerRepository {
        return ServerRepositoryImp()
    }


    @Singleton
    @Provides
    fun provideSharedViewModel(
        @ApplicationContext appContext: Context
    ): SharedViewModel {
        return SharedViewModel(
            provideLocalRepository(
                appContext
            )
        )
    }


}