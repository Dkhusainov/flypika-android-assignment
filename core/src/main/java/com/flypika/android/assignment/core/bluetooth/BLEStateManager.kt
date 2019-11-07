package com.flypika.android.assignment.core.bluetooth

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import com.flypika.android.assignment.core.activity.ActivityPermissionCallback
import com.flypika.android.assignment.core.activity.ActivityResultCallback
import com.flypika.android.assignment.core.di.ui.ActivityScope
import kotlinx.coroutines.isActive
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ActivityScope
class BLEStateManager @Inject constructor(
  private val act: Activity,
  private val bluetoothManager: BluetoothManager
) :
  ActivityPermissionCallback,
  ActivityResultCallback {

  private companion object {
    const val BLUETOOTH_ACTIVITY_RESULT = 100
    const val LOCATION_ACTIVITY_RESULT = 101
  }

  suspend fun requestBLE(): Boolean {
    return requestBluetooth() && requestCoerceLocation()
  }

  private var requestContinuation: Continuation<Boolean>? = null
  private fun getContinuation(): Continuation<Boolean>? {
    val continuation = requestContinuation.also { requestContinuation = null }
    return when {
      continuation == null           -> null.also { Timber.e(IllegalStateException("Unexpected state: continuation for BLE enabling not found")) }
      !continuation.context.isActive -> null
      else                           -> continuation
    }
  }

  private val bluetoothEnabled get() = bluetoothManager.adapter.isEnabled
  private suspend fun requestBluetooth(): Boolean {
    if (bluetoothEnabled) return true

    return suspendCoroutine { continuation ->
      requestContinuation = continuation
      act.startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), BLUETOOTH_ACTIVITY_RESULT)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode != BLUETOOTH_ACTIVITY_RESULT) return

    getContinuation()?.resume(bluetoothEnabled)
  }

  private val coerceLocationEnabled get() = ActivityCompat.checkSelfPermission(act, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED
  private suspend fun requestCoerceLocation(): Boolean {
    if (coerceLocationEnabled) return true

    return suspendCoroutine { continuation ->
      requestContinuation = continuation
      ActivityCompat.requestPermissions(act, arrayOf(ACCESS_COARSE_LOCATION), LOCATION_ACTIVITY_RESULT)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    if (requestCode != LOCATION_ACTIVITY_RESULT) return

    val locationGrantResult = grantResults.first()
    val locationEnabled = locationGrantResult == PERMISSION_GRANTED
    getContinuation()?.resume(locationEnabled)
  }
}