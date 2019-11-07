package com.flypika.android.assignment.core.activity.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.flypika.android.assignment.core.activity.CallbackActivity
import dagger.BindsInstance

interface CallbackActivityComponent {

  interface Factory<T : CallbackActivityComponent> {
    fun create(
      @BindsInstance act: Activity,
      @BindsInstance actCompat: AppCompatActivity
    ): T
  }

   fun inject(act: CallbackActivity)
}