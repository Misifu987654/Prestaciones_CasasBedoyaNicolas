package com.example.prestaciones.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prestaciones.viewmodel.PrestacionesViewModel
import java.text.NumberFormat
import java.util.Locale

// Objeto para formatear moneda colombiana (COP)
val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CO"))

@Composable
fun PrestacionesScreen(viewModel: PrestacionesViewModel = viewModel()) {
    // Lectura de estados reactivos
    val salarioInput = viewModel.salarioInput
    val diasInput = viewModel.diasTrabajadosInput
    val recibeAuxilio = viewModel.recibeAuxilioTransporte
    val resultados = viewModel.prestacionResultado
    val mostrarError = viewModel.mostrarError
    val mensajeError = viewModel.mensajeError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "CALCULADORA DE PRESTACIONES SOCIALES",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Campos de Entrada
        OutlinedTextField(
            value = salarioInput,
            onValueChange = viewModel::onSalarioChange,
            label = { Text("Salario Mensual (\$)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = diasInput,
            onValueChange = viewModel::onDiasTrabajadosChange,
            label = { Text("Días Trabajados (máx 360)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        // Switch Auxilio de Transporte
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Recibe Auxilio de Transporte", style = MaterialTheme.typography.bodyLarge)
            Switch(checked = recibeAuxilio, onCheckedChange = viewModel::onAuxilioTransporteToggle)
        }
        Spacer(Modifier.height(24.dp))

        // Botones
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = viewModel::calcularPrestaciones, modifier = Modifier.weight(1f)) {
                Text("CALCULAR")
            }
            Spacer(Modifier.width(16.dp))
            OutlinedButton(onClick = viewModel::limpiarCampos, modifier = Modifier.weight(1f)) {
                Text("LIMPIAR")
            }
        }
        Spacer(Modifier.height(24.dp))

        // Errores
        if (mostrarError) {
            Text(text = mensajeError, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 16.dp))
        }

        // Resultados
        if (resultados.total > 0.0) {
            Text(text = "RESULTADOS", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            PrestacionRow(label = "Prima de Servicios:", value = formatter.format(resultados.prima))
            PrestacionRow(label = "Cesantías:", value = formatter.format(resultados.cesantias))
            PrestacionRow(label = "Intereses s/Cesantías:", value = formatter.format(resultados.interesesCesantias))
            PrestacionRow(label = "Vacaciones:", value = formatter.format(resultados.vacaciones))
            Spacer(Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("TOTAL PRESTACIONES:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(formatter.format(resultados.total), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold)
                }
            }
        }
    }
}

@Composable
fun PrestacionRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Text(value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
    }
}