package com.dart69.quizgame.store.data

import androidx.room.*
import com.dart69.quizgame.store.domain.models.Wallpaper
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpaperDao {

    @Query("SELECT * FROM Wallpaper")
    fun observeAll(): Flow<List<Wallpaper>>

    @Update
    fun update(wallpaper: Wallpaper)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMany(wallpapers: List<Wallpaper>)
}