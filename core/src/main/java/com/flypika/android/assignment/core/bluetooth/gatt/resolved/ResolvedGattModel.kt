package com.flypika.android.assignment.core.bluetooth.gatt.resolved

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor

class ResolvedGattCharacteristic(
  val name: String,
  val value: String?,
  val descriptors: List<ResolvedGattDescriptor>
) {
  companion object {
    fun resolve(charactertic: BluetoothGattCharacteristic): ResolvedGattCharacteristic {
      val name = AllGattCharacteristics.lookup(charactertic.uuid, "?")
      val value = charactertic.value?.let { value -> String(value) } ?: "?"
      charactertic.value
      val descriptors = charactertic
        .descriptors
        .orEmpty()
        .map(ResolvedGattDescriptor.Companion::resolve)
      return ResolvedGattCharacteristic(
        name,
        value,
        descriptors
      )
    }
  }

  override fun toString(): String {
    return "$name - $value (${descriptors.joinToString(separator = ", ")})"
  }
}

class ResolvedGattDescriptor(
  val text: String,
  val value: String?
) {
  companion object {
    fun resolve(descriptor: BluetoothGattDescriptor): ResolvedGattDescriptor {
      val name = AllGattDescriptors.lookup(descriptor.uuid, "?")
      val value = descriptor.value?.let { value -> String(value) } ?: "?"
      return ResolvedGattDescriptor(
        name,
        value
      )
    }
  }

  override fun toString(): String {
    return "$value($text)"
  }
}