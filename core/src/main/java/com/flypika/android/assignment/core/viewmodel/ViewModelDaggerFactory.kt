package com.flypika.android.assignment.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Singleton
class ViewModelDaggerFactory @Inject constructor(
  private val vmProviders: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    val viewModelProvider = vmProviders[modelClass] ?: throw IllegalArgumentException("ViewModel not binded: class=${modelClass}")
    return viewModelProvider.get() as T
  }
}