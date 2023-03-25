package com.dart69.quizgame.store.data

import com.dart69.quizgame.R
import com.dart69.quizgame.common.coroutines.AvailableDispatchers
import com.dart69.quizgame.store.domain.models.Wallpaper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface WallpaperDataSource {
    fun observeAll(): Flow<List<Wallpaper>>

    suspend fun buy(wallpaper: Wallpaper)

    suspend fun initialize()

    class Fake @Inject constructor(
        private val dispatchers: AvailableDispatchers,
        private val dao: WallpaperDao,
        private val resourceManager: ResourceManager,
    ) : WallpaperDataSource {
        override fun observeAll(): Flow<List<Wallpaper>> = dao.observeAll()

        override suspend fun buy(wallpaper: Wallpaper) = withContext(dispatchers.io) {
            dao.update(
                wallpaper.copy(isBought = true),
            )
        }

        override suspend fun initialize() = withContext(dispatchers.io) {
            val predefined = createPredefined(resourceManager)
            dao.insertMany(predefined)
        }

        private companion object {
            val WALLPAPERS = listOf(
                R.drawable.wallpaper_1,
                R.drawable.wallpaper_2,
                R.drawable.wallpaper_3,
                R.drawable.wallpaper_4,
                R.drawable.wallpaper_5,
            )

            fun createPredefined(resourceManager: ResourceManager) =
                List(WALLPAPERS.size) { index ->
                    Wallpaper(
                        uri = resourceManager.getUri(WALLPAPERS[index]),
                        price = (index + 1) * 5,
                        isBought = false,
                    )
                }
        }
    }
}