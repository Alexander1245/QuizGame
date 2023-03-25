package com.dart69.quizgame.store.presentation

import androidx.lifecycle.viewModelScope
import com.dart69.quizgame.R
import com.dart69.quizgame.common.coroutines.AvailableDispatchers
import com.dart69.quizgame.common.presentation.BaseViewModel
import com.dart69.quizgame.store.data.WallpaperRepositoryImpl
import com.dart69.quizgame.store.domain.WallpaperRepository
import com.dart69.quizgame.store.presentation.models.WallpaperItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val repository: WallpaperRepository,
    private val dispatchers: AvailableDispatchers,
) : BaseViewModel<StoreViewModel.State, ContextEvents>(State.INITIAL), WallpaperItemAdapter.Callbacks {

    init {
        viewModelScope.launch(dispatchers.default) {
            repository.initialize()
        }

        repository.observePoints()
            .onEach { points -> states.update { it.copy(points = points) } }
            .launchIn(viewModelScope)

        repository.observeWallpapers()
            .onEach { items -> states.update { it.copy(wallpapers = items, isInProgress = false) } }
            .launchIn(viewModelScope)
    }

    override fun onSubmitPurchase(wallpaper: WallpaperItem) {
        viewModelScope.launch(dispatchers.default) {
            try {
                repository.buyWallpaper(wallpaper)
                events.emit(ContextEvents.SetWallpaperEvent(wallpaper.uri))
            } catch (error: WallpaperRepositoryImpl.NotEnoughPointsError) {
                events.emit(ContextEvents.ShowToastEvent(R.string.not_enough_points))
            }
        }
    }

    override fun onSubmitSetAsWallpaper(wallpaper: WallpaperItem) {
        viewModelScope.launch(dispatchers.default) {
            events.emit(ContextEvents.SetWallpaperEvent(wallpaper.uri))
        }
    }

    data class State(
        val wallpapers: List<WallpaperItem>,
        val points: Int,
        val isInProgress: Boolean,
    ) {
        companion object {
            val INITIAL = State(
                wallpapers = emptyList(),
                points = 0,
                isInProgress = true,
            )
        }
    }
}