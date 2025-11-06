package edu.ucne.ronalfyjimenez_ap2_p2.presentation.gastos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.ronalfyjimenez_ap2_p2.presentation.gastos.GastoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastoScreen(
    gastoId: Int?,
    onBack: () -> Unit,
    onGoToList: () -> Unit,
    viewModel: GastoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isEditing) "Editar Gasto" else "Nuevo Gasto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = onGoToList) {
                        Icon(Icons.Default.List, contentDescription = "Ver listado")
                    }
                }
            )
        }
    ) { pv ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(pv)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.suplidor,
                onValueChange = { viewModel.onEvent(GastoEvent.SuplidorChange(it)) },
                label = { Text("Suplidor") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.errorMessage != null
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.ncf,
                onValueChange = { viewModel.onEvent(GastoEvent.NcfChange(it)) },
                label = { Text("NCF") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.errorMessage != null
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.monto,
                onValueChange = { viewModel.onEvent(GastoEvent.MontoChange(it)) },
                label = { Text("Monto") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                isError = state.errorMessage != null
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.itbis,
                onValueChange = { viewModel.onEvent(GastoEvent.ItbisChange(it)) },
                label = { Text("ITBIS") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                isError = state.errorMessage != null
            )

            state.errorMessage?.let {
                Spacer(Modifier.height(12.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onEvent(GastoEvent.SaveGasto) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = state.suplidor.isNotBlank() &&
                        state.ncf.isNotBlank() &&
                        state.monto.isNotBlank() &&
                        state.itbis.isNotBlank() &&
                        !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Guardar")
                }
            }
        }
    }
}