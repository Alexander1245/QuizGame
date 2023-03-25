package com.dart69.quizgame.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dart69.quizgame.store.data.WallpaperDao
import com.dart69.quizgame.store.domain.models.Wallpaper

@Database(
    entities = [Wallpaper::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun wallpaperDao(): WallpaperDao
}