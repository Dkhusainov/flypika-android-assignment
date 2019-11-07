package com.flypika.android.assignment.core.bluetooth.di

import dagger.Module

@Module(
  includes = [
    BluetoothModuleProviders::class
  ]
)
abstract class BluetoothModule {
}