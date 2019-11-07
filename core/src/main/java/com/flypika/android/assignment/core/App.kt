package com.flypika.android.assignment.core

import android.app.Application
import android.os.Looper
import android.util.Printer
import com.flypika.android.assignment.core.di.AppComponent
import com.flypika.android.assignment.core.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {

  lateinit var di: AppComponent

  override fun onCreate() {
    super.onCreate()

    di = DaggerAppComponent.factory().create(
      app = this,
      ctx = this
    )

    Timber.plant(Timber.DebugTree())
//    Looper.getMainLooper().setMessageLogging(Printer(::println) )
  }
}