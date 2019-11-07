package com.flypika.android.assignment.core.impl.pulsometer.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.flypika.android.assignment.core.viewmodel.ViewModelKey
import com.flypika.android.assignment.core.impl.pulsometer.PulsometerScreenViewModel

@Module
abstract class PulsometerModule {
  //@formatter:off
  @Binds @IntoMap @ViewModelKey(PulsometerScreenViewModel::class) abstract fun a1(impl: PulsometerScreenViewModel): ViewModel
}