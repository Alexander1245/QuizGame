package com.dart69.quizgame.common.timer

import android.os.CountDownTimer
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

interface SingleTimerLauncher {
    interface Callbacks {
        fun onTick(duration: Duration)

        fun onFinish()
    }

    fun startAndCancelPrevious(total: Duration, tickRate: Duration, callbacks: Callbacks)

    fun cancel()

    class Default @Inject constructor() : SingleTimerLauncher {
        private var timer: CountDownTimer? = null

        override fun startAndCancelPrevious(
            total: Duration,
            tickRate: Duration,
            callbacks: Callbacks
        ) {
            cancel()
            timer =
                object : CountDownTimer(total.inWholeMilliseconds, tickRate.inWholeMilliseconds) {
                    override fun onTick(millisUntilFinished: Long) {
                        callbacks.onTick(millisUntilFinished.milliseconds)
                    }

                    override fun onFinish() {
                        callbacks.onFinish()
                    }
                }.start()
        }

        override fun cancel() {
            timer?.cancel()
        }
    }
}