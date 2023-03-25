package com.dart69.quizgame.store.presentation.models

import com.dart69.quizgame.store.domain.models.Wallpaper

data class WallpaperItem(
    val uri: String,
    val price: Int,
    val isAvailable: Boolean,
    val isBought: Boolean,
)

fun Wallpaper.toItem(points: Int): WallpaperItem =
    WallpaperItem(
        uri = uri,
        price = price,
        isAvailable = !isBought && points >= price,
        isBought = isBought,
    )

fun WallpaperItem.toWallpaper(): Wallpaper =
    Wallpaper(
        uri = uri,
        price = price,
        isBought = isBought,
    )