package com.flypika.android.assignment.core.activity

import android.content.Intent

interface ActivityResultCallback {
  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}

typealias ActivityCallbacks = Set<@JvmSuppressWildcards  ActivityResultCallback>