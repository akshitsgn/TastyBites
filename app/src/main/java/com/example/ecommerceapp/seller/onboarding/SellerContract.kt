package com.example.ecommerceapp.seller.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LegalContractScreen() {
    val terms = listOf(
        "The app reserves the right to verify the authenticity of the provided documents.",
        "Any false information or fraudulent activity may result in the suspension of services.",
        "You agree to comply with all applicable laws and regulations for operating a restaurant business.",
        "The app is authorized to use your restaurant details and menu for promotional purposes.",
        "Fees and commissions will be deducted as per the agreed terms."
    )

    val checkedStates = remember { mutableStateListOf(*Array(terms.size) { false }) }
    val allChecked = checkedStates.all { it } // Check if all checkboxes are checked

    Column(modifier = Modifier.fillMaxSize()) {
        // Header Section
        Row(
            modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
        ) {
            IconButton(onClick = {
                // navController.popBackStack("OnBoardingStepper", false)
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Partner Contract",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color(0xFFF6F6F6)) // Light gray background
        ) {

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Legal Contract Section
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Legal Contract",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.CenterHorizontally) // Align text to the center
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Checkpoints with Tick and Color Change
                        terms.forEachIndexed { index, term ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                // Small Checkbox with Tick
                                Box(
                                    modifier = Modifier
                                        .size(20.dp) // Small box size
                                        .border(
                                            1.dp,
                                            if (checkedStates[index]) Color(0xFFFF5722) else Color.Gray,
                                            RoundedCornerShape(2.dp)
                                        )
                                        .background(
                                            if (checkedStates[index]) Color(0xFFFF5722) else Color.Transparent
                                        )
                                        .clickable {
                                            checkedStates[index] = !checkedStates[index]
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (checkedStates[index]) {
                                        Text(
                                            text = "✓",
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = term,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Please ensure you have read all the details and understood all terms and conditions.",
                            fontSize = 14.sp,
                            color = Color.Black,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            item{Spacer(modifier = Modifier.height(16.dp))}

            // Accept T&C Button
            item {
                Button(
                    onClick = {
                        // Navigate to the next screen or perform the desired action
                    },
                    enabled = allChecked, // Enable only if all checkboxes are checked
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (allChecked) Color(0xFFFF5722) else Color.Gray // Button color logic
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Accept T&C",
                        color = Color.White
                    )
                }
            }

            // Help Footer
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = "\u002A",
                        fontSize = 18.sp,
                        color = Color.Red.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "If you need any help, check out the FAQs",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

