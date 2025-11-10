package com.example.huertohogar.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.huertohogar.navigation.Screen
import com.example.huertohogar.repository.HuertoHogarViewModel
import com.example.huertohogar.ui.theme.DarkGrey
import com.example.huertohogar.ui.theme.EmeraldGreen
import com.example.huertohogar.ui.theme.MustardYellow
import kotlinx.coroutines.launch

@Composable
fun MainScaffold(
    navController: NavController,
    screen: Screen,
    viewModel: HuertoHogarViewModel,
    content: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val cartItems by viewModel.cart.collectAsState()

    // Determina si se debe mostrar el ícono de "hacia atrás"
    val canNavigateBack = navController.previousBackStackEntry != null
    val navItems = listOf(Screen.Products, Screen.Cart, Screen.Profile)
    val isTopLevelDestination = navItems.any { it.route == navController.currentDestination?.route }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(screen.title, style = MaterialTheme.typography.h6.copy(color = Color.White)) },
                navigationIcon = {
                    if (canNavigateBack && !isTopLevelDestination) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                        }
                    } else {
                        IconButton(onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menú", tint = Color.White)
                        }
                    }
                },
                actions = {
                    // Acciones en Appbar: Botón de Carrito
                    BadgedBox(
                        badge = {
                            if (cartItems.isNotEmpty()) {
                                Badge(backgroundColor = MustardYellow) {
                                    Text(cartItems.size.toString(), color = DarkGrey)
                                }
                            }
                        },
                        modifier = Modifier.clickable { navController.navigate(Screen.Cart.route) }
                            .padding(horizontal = 8.dp)
                    ) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito", tint = Color.White)
                    }
                },
                backgroundColor = EmeraldGreen,
                contentColor = Color.White,
                modifier = Modifier.statusBarsPadding()
            )
        },
        bottomBar = {
            BottomNavigation(backgroundColor = EmeraldGreen, contentColor = Color.White) {
                navItems.forEach { item ->
                    BottomNavigationItem(
                        icon = { Icon(item.icon!!, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = navController.currentDestination?.route == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        selectedContentColor = MustardYellow,
                        unselectedContentColor = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        },
        drawerContent = {
            DrawerContent(navController = navController, viewModel = viewModel)
        },
        drawerShape = MaterialTheme.shapes.large,
        drawerBackgroundColor = com.example.huertohogar.ui.theme.SoftWhite,
        drawerContentColor = DarkGrey,
        content = content
    )
}