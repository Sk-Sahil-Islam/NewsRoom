package com.example.newsroom.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun SavedNewsScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Saved News", fontSize = 30.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun SavedNewsScreenPreview() {
    SavedNewsScreen()
}