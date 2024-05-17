package com.eep.aviones100

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eep.aviones100.AvionesViewModel
import com.eep.aviones100.ui.theme.Aviones100Theme
import com.example.myapppersonas.SecondScreen
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {
    private val viewModel: AvionesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Aviones100Theme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = "main_screen") {
                        composable("main_screen") { AvionesListScreen(navController, viewModel) }
                        composable("second_screen") { SecondScreen(coroutineScope = rememberCoroutineScope()) }
                    }
                }
            }
        }
    }
}

@Composable
fun AvionesListScreen(navController: NavHostController, viewModel: AvionesViewModel) {
    val avionesList = viewModel.avionesList

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { navController.navigate("second_screen") }) {
            Text(text = "Ir a la segunda pantalla")
        }

        LazyColumn(modifier = Modifier.padding(vertical = 8.dp)) {
            items(avionesList) { avion ->
                AvionItem(avion)
            }
        }
    }
}

@Composable
fun AvionItem(avion: Aviones) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "ID: ${avion.id}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "AE: ${avion.ae}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Apellido: ${avion.apellido}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Nombre: ${avion.name}", style = MaterialTheme.typography.bodyMedium)
    }
}

