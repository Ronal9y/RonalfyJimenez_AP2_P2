package edu.ucne.ronalfyjimenez_ap2_p2.presentation.gastos

import edu.ucne.ronalfyjimenez_ap2_p2.domain.model.Gasto

data class GastoState(
    val gastos: List<Gasto> = emptyList(),
    val gastoSeleccionado: Gasto? = null,
    val suplidor: String = "",
    val ncf: String = "",
    val itbis: String = "",
    val monto: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val showDialog: Boolean = false,
    val isEditing: Boolean = false
)