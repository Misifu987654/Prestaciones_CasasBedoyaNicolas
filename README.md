Prestaciones - Calculadora de Prestaciones Sociales (Ejercicio 2)
Autor: Nicolás Casas Bedoya

Descripción del Proyecto

Aplicación móvil desarrollada en Kotlin y Jetpack Compose que permite a los usuarios **calcular sus prestaciones sociales** (Prima de Servicios, Cesantías, Intereses sobre Cesantías y Vacaciones) basadas en el salario mensual y los días trabajados en Colombia.

La aplicación sigue estrictamente los requisitos académicos del patrón de diseño **MVVM** (Model-View-ViewModel).

Arquitectura MVVM y Stack Tecnológico

Este proyecto está organizado bajo la arquitectura MVVM con la siguiente estructura de paquetes:

* **`model`**: Contiene la estructura de datos inmutable (`data class`) para almacenar los resultados del cálculo: `PrestacionesResult.kt`.
* **`viewmodel`**: Contiene la lógica de negocio, manejo de estados (`MutableStateOf`), validaciones y funciones de cálculo (`calcularPrestaciones`). El archivo principal es `PrestacionesViewModel.kt`.
* **`ui`**: Contiene la interfaz de usuario desarrollada con Jetpack Compose. Consume los estados reactivos del ViewModel y presenta los resultados. El archivo principal es `PrestacionesScreen.kt`.

| Componente | Archivo Principal | Función |
| :--- | :--- | :--- |
| **Model** | `PrestacionesResult.kt` | Estructura de datos inmutable. |
| **ViewModel** | `PrestacionesViewModel.kt` | Contiene la lógica de cálculo y el manejo del estado (reglas colombianas). |
| **View** | `PrestacionesScreen.kt` | Interfaz de usuario interactiva construida con Jetpack Compose. |

Funcionalidades Implementadas

La calculadora realiza los siguientes cálculos basados en el salario base de liquidación (SBL) y los días trabajados:

1.  **Salario Base de Liquidación (SBL):** Incluye el auxilio de transporte solo si el salario es menor o igual a dos (2) Salarios Mínimos Legales Vigentes (SMLV).
2.  **Prima de Servicios:** (SBL * Días Trabajados) / 360
3.  **Cesantías:** (SBL * Días Trabajados) / 360
4.  **Intereses sobre Cesantías:** (Cesantías * Días Trabajados * 0.12) / 360
5.  **Vacaciones:** (Salario **sin** Auxilio * Días Trabajados) / 720

Requisitos del Sistema

* **Android Studio:** Jellyfish o superior.
* **Lenguaje:** Kotlin.
* **Android SDK:** Mínimo 24 (Nougat), Target 34 o 36.
* **Gradle:** Kotlin DSL.

Guía de Instalación y Ejecución

Para ejecutar el proyecto, sigue estos pasos:

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/Misifu987654/Prestaciones_CasasBedoyaNicolas.git](https://github.com/Misifu987654/Prestaciones_CasasBedoyaNicolas.git)
    ```
2.  **Abrir en Android Studio:**
    * En Android Studio, selecciona **File > Open** y navega a la carpeta clonada.
3.  **Sincronizar Gradle:**
    * Espera a que Gradle sincronice automáticamente o haz clic en **Sync Now**.
4.  **Ejecutar:**
    * Ejecuta la aplicación en un emulador o dispositivo físico haciendo clic en el botón **Run.
  
<img width="1362" height="767" alt="image" src="https://github.com/user-attachments/assets/5bdda9b2-f904-4f41-95b4-3e7fd06059d7" />
