package com.flypika.android.assignment.core.bluetooth.di

import com.flypika.android.assignment.core.activity.ActivityResultCallback
import com.flypika.android.assignment.core.bluetooth.BLEStateManager
import com.flypika.android.assignment.core.activity.ActivityPermissionCallback
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module(
  includes = [
  ]
)
abstract class BluetoothUiModule {
  @Binds @IntoSet abstract fun a1(impl: BLEStateManager): ActivityResultCallback
  @Binds @IntoSet abstract fun a2(impl: BLEStateManager): ActivityPermissionCallback
}