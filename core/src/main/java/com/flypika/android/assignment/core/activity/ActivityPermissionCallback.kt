package com.flypika.android.assignment.core.activity

interface ActivityPermissionCallback {
  fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
}

typealias ActivityPermissionCallbacks = Set<@JvmSuppressWildcards ActivityPermissionCallback>