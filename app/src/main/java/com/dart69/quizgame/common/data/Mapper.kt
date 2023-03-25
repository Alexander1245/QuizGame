package com.dart69.quizgame.common.data

fun interface Mapper<I, O> {
    fun map(from: I): O
}