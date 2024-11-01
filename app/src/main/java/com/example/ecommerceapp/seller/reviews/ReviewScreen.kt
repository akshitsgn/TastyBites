package com.example.ecommerceapp.seller.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomerFeedbackScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
            // Bck button and title
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /* Handle back navigation */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Customer Feedback",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Overall rating
            Text(
                text = "Overall Rating",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = "3.9",
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(4) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color.Yellow
                    )
                }
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = Color.Gray
                )
            }

            Text(
                text = "Based on 20 reviews",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )


        Spacer(modifier = Modifier.height(16.dp))

        // Ratings breakdown
        RatingBar(label = "Excellent", rating = 0.8f)
        RatingBar(label = "Good", rating = 0.6f)
        RatingBar(label = "Average", rating = 0.4f)
        RatingBar(label = "Poor", rating = 0.2f)

        Spacer(modifier = Modifier.height(16.dp))

        // Reviews
        ReviewItem(
            name = "Martin Luather",
            daysAgo = "2 days ago",
            rating = 4,
            review = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        )
        Spacer(modifier = Modifier.height(12.dp))
        ReviewItem(
            name = "Johan Smith Jeo",
            daysAgo = "3 days ago",
            rating = 3,
            review = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s."
        )

    }
}

@Composable
fun RatingBar(label: String, rating: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.width(80.dp)
        )
        LinearProgressIndicator(
            progress = rating,
            color = Color(0xFF4CAF50), // Green color
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun ReviewItem(name: String, daysAgo: String, rating: Int, review: String) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Placeholder for user image
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = name, style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = daysAgo, style = MaterialTheme.typography.caption)
            }

            Row {
                repeat(rating) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Star", tint = Color.Green.copy(alpha=0.5f),)
                }
                repeat(5 - rating) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Star", tint = Color.Gray.copy(alpha= 0.5f))
                }
            }

            Text(
                text = review,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
