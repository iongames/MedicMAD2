package com.example.medicmad2.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.medicmad2.ui.theme.MedicMAD2Theme
import com.example.medicmad2.ui.theme.primaryColor
import com.example.medicmad2.ui.theme.selectedStrokeColor
import com.example.medicmad2.view.screens.HomeScreen
import com.example.medicmad2.view.screens.ProfileScreen
import com.example.medicmad2.view.screens.ResultsScreen
import com.example.medicmad2.view.screens.SupportScreen
import com.example.medicmad2.R
import com.example.medicmad2.viewmodel.HomeViewModel
import com.example.medicmad2.viewmodel.LoginViewModel

/*
Описание: Класс главного экрана с нижним меню
Дата создания: 09.03.2023 8:50
Автор: Георгий Хасанов
*/
class HomeActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicMAD2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeContent()
                }
            }
        }
    }

    /*
    Описание: Контент главного экрана с нижним меню
    Дата создания: 09.03.2023 8:50
    Автор: Георгий Хасанов
    */
    @Composable
    fun HomeContent() {
        val sharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)



        navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        Scaffold(
            bottomBar = {
                BottomNavigation(
                    backgroundColor = Color.White,
                    modifier = Modifier.drawBehind {
                        drawLine(
                            Color(0xFFA0A0A0).copy(0.3f),
                            Offset(0f, 0f),
                            Offset(size.width, 0f),
                            0.5f
                        )
                    }
                ) {
                    BottomNavigationItem(
                        selected = navBackStackEntry?.destination?.route == "home",
                        icon = { Icon(painter = painterResource(id = R.drawable.ic_analysis), contentDescription = "") },
                        label = { Text("Анализы", fontSize = 12.sp) },
                        onClick = {
                            navController.navigate("home")
                        },
                        selectedContentColor = primaryColor,
                        unselectedContentColor = selectedStrokeColor
                    )
                    BottomNavigationItem(
                        selected = navBackStackEntry?.destination?.route == "results",
                        icon = { Icon(painter = painterResource(id = R.drawable.ic_results), contentDescription = "") },
                        label = { Text("Результаты", fontSize = 12.sp) },
                        onClick = {
                            navController.navigate("results")
                        },
                        selectedContentColor = primaryColor,
                        unselectedContentColor = selectedStrokeColor
                    )
                    BottomNavigationItem(
                        selected = navBackStackEntry?.destination?.route == "support",
                        icon = { Icon(painter = painterResource(id = R.drawable.ic_support), contentDescription = "") },
                        label = { Text("Поддержка", fontSize = 12.sp) },
                        onClick = {
                            navController.navigate("support")
                        },
                        selectedContentColor = primaryColor,
                        unselectedContentColor = selectedStrokeColor
                    )
                    BottomNavigationItem(
                        selected = navBackStackEntry?.destination?.route == "profile",
                        icon = { Icon(painter = painterResource(id = R.drawable.ic_user), contentDescription = "") },
                        label = { Text("Профиль", fontSize = 12.sp) },
                        onClick = {
                            navController.navigate("profile")
                        },
                        selectedContentColor = primaryColor,
                        unselectedContentColor = selectedStrokeColor
                    )
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navHostController = navController)
            }
        }
    }

    /*
    Описание: Функция навигации нижнего меню с переходами на гдругие экраны
    Дата создания: 09.03.2023 8:50
    Автор: Георгий Хасанов
    */
    @Composable
    fun Navigation(navHostController: NavHostController) {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        NavHost(
            navController = navHostController,
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen(homeViewModel)
            }
            composable("results") {
                ResultsScreen()
            }
            composable("support") {
                SupportScreen()
            }
            composable("profile") {
                ProfileScreen(loginViewModel, imageResultLauncher, videoResultLauncher)
            }
        }
    }

    val imageResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

            val data: Intent? = result.data

            loginViewModel.selectedImage.value = data!!.extras!!.get("data") as Bitmap
        }
    }

    val videoResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

            val data: Intent? = result.data

            loginViewModel.selectedVideo.value = data!!.data.toString()
        }
    }
}