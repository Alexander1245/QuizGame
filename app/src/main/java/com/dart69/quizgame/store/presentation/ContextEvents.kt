package com.dart69.quizgame.store.presentation

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.dart69.quizgame.R
import java.io.IOException

sealed class ContextEvents {
    abstract fun applyOn(context: Context)

    data class ShowToastEvent(
        val message: Int,
        val duration: Int = Toast.LENGTH_SHORT,
    ) : ContextEvents() {

        override fun applyOn(context: Context) {
            Toast.makeText(context, message, duration).show()
        }
    }

    data class SetWallpaperEvent(
        val imageUri: String,
    ) : ContextEvents() {

        @SuppressLint("ResourceType")
        override fun applyOn(context: Context) {
            val manager = WallpaperManager.getInstance(context.applicationContext)
            val stream = context.contentResolver.openInputStream(Uri.parse(imageUri))
            val message = try {
                val bitmap = Drawable.createFromStream(stream, imageUri)?.toBitmap()
                manager.setBitmap(bitmap)
                R.string.wallpapers_successfully_set
            } catch (e: IOException) {
                R.string.cant_set_wallpaper
            } finally {
                stream?.close()
            }
            ShowToastEvent(message).applyOn(context)
        }
    }
}
