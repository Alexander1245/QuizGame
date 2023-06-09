package com.dart69.quizgame.common.di

import com.dart69.quizgame.common.coroutines.AvailableDispatchers
import com.dart69.quizgame.common.data.PointsDataSource
import com.dart69.quizgame.common.data.PointsRepositoryImpl
import com.dart69.quizgame.common.data.QuizRepositoryImpl
import com.dart69.quizgame.common.data.QuizzesDataSource
import com.dart69.quizgame.common.domain.PointsRepository
import com.dart69.quizgame.common.domain.QuizRepository
import com.dart69.quizgame.common.timer.SingleTimerLauncher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CommonModule {

    @Binds
    fun bindDispatchers(
        dispatchers: AvailableDispatchers.App
    ): AvailableDispatchers

    @Binds
    @Singleton
    fun bindQuizzesDataSource(
        dataSource: QuizzesDataSource.FakeDataSource
    ): QuizzesDataSource

    @Binds
    @Singleton
    fun bindPointsDataSource(
        dataSource: PointsDataSource.Default
    ): PointsDataSource

    @Binds
    fun bindSingleTimerLauncher(
        launcher: SingleTimerLauncher.Default
    ): SingleTimerLauncher

    @Binds
    @Singleton
    fun bindQuizRepository(
        gameImpl: QuizRepositoryImpl
    ): QuizRepository

    @Binds
    @Singleton
    fun bindPointRepository(
        repositoryImpl: PointsRepositoryImpl
    ): PointsRepository
}