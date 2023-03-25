package com.dart69.quizgame.store.di

import com.dart69.quizgame.store.data.ResourceManager
import com.dart69.quizgame.store.data.WallpaperDataSource
import com.dart69.quizgame.store.data.WallpaperRepositoryImpl
import com.dart69.quizgame.store.domain.WallpaperRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StoreModule {

    @Binds
    fun bindResourceManager(
        manager: ResourceManager.Default
    ): ResourceManager

    @Binds
    @Singleton
    fun bindWallpaperDataSource(
        source: WallpaperDataSource.Fake
    ): WallpaperDataSource

    @Binds
    fun bindRepository(
        repositoryImpl: WallpaperRepositoryImpl
    ): WallpaperRepository
}