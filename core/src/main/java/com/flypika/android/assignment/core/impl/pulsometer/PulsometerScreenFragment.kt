@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.flypika.android.assignment.core.impl.pulsometer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.flypika.android.assignment.core.R
import com.flypika.android.assignment.core.bluetooth.BLEStateManager
import com.flypika.android.assignment.core.di.ui.di
import com.flypika.android.assignment.core.viewmodel.ViewModelFragment
import com.flypika.android.assignment.core.view.visible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PulsometerScreenFragment : ViewModelFragment() {

  private val viewModel: PulsometerScreenViewModel by viewModel()

  @Inject lateinit var bluetoothStateManager: BLEStateManager

  private val data:       TextView get() = requireView().findViewById(R.id.screen_pulsometer_data)
  private val connection: TextView get() = requireView().findViewById(R.id.screen_pulsometer_connection)
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.screen_pulosmeter, container, false)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    act.di().inject(this)
    lifecycleScope.launchWhenStarted { monitor() }
  }

  private suspend fun CoroutineScope.monitor() {
    if (!bluetoothStateManager.requestBLE()) {
      Toast.makeText(act, R.string.screen_pulsometer_bluetooth_required, Toast.LENGTH_LONG).show()
      return
    }

    try {
      viewModel
        .connectToPulsometer()
        .onEach { state -> updateState(state) }
        .launchIn(this)
    } catch (e: Throwable) {
      updateStateError(e)
    }
  }

  private fun updateState(gatt: Pulsometer) {
    val (name, state, gattData) = gatt

    connection.text = when(state) {
      Pulsometer.State.DISCONNECTED -> act.getString(R.string.screen_pulsometer_search)
      Pulsometer.State.CONNECTING   -> act.getString(R.string.screen_pulsometer_connecting)
      Pulsometer.State.CONNECTED    -> act.getString(R.string.screen_pulsometer_connected)
    }

    data.visible = state != Pulsometer.State.DISCONNECTED

    data.text = """
     |Устройство - ${name ?: "Не подключено"}
     |---------------
     |Характеристики:
     |${gattData.characteristics.joinToString(separator = "\n")}
    """.trimMargin()
  }

  private fun updateStateError(e: Throwable) {
    data.visible = false
    connection.text = act.getString(R.string.screen_pulsometer_error, e.message)
  }
}