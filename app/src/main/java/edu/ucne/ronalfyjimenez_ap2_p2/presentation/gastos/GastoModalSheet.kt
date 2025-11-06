package edu.ucne.ronalfyjimenez_ap2_p2.presentation.gastos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.ucne.ronalfyjimenez_ap2_p2.presentation.gastos.GastoState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastoModalSheet(
    state: GastoState,
    onEvent: (GastoEvent) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onEvent(GastoEvent.ShowDialog(false)) },
        title = { Text(if (state.isEditing) "Editar Gasto" else "Nuevo Gasto") },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                OutlinedTextField(
                    value = state.suplidor,
                    onValueChange = { onEvent(GastoEvent.SuplidorChange(it)) },
                    label = { Text("Suplidor") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.ncf,
                    onValueChange = { onEvent(GastoEvent.NcfChange(it)) },
                    label = { Text("NCF") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.monto,
                    onValueChange = { onEvent(GastoEvent.MontoChange(it)) },
                    label = { Text("Monto") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.itbis,
                    onValueChange = { onEvent(GastoEvent.ItbisChange(it)) },
                    label = { Text("ITBIS") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
                state.errorMessage?.let {
                    Spacer(Modifier.height(12.dp))
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        },
        confirmButton = {
            Row(Modifier.fillMaxWidth(), Arrangement.End) {
                TextButton(onClick = { onEvent(GastoEvent.ShowDialog(false)) }) {
                    Text("Cancelar")
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { onEvent(GastoEvent.SaveGasto) },
                    enabled = state.suplidor.isNotBlank() &&
                            state.ncf.isNotBlank() &&
                            state.monto.isNotBlank() &&
                            state.itbis.isNotBlank() &&
                            !state.isLoading
                ) {
                    if (state.isLoading) CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    else Text(if (state.isEditing) "Actualizar" else "Guardar")
                }
            }
        },
        dismissButton = {
            if (state.isEditing && state.gastoSeleccionado != null) {
                TextButton(
                    onClick = { onEvent(GastoEvent.DeleteGasto(state.gastoSeleccionado.gastoId)) }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    )
}