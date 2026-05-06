package com.ElOuedUniv.maktaba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ElOuedUniv.maktaba.data.local.OnboardingPreferencesManager
import com.ElOuedUniv.maktaba.presentation.navigation.NavGraph
import com.ElOuedUniv.maktaba.presentation.navigation.Screen
import com.ElOuedUniv.maktaba.presentation.theme.MaktabaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val hasCompletedOnboarding by OnboardingPreferencesManager
                .getHasCompletedOnboardingFlow(applicationContext)
                .collectAsState(initial = false)

            MaktabaTheme {
                NavGraph(
                    startDestination = if (hasCompletedOnboarding) {
                        Screen.BookList.route
                    } else {
                        Screen.Onboarding.route
                    }
                )
            }
        }
    }
}