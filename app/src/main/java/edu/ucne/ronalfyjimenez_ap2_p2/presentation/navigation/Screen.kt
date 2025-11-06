package edu.ucne.ronalfyjimenez_ap2_p2.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object ListaGastos : Screen()

    @Serializable
    data class GastoForm(
        val gastoId: Int? = null
    ) : Screen()
}