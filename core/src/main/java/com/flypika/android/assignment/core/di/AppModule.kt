package com.flypika.android.assignment.core.di

import com.flypika.android.assignment.core.bluetooth.di.BluetoothModule
import com.flypika.android.assignment.core.di.ui.ActivityComponent
import com.flypika.android.assignment.core.viewmodel.di.ViewModelModule
import dagger.Module

@Module(
  includes = [
    BluetoothModule::class,
    ViewModelModule::class
  ],
  subcomponents = [ActivityComponent::class]
)
abstract class AppModule