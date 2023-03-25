package com.dart69.quizgame.store.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wallpaper(
    @PrimaryKey val uri: String,
    val price: Int,
    val isBought: Boolean,
)
