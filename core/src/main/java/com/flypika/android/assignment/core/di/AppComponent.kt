package com.flypika.android.assignment.core.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import com.flypika.android.assignment.core.App
import com.flypika.android.assignment.core.di.ui.ActivityComponent
import com.flypika.android.assignment.core.viewmodel.di.ViewModelComponent
import com.flypika.android.assignment.core.impl.pulsometer.di.PulsometerModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AppModule::class,

    PulsometerModule::class
  ]
)
interface AppComponent : ViewModelComponent {

  val activityComponentFactory: ActivityComponent.Factory

  @Component.Factory
  interface Factory {
    fun create(
      @BindsInstance ctx: Context,
      @BindsInstance app: Application
    ): AppComponent
  }
}

val Context.di: AppComponent get() = (applicationContext as App).di