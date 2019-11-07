package com.flypika.android.assignment.core.bluetooth.gatt

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService

fun BluetoothGatt.allCharacteristics(): Sequence<BluetoothGattCharacteristic> {
  return services
    .asSequence()
    .map(BluetoothGattService::getCharacteristics)
    .flatten()
}