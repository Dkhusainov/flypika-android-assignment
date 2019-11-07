package com.flypika.android.assignment.core.viewmodel.di

import androidx.lifecycle.ViewModelProvider

interface ViewModelComponent {
  val viewModelFactory: ViewModelProvider.Factory
}