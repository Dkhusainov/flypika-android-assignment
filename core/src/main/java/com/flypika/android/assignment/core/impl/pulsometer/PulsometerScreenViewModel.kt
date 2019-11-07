package com.flypika.android.assignment.core.impl.pulsometer

import android.bluetooth.BluetoothGatt
import android.bluetooth.le.ScanResult
import android.content.Context
import androidx.lifecycle.ViewModel
import com.flypika.android.assignment.core.bluetooth.BLEScanner
import com.flypika.android.assignment.core.bluetooth.gatt.resolved.ResolvedGattCharacteristic
import com.flypika.android.assignment.core.bluetooth.gatt.allCharacteristics
import com.flypika.android.assignment.core.bluetooth.gatt.connectGattForCharacteristics
import com.flypika.android.assignment.core.impl.pulsometer.Pulsometer.State.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@Suppress("EXPERIMENTAL_API_USAGE")
class PulsometerScreenViewModel @Inject constructor(
  private val ctx: Context,
  private val bleScanner: BLEScanner
) : ViewModel() {

  private var last: Pulsometer = Pulsometer.DISCONNECTED

  /**
   * Requires bluetooth and coerce location
   */
  suspend fun connectToPulsometer(): Flow<Pulsometer> {
    val firstSuitableGattDevice: Flow<ScanResult> = bleScanner
      .scanLe()
      .filter(::isSuitableGattDevice)
      .take(1)

    val pulsometerUpdates: Flow<Pulsometer> = firstSuitableGattDevice
      .flatMapConcat { scan -> ctx.connectGattForCharacteristics(scan.device) }
      .map { (gatt, connected) -> mapGatt(gatt, connected) }
      .onEach { gatt -> last = gatt }

    return flowOf(last)
      .onCompletion { emitAll(pulsometerUpdates) }
      .flowOn(Dispatchers.Default)
  }

  private suspend fun isSuitableGattDevice(scanResult: ScanResult): Boolean {
    val connectable = scanResult.isConnectable
    val services = scanResult.scanRecord?.serviceUuids.orEmpty()
    val name = scanResult.device.name
//    val local = name == "A6"
    Timber.d("connectable=$connectable, name=$name, scan=$scanResult")
    return connectable && services.isNotEmpty()// && local
  }

  private fun mapGatt(gatt: BluetoothGatt, connected: Boolean): Pulsometer {
    val name = gatt.device.name ?: "?"

    val characteristics = gatt
      .allCharacteristics()
      .mapTo(mutableListOf(), ResolvedGattCharacteristic.Companion::resolve)

    return Pulsometer(
      name = name,
      state =  if (connected) CONNECTED else CONNECTING,
      data = Pulsometer.Data(
        characteristics = characteristics
      )
    )
  }
}
