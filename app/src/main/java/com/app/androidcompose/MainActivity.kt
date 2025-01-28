package com.app.androidcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import leegroup.module.compose.ui.theme.ComposeTheme
import leegroup.module.phototopdf.ui.PhotoToPdfNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                PhotoToPdfNavGraph(navController = rememberNavController())
            }
        }
    }
}
