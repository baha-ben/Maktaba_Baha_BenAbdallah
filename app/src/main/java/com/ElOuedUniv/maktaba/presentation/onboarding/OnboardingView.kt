package com.ElOuedUniv.maktaba.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.ElOuedUniv.maktaba.data.local.OnboardingPreferencesManager
import kotlinx.coroutines.launch

@Composable
fun OnboardingView(
    onNavigateToLibrary: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val backgroundGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF6C47FF),
            Color(0xFFA855F7),
            Color(0xFFEC4899)
        ),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        // ── Decorative circles
        Box(
            modifier = Modifier
                .size(280.dp)
                .offset(x = (-80).dp, y = (-80).dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.07f))
        )
        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 60.dp, y = (-120).dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.06f))
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-40).dp, y = 160.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.06f))
        )

        // ── Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .padding(top = 72.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // App icon
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(26.dp))
                        .background(Color.White.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "📚", fontSize = 42.sp)
                }

                Spacer(modifier = Modifier.height(36.dp))

                // App label
                Text(
                    text = "MAKTABA",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 4.sp,
                    color = Color.White.copy(alpha = 0.60f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Headline
                Text(
                    text = "Welcome to\nYour Library",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 38.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Subtitle
                Text(
                    text = "Your personal digital library,\norganized and always with you.",
                    fontSize = 15.sp,
                    color = Color.White.copy(alpha = 0.65f),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Pagination dots
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.White)
                    )
                    repeat(2) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.35f))
                        )
                    }
                }
            }

            // Bottom section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            OnboardingPreferencesManager.setHasCompletedOnboarding(context, true)
                            viewModel.onCompleteOnboarding()
                            onNavigateToLibrary()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF6C47FF)
                    )
                ) {
                    Text(
                        text = "Get Started",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.3.sp
                    )
                }

                // Credit line
                Text(
                    text = "Crafted with care by Baha Taki Eddine Ben Abdallah",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.28f),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.2.sp
                )
            }
        }
    }
}