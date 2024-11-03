package com.example.ecommerceapp.seller.reviews

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerceapp.R

@Composable
fun CustomerFeedbackScreen() {

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.food3), // replace with your image resource
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f)
        )

        // Overlay gradient for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.Black.copy(alpha = 0.7f)
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                //.background()
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {

Column (
    modifier = Modifier
        .background(Color.Green.copy(alpha = 0.06f), shape = RoundedCornerShape(26.dp))
        .padding(8.dp)

){
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { /* Handle back navigation */ }) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint= Color.White
            )
        }

        Text(
            text = "Customer Feedback",
            textAlign = TextAlign.Center,
            fontSize = 45.sp,
            color = Color.White,
            fontFamily = FontFamily.Cursive,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 8.dp)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Overall rating
    Text(
        text = "Overall Rating",
        fontFamily = FontFamily.Cursive,
        color = Color.White,
        fontSize = 22.sp,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )

    Text(
        text = "3.9",
        fontFamily = FontFamily.Cursive,
        color = Color.White,
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
                tint = Color(0xFFFFC30B)
            )
        }
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star",
            tint = Color.Gray.copy(alpha = 0.5f)
        )
    }
Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "Based on 20 reviews",
        fontFamily = FontFamily.Cursive,
        color = Color.White,
        fontSize = 22.sp,
        style = MaterialTheme.typography.body2,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )


    Spacer(modifier = Modifier.height(26.dp))

    // Ratings breakdown
    RatingBar(label = "Excellent", rating = 0.8f)
    RatingBar(label = "Good", rating = 0.6f)
    RatingBar(label = "Average", rating = 0.4f)
    RatingBar(label = "Poor", rating = 0.2f)
}
            Spacer(modifier = Modifier.height(56.dp))

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
}

@Composable
fun RatingBar(label: String, rating: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = FontFamily.Cursive,
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
        modifier = Modifier.padding(vertical = 8.dp).background(Color.LightGray.copy(alpha=0.15f), shape = RoundedCornerShape(26.dp)),
        verticalAlignment = Alignment.Top
    ) {
        // Placeholder for user image
        Box(
            modifier = Modifier.padding(8.dp)
                .size(48.dp)
                .background(Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier=Modifier.padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 4.dp)) {

                Text(
                    text = name,
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = daysAgo,
                    color = Color.White,
                    style = MaterialTheme.typography.caption)
            }

            Row {
                repeat(rating) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Star", tint = Color(0xFFFFC30B),)
                }
                repeat(5 - rating) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Star", tint = Color.Gray.copy(alpha= 0.5f))
                }
            }

            Text(
                text = review,
                color = Color.White,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun CustomCircularProgressBar(
    progress: Float,            // Progress as a value between 0f and 1f
    modifier: Modifier = Modifier.size(100.dp), // Size of the progress bar
    strokeWidth: Dp = 8.dp,     // Thickness of the progress bar
    color: Color = Color(0xFF4CAF50), // Color of the progress bar
    backgroundColor: Color = Color.LightGray, // Background color of the track
    showPercentage: Boolean = true // Option to show percentage in the center
) {
    // Animate the progress for a smoother effect
    val animatedProgress by animateFloatAsState(targetValue = progress)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            // Draw background circle
            drawCircle(
                color = backgroundColor,
                radius = size.minDimension / 2,
                style = Stroke(width = strokeWidth.toPx())
            )

            // Draw progress arc
            withTransform({
                rotate(degrees = -90f)
            }) {
                drawArc(
                    color = color,
                    startAngle = 0f,
                    sweepAngle = 360 * animatedProgress,
                    useCenter = false,
                    style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        // Optional: Display percentage in the center
        if (showPercentage) {
            Text(
                text = "${(animatedProgress * 100).toInt()}%",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}
