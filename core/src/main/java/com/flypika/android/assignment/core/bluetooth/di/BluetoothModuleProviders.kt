package com.flypika.android.assignment.core.bluetooth.di

import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.core.content.getSystemService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object BluetoothModuleProviders {
  //@formatter:off
  @JvmStatic @Singleton @Provides fun a1(ctx: Context): BluetoothManager = ctx.getSystemService()!!
}