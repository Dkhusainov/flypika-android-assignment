package com.flypika.android.assignment.core.di.ui

import android.app.Activity
import com.flypika.android.assignment.core.MainActivity
import com.flypika.android.assignment.core.activity.di.CallbackActivityComponent
import com.flypika.android.assignment.core.bluetooth.di.BluetoothUiModule
import com.flypika.android.assignment.core.impl.pulsometer.di.PulsometerComponent
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
  modules = [
    BluetoothUiModule::class
  ]
)
interface ActivityComponent :
  CallbackActivityComponent,
  PulsometerComponent {

  @Subcomponent.Factory
  interface Factory: CallbackActivityComponent.Factory<ActivityComponent>
}

fun Activity.di(): ActivityComponent = (this as MainActivity).di as ActivityComponent