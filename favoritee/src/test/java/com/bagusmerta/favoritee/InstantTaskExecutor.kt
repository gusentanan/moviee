package com.bagusmerta.favoritee

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor

object InstantTaskExecutor {
    fun start() {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread() = true

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
    }

    fun end() {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}