package com.dart69.quizgame.common.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.dart69.quizgame.common.data.db.AppDataBase
import com.dart69.quizgame.store.data.WallpaperDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonProviders {
    private const val PREFS_NAME = "prefs"
    private const val DB_NAME = "AppDatabase"

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAppDataBase(
        @ApplicationContext context: Context,
    ): AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java, DB_NAME).build()

    @Provides
    @Singleton
    fun provideWallpaperDao(
        dataBase: AppDataBase
    ): WallpaperDao = dataBase.wallpaperDao()
}