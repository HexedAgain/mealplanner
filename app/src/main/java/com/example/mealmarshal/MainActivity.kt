package com.example.mealmarshal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.navigation.BottomNavigationItem
import com.example.mealmarshal.ui.theme.MealMarshalTheme
import com.example.recipes.nav.RecipesNavScreen
import com.example.settings.nav.SettingsNavScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealMarshalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Greeting("Android")
                        BottomNav()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav() {
    val navItems: List<BottomNavigationItem> = listOf(
        RecipesNavScreen(),
        SettingsNavScreen()
    )
    Scaffold(
        bottomBar = {
            BottomAppBar() {
                navItems.forEach { item ->
                    NavigationBarItem(
                        selected = false,
                        onClick = { /*TODO*/ },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.navigationIconRes),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = item.navigationIconText)
                            )
                        }
                    )
                }
            }
        }
    ) { padding ->
        Text(text = "test", modifier = Modifier.padding(padding))
    }
//    Scaffold() { padding ->
//        BottomNavigation(
//            modifier = Modifier.padding(padding),
//        ) {
////            NavigationBarItem(
////                selected = false,
////                onClick = { /*TODO*/ },
////                icon = { Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null) }
////            )
////            NavigationBarItem(
////                selected = true,
////                onClick = { /*TODO*/ },
////                icon = { Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null) }
////            )
//        }
//    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MealMarshalTheme {
        Greeting("Android")
    }
}