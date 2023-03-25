package com.dart69.quizgame.store.data

import android.content.ContentResolver
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ResourceManager {
    fun getUri(res: Int): String

    class Default @Inject constructor(
        @ApplicationContext private val context: Context,
    ) : ResourceManager {
        override fun getUri(res: Int): String {
            val resources = context.resources
            return "${ContentResolver.SCHEME_ANDROID_RESOURCE}://${
                resources.getResourcePackageName(res)
            }/${resources.getResourceTypeName(res)}/${resources.getResourceEntryName(res)}"
        }
    }
}