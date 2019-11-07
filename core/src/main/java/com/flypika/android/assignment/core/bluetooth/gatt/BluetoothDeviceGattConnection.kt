@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.flypika.android.assignment.core.bluetooth.gatt

import android.bluetooth.*
import android.content.Context
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

data class ConnectedBluetoothGattState(
  val gatt: BluetoothGatt,
  val connected: Boolean
)

fun Context.connectGattForCharacteristics(device: BluetoothDevice): Flow<ConnectedBluetoothGattState> = callbackFlow {
  val ctx = this@connectGattForCharacteristics
  var connected = false

  fun update(gatt: BluetoothGatt) {
    if (!isClosedForSend) {
      offer(ConnectedBluetoothGattState(gatt, connected))
    }
  }

  val callback = object : BluetoothGattCallback() {

    override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
      connected = when (newState) {
        BluetoothProfile.STATE_CONNECTED    -> true.also { gatt.discoverServices() }
        BluetoothProfile.STATE_DISCONNECTED -> false
        else                                -> false
      }
      update(gatt)
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
      gatt
        .allCharacteristics()
        .forEach { characteristic -> gatt.setCharacteristicNotification(characteristic, true) }
      update(gatt)
    }

    override fun onDescriptorRead(gatt: BluetoothGatt, descriptor: BluetoothGattDescriptor, status: Int) = update(gatt)
    override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) = update(gatt)
    override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) = update(gatt)

    /*
    override fun onMtuChanged(gatt: BluetoothGatt, mtu: Int, status: Int) = Unit
    override fun onPhyRead(gatt: BluetoothGatt, txPhy: Int, rxPhy: Int, status: Int) = Unit
    override fun onPhyUpdate(gatt: BluetoothGatt, txPhy: Int, rxPhy: Int, status: Int) = Unit
    override fun onReadRemoteRssi(gatt: BluetoothGatt?, rssi: Int, status: Int) = Unit

    override fun onDescriptorWrite(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) = Unit
    override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) = Unit
    override fun onReliableWriteCompleted(gatt: BluetoothGatt?, status: Int) = Unit
    */
  }

  val gatt = device.connectGatt(ctx, true, callback)

  update(gatt)

  awaitClose(gatt::disconnect)
}