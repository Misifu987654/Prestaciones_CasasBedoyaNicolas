package com.example.prestaciones.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.prestaciones.model.PrestacionesResult

class PrestacionesViewModel : ViewModel() {
    // --- CONSTANTES LEGALES ---
    companion object {
        const val SALARIO_MINIMO: Double = 1_300_000.0
        const val AUXILIO_TRANSPORTE: Double = 162_000.0
        const val DIAS_ANIO: Double = 360.0
        const val INTERES_CESANTIAS_RATE: Double = 0.12
    }

    // --- ESTADOS REACTIVOS ---
    var salarioInput by mutableStateOf("")
        private set
    var diasTrabajadosInput by mutableStateOf("")
        private set
    var recibeAuxilioTransporte by mutableStateOf(false)
        private set
    var prestacionResultado by mutableStateOf(PrestacionesResult())
        private set
    var mostrarError by mutableStateOf(false)
        private set
    var mensajeError by mutableStateOf("")
        private set

    // --- MANEJO DE INPUT ---
    fun onSalarioChange(nuevoSalario: String) {
        salarioInput = nuevoSalario.filter { it.isDigit() || it == '.' }
        mostrarError = false
    }

    fun onDiasTrabajadosChange(nuevosDias: String) {
        diasTrabajadosInput = nuevosDias.filter { it.isDigit() }
        mostrarError = false
    }

    fun onAuxilioTransporteToggle(value: Boolean) {
        recibeAuxilioTransporte = value
    }

    // --- LÓGICA DE NEGOCIO ---
    private fun validarEntradas(): Boolean {
        if (salarioInput.isEmpty() || diasTrabajadosInput.isEmpty()) {
            mostrarError = true
            mensajeError = "Por favor, ingrese Salario y Días trabajados."
            return false
        }
        val salario = salarioInput.toDoubleOrNull()
        val diasTrabajados = diasTrabajadosInput.toIntOrNull()
        if (salario == null || diasTrabajados == null || salario <= 0 || diasTrabajados <= 0) {
            mostrarError = true
            mensajeError = "Salario y Días deben ser números válidos y positivos."
            return false
        }
        if (diasTrabajados > DIAS_ANIO) {
            mostrarError = true
            mensajeError = "El máximo de días trabajados es ${DIAS_ANIO.toInt()}."
            return false
        }
        mostrarError = false
        return true
    }

    fun calcularPrestaciones() {
        if (!validarEntradas()) return

        val salarioBase = salarioInput.toDoubleOrNull() ?: return
        val diasTrabajados = diasTrabajadosInput.toDoubleOrNull() ?: return

        // 1. Determinar Salario Base de Liquidación (SBL)
        val aplicaAuxilio = salarioBase <= (2 * SALARIO_MINIMO) && recibeAuxilioTransporte
        val auxilioLiquidacion = if (aplicaAuxilio) AUXILIO_TRANSPORTE else 0.0
        val sbl = salarioBase + auxilioLiquidacion

        // 2. CÁLCULO DE CONCEPTOS
        val prima = (sbl * diasTrabajados) / DIAS_ANIO
        val cesantias = (sbl * diasTrabajados) / DIAS_ANIO
        val interesesCesantias = (cesantias * diasTrabajados * INTERES_CESANTIAS_RATE) / DIAS_ANIO
        val vacaciones = (salarioBase * diasTrabajados) / 720.0

        // 3. ACTUALIZAR ESTADO
        val total = prima + cesantias + interesesCesantias + vacaciones
        prestacionResultado = PrestacionesResult(
            prima = prima,
            cesantias = cesantias,
            interesesCesantias = interesesCesantias,
            vacaciones = vacaciones,
            total = total
        )
    }

    fun limpiarCampos() {
        salarioInput = ""
        diasTrabajadosInput = ""
        recibeAuxilioTransporte = false
        prestacionResultado = PrestacionesResult()
        mostrarError = false
        mensajeError = ""
    }
}