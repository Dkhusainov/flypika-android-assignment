package com.flypika.android.assignment.core.impl.pulsometer

import com.flypika.android.assignment.core.bluetooth.gatt.resolved.ResolvedGattCharacteristic

data class Pulsometer(
  val name: String?,
  val state: State,
  val data: Data
) {

  companion object {
    val DISCONNECTED = Pulsometer(null, State.DISCONNECTED, data = Data(emptyList()))
  }

  enum class State {
    DISCONNECTED,
    CONNECTING,
    CONNECTED
  }

  data class Data(
    val characteristics: List<ResolvedGattCharacteristic>
  )
}