package edu.ucne.ronalfyjimenez_ap2_p2.presentation.gastos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.ronalfyjimenez_ap2_p2.data.remote.Resource
import edu.ucne.ronalfyjimenez_ap2_p2.domain.model.Gasto
import edu.ucne.ronalfyjimenez_ap2_p2.domain.usecases.DeleteGastoUseCase
import edu.ucne.ronalfyjimenez_ap2_p2.domain.usecases.GetGastoByIdUseCase
import edu.ucne.ronalfyjimenez_ap2_p2.domain.usecases.GetGastosUseCase
import edu.ucne.ronalfyjimenez_ap2_p2.domain.usecases.PostGastoUseCase
import edu.ucne.ronalfyjimenez_ap2_p2.domain.usecases.PutGastoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GastoViewModel @Inject constructor(
    private val getGastosUseCase: GetGastosUseCase,
    private val getGastoByIdUseCase: GetGastoByIdUseCase,
    private val postGastoUseCase: PostGastoUseCase,
    private val putGastoUseCase: PutGastoUseCase,
    private val deleteGastoUseCase: DeleteGastoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GastoState())
    val state: StateFlow<GastoState> = _state.asStateFlow()

    init {
        onEvent(GastoEvent.LoadGastos)
    }

    fun onEvent(event: GastoEvent) {
        when (event) {
            GastoEvent.LoadGastos -> loadGastos()
            is GastoEvent.DeleteGasto -> deleteGasto(event.id)
            is GastoEvent.EditGasto -> editGasto(event.id)
            is GastoEvent.ShowDialog -> _state.update { it.copy(showDialog = event.show) }
            is GastoEvent.SuplidorChange -> _state.update { it.copy(suplidor = event.suplidor) }
            is GastoEvent.NcfChange -> _state.update { it.copy(ncf = event.ncf) }
            is GastoEvent.ItbisChange -> _state.update { it.copy(itbis = event.itbis) }
            is GastoEvent.MontoChange -> _state.update { it.copy(monto = event.monto) }
            GastoEvent.SaveGasto -> saveGasto()
            GastoEvent.ClearForm -> clearForm()
            GastoEvent.ClearError -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun loadGastos() = viewModelScope.launch {
        getGastosUseCase().collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> _state.update {
                    it.copy(isLoading = false, gastos = res.data ?: emptyList())
                }
                is Resource.Error -> _state.update {
                    it.copy(isLoading = false, errorMessage = res.message)
                }
            }
        }
    }

    private fun deleteGasto(id: Int) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val res = deleteGastoUseCase(id)) {
            is Resource.Success -> {
                _state.update { it.copy(isLoading = false, showDialog = false) }
                loadGastos()
            }
            is Resource.Error -> _state.update {
                it.copy(isLoading = false, errorMessage = res.message)
            }
            else -> Unit
        }
    }

    private fun editGasto(id: Int) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val res = getGastoByIdUseCase(id)) {
            is Resource.Success -> {
                val g = res.data
                _state.update {
                    it.copy(
                        isLoading = false,
                        gastoSeleccionado = g,
                        suplidor = g?.suplidor ?: "",
                        ncf = g?.ncf ?: "",
                        itbis = g?.itbis?.toString() ?: "",
                        monto = g?.monto?.toString() ?: "",
                        isEditing = true,
                        showDialog = true
                    )
                }
            }
            is Resource.Error -> _state.update {
                it.copy(isLoading = false, errorMessage = res.message)
            }
            else -> Unit
        }
    }

    private fun saveGasto() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        // Fecha
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val fecha = if (state.value.isEditing)
            state.value.gastoSeleccionado?.fecha ?: dateFormat.format(Date())
        else
            dateFormat.format(Date())

        // Conversión segura
        val itbisVal = state.value.itbis.toDoubleOrNull() ?: 0.0
        val montoVal = state.value.monto.toDoubleOrNull() ?: 0.0

        val gasto = Gasto(
            gastoId = state.value.gastoSeleccionado?.gastoId ?: 0,
            fecha = fecha,
            suplidor = state.value.suplidor.trim(),
            ncf = state.value.ncf.trim(),
            itbis = itbisVal,
            monto = montoVal
        )

        val res = if (state.value.isEditing) putGastoUseCase(gasto.gastoId, gasto)
        else postGastoUseCase(gasto)

        when (res) {
            is Resource.Success -> {
                _state.update { it.copy(isLoading = false, showDialog = false) }
                clearForm()
                loadGastos()
            }
            is Resource.Error -> _state.update {
                it.copy(isLoading = false, errorMessage = res.message)
            }
            else -> Unit
        }
    }

    private fun clearForm() {
        _state.update {
            it.copy(
                gastoSeleccionado = null,
                suplidor = "",
                ncf = "",
                itbis = "",
                monto = "",
                isEditing = false
            )
        }
    }
}