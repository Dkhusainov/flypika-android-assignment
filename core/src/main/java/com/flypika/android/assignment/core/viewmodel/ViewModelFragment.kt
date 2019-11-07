package com.flypika.android.assignment.core.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.flypika.android.assignment.core.di.di

abstract class ViewModelFragment : Fragment() {

  inline fun <reified VM: ViewModel> viewModel(): ViewModelLazy<VM> {
    return ViewModelLazy(
      viewModelClass = VM::class,
      storeProducer = ::getViewModelStore,
      factoryProducer = { act.di.viewModelFactory }
    )
  }

  val act get() = requireActivity()
}