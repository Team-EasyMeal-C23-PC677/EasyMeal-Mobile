package com.doanda.easymeal.utils

import android.app.Application
import android.content.Context

class MainApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any

        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context = MainApplication.applicationContext()
    }

//    override fun onCreate() {
//        super.onCreate()
//        appContext = applicationContext
//    }
//
//    companion object {
//        lateinit var appContext: Context
//    }

}