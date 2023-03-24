package com.dart69.quizgame.common.di

import com.dart69.quizgame.common.coroutines.AvailableDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CommonModule {

    @Binds
    fun bindDispatchers(
        dispatchers: AvailableDispatchers.App
    ): AvailableDispatchers
}