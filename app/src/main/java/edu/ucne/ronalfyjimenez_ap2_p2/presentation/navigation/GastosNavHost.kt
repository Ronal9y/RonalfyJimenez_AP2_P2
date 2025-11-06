package edu.ucne.ronalfyjimenez_ap2_p2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.ronalfyjimenez_ap2_p2.presentation.gastos.GastoListScreen
import edu.ucne.ronalfyjimenez_ap2_p2.presentation.gastos.GastoScreen

@Composable
fun GastosNavHost(nav: NavHostController) {
    NavHost(
        navController = nav,
        startDestination = Screen.ListaGastos
    ) {
        composable<Screen.ListaGastos> {
            GastoListScreen(
                onAdd = { nav.navigate(Screen.GastoForm()) },
                onEdit = { id -> nav.navigate(Screen.GastoForm(id)) }
            )
        }

        composable<Screen.GastoForm> { backEntry ->
            val args = backEntry.toRoute<Screen.GastoForm>()

            GastoScreen(
                gastoId = args.gastoId,
                onBack = { nav.popBackStack() },
                onGoToList = { nav.popBackStack(Screen.ListaGastos, inclusive = false) }
            )
        }
    }
}