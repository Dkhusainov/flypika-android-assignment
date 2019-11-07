package com.flypika.android.assignment.core.viewmodel.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import com.flypika.android.assignment.core.viewmodel.ViewModelDaggerFactory

@Module
abstract class ViewModelModule {
  @Binds abstract fun a1(impl: ViewModelDaggerFactory): ViewModelProvider.Factory
}