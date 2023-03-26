package com.dart69.quizgame.store.domain

import com.dart69.quizgame.store.presentation.models.WallpaperItem
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {
    fun observeWallpapers(): Flow<List<WallpaperItem>>

    suspend fun initialize()

    suspend fun buyWallpaper(item: WallpaperItem)
}