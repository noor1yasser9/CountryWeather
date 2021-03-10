package com.nurbk.ps.countryweather.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutor private constructor() {
    private class MainThreadExecutor : Executor {
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }

        companion object {
            private val mainThreadHandler = Handler(Looper.getMainLooper())
        }
    }
     var networkExecutor: Executor = Executors.newFixedThreadPool(3)
    var diskIoExecutor: Executor = Executors.newSingleThreadExecutor()
    var mainExecutor: Executor= MainThreadExecutor()


    companion object {

        @Volatile
        private var instance: AppExecutor? = null
        private val LOCK = Any()

        operator fun invoke() =
            instance ?: synchronized(LOCK) {
                instance ?: createExecutor().also {
                    instance = it
                }
            }

        private fun createExecutor() =
            AppExecutor()

    }




}
