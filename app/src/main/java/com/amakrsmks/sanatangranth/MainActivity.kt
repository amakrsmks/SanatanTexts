package com.amakrsmks.sanatangranth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.amakrsmks.sanatangranth.screens.ScriptureScreen
import com.amakrsmks.sanatangranth.ui.theme.SanatanGranthTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SanatanGranthTheme {
                ScriptureScreen()
            }
        }
    }
}