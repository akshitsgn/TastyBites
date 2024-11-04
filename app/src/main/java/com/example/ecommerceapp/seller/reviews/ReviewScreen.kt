@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.ecommerceapp.seller.reviews



import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerceapp.R
import com.example.ecommerceapp.common.models.Reviews
import com.example.ecommerceapp.seller.addseller.AddSellerViewModel
import kotlin.math.roundToInt

@Composable
fun CustomerFeedbackScreen() {
    val viewModel: RatingsViewModel= hiltViewModel()
    val reviews =  viewModel.ratings.collectAsState()
    val viewModel1: AddSellerViewModel = hiltViewModel()
    val uniqueSeller by viewModel1.uniqueSeller.collectAsState()
    val averageRating = if(reviews.value.isNotEmpty()) { reviews.value.map { it.rating }.average().roundToInt() } else{ 0.0 }
    val excellentRating = reviews.value.filter { it.rating ==5 }
    val excellentAverage = if (excellentRating.isNotEmpty() && reviews.value.isNotEmpty()) { (excellentRating.size / (reviews.value.size*1.0))} else { 0.0}
    val goodRating = reviews.value.filter { it.rating ==3 || it.rating==4 }
    val goodAverage = if (goodRating.isNotEmpty() && reviews.value.isNotEmpty()) { (goodRating.size / (reviews.value.size*1.0))} else { 0.0}
    val mediumRating = reviews.value.filter { it.rating ==2 }
    val mediumAverage = if (mediumRating.isNotEmpty() && reviews.value.isNotEmpty()) { (mediumRating.size / (reviews.value.size*1.0))} else { 0.0}
    val poorRating = reviews.value.filter { it.rating <=2}
    val poorAverage = if (poorRating.isNotEmpty() && reviews.value.isNotEmpty()) { (poorRating.size / (reviews.value.size*1.0))} else { 0.0}
    Log.d("excellentRating",excellentRating.size.toString())
    Log.d("excellentAverage",excellentAverage.toString())
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
            if(reviews.value.size==0){
                Box(modifier=Modifier.fillMaxSize().background(Color.White.copy(alpha=0.6f)),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color=Color.Black.copy(alpha=0.6f)
                    )
                }
            }
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
                        text = averageRating.toInt().toString(),
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
                        repeat(averageRating.toInt()) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star",
                                tint = Color(0xFFFFC30B)
                            )
                        }
                        repeat(5-averageRating.toInt()){
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star",
                                tint = Color.Gray.copy(alpha = 0.5f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Based on ${reviews.value.size} reviews",
                        fontFamily = FontFamily.Cursive,
                        color = Color.White,
                        fontSize = 22.sp,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )


                    Spacer(modifier = Modifier.height(26.dp))

                    // Ratings breakdown
                    RatingBar(label = "Excellent", rating = excellentAverage.toFloat())
                    RatingBar(label = "Good", rating = goodAverage.toFloat())
                    RatingBar(label = "Average", rating = mediumAverage.toFloat())
                    RatingBar(label = "Poor", rating = 0.2f)
                    Spacer(modifier = Modifier.height(56.dp))
                }
                LazyColumn {
                    items(reviews.value) { Review ->
                        ReviewItem(review = Review)
                    }
                }
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
fun ReviewItem(review: Reviews) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.LightGray.copy(alpha = 0.15f), shape = RoundedCornerShape(26.dp)),
        verticalAlignment = Alignment.Top
    ) {
        // Placeholder for user image
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(48.dp)
                .background(Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier=Modifier.padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 4.dp)) {

                Text(
                    text = "Martin Luther",
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = review.timestamp.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.caption)
            }

            Row {
                repeat(review.rating) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Star", tint = Color(0xFFFFC30B),)
                }
                repeat(5 - review.rating) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Star", tint = Color.Gray.copy(alpha= 0.5f))
                }
            }

            Text(
                text = review.description,
                color = Color.White,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

