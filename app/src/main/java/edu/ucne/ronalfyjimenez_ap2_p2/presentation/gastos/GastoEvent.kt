package edu.ucne.ronalfyjimenez_ap2_p2.presentation.gastos

sealed interface GastoEvent {
    object LoadGastos : GastoEvent
    data class DeleteGasto(val id: Int) : GastoEvent
    data class EditGasto(val id: Int) : GastoEvent
    data class ShowDialog(val show: Boolean) : GastoEvent
    data class SuplidorChange(val suplidor: String) : GastoEvent
    data class NcfChange(val ncf: String) : GastoEvent
    data class ItbisChange(val itbis: String) : GastoEvent
    data class MontoChange(val monto: String) : GastoEvent
    object SaveGasto : GastoEvent
    object ClearForm : GastoEvent
    object ClearError : GastoEvent

}