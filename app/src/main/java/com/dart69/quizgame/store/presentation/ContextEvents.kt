package com.dart69.quizgame.store.presentation

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dart69.quizgame.R

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
            Glide.with(context)
                .asBitmap()
                .load(imageUri)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        manager.setBitmap(resource)
                        ShowToastEvent(R.string.wallpapers_successfully_set).applyOn(context)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }
    }
}
