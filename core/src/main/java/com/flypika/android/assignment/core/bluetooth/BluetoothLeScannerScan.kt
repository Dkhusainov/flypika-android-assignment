@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.flypika.android.assignment.core.bluetooth

import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun BluetoothLeScanner.scanLe(): Flow<ScanResult> = callbackFlow {

  fun send(scan: ScanResult) {
    if (!isClosedForSend) {
      offer(scan)
    }
  }

  val callback = object : ScanCallback() {
    override fun onBatchScanResults(results: MutableList<ScanResult>) = results.forEach(::send)
    override fun onScanResult(callbackType: Int, result: ScanResult) = result.run(::send)

    override fun onScanFailed(errorCode: Int) {
      close(cause = ScanFailedException(errorCode))
    }
  }

  startScan(callback)
  awaitClose { stopScan(callback) }
}
