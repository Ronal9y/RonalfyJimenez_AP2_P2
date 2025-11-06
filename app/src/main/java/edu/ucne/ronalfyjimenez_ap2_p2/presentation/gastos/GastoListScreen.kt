package edu.ucne.ronalfyjimenez_ap2_p2.presentation.gastos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.ronalfyjimenez_ap2_p2.domain.model.Gasto
import edu.ucne.ronalfyjimenez_ap2_p2.presentation.gastos.GastoViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastoListScreen(
    onAdd: () -> Unit,
    onEdit: (Int) -> Unit,
    viewModel: GastoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.onEvent(GastoEvent.LoadGastos) }

    // Modal-sheet
    if (state.showDialog) GastoModalSheet(state, viewModel::onEvent)

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Gastos") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo")
            }
        }
    ) { pv ->
        Box(Modifier.fillMaxSize().padding(pv)) {
            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                state.errorMessage != null -> Text(
                    text = state.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
                state.gastos.isEmpty() -> Text(
                    "No hay gastos registrados",
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(state.gastos) { g ->
                        GastoItem(
                            gasto = g,
                            onEdit = { viewModel.onEvent(GastoEvent.EditGasto(g.gastoId)) },
                            onDelete = { viewModel.onEvent(GastoEvent.EditGasto(g.gastoId)) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GastoItem(
    gasto: Gasto,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val fmt = NumberFormat.getCurrencyInstance(Locale("es", "DO"))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Text(gasto.suplidor, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("NCF: ${gasto.ncf}")
            Text("Fecha: ${gasto.fecha.substring(0, 10)}")
            Text("Monto: ${fmt.format(gasto.monto)}")
            Text("ITBIS: ${fmt.format(gasto.itbis)}")

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = onEdit) { Text("Editar") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = onDelete) { Text("Eliminar") }
            }
        }
    }
}