package com.flypika.android.assignment.core

import android.content.Context
import android.os.Bundle
import com.flypika.android.assignment.core.activity.CallbackActivity
import com.flypika.android.assignment.core.activity.di.CallbackActivityComponent
import com.flypika.android.assignment.core.di.di
import com.flypika.android.assignment.core.impl.pulsometer.PulsometerScreenFragment

class MainActivity : CallbackActivity() {

  override fun factory(ctx: Context): CallbackActivityComponent.Factory<*> = ctx.di.activityComponentFactory

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    if (savedInstanceState == null) {
      supportFragmentManager
        .beginTransaction()
        .replace(R.id.container, PulsometerScreenFragment())
        .commit()
    }
  }
}
