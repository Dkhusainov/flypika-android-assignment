@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.flypika.android.assignment.core.bluetooth

import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BLEScanner @Inject constructor(
  private val bluetoothManager: BluetoothManager
) {

  private val scanner: BluetoothLeScanner? get() = bluetoothManager.adapter?.bluetoothLeScanner

  /**
   * Requires bluetooth to be enabled
   */
  fun scanLe(): Flow<ScanResult> {
    return when (val scanner = scanner) {
      null -> throw IllegalStateException("Can't start LE scan - Bluetooth disabled")
      else -> scanner.scanLe()
    }
  }
}

class ScanFailedException(val errorCode: Int) : Throwable()