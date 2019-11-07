package com.flypika.android.assignment.core.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.flypika.android.assignment.core.activity.di.CallbackActivityComponent
import com.flypika.android.assignment.core.di.DaggerAppComponent.factory
import javax.inject.Inject
import javax.inject.Provider

abstract class CallbackActivity : AppCompatActivity() {

  abstract fun factory(ctx: Context): CallbackActivityComponent.Factory<*>

  lateinit var di: CallbackActivityComponent

  override fun attachBaseContext(newBase: Context) {
    super.attachBaseContext(newBase)
    di = factory(newBase).create(
      act = this,
      actCompat = this
    )
    di.inject(this)
  }

  @Inject lateinit var activityCallbacks: Provider<ActivityCallbacks>
  @Inject lateinit var permissionCallbacks: Provider<ActivityPermissionCallbacks>

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    activityCallbacks.get().forEach { callback -> callback.onActivityResult(requestCode, resultCode, data) }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    permissionCallbacks.get().forEach { callback -> callback.onRequestPermissionsResult(requestCode, permissions, grantResults) }
  }
}
