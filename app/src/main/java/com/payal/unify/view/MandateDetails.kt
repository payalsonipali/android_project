package com.payal.unify.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.payal.unify.R
import com.payal.unify.model.ApiResponse
import com.payal.unify.model.CardItem
import com.payal.unify.ui.theme.gray
import com.payal.unify.ui.theme.light_gray
import com.payal.unify.ui.theme.light_orange
import com.payal.unify.ui.theme.orange
import com.payal.unify.viewmodel.MandateDetailsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mandateDetails(viewModel: MandateDetailsViewModel) {
    var selectedCardIndex by remember { mutableStateOf(0) } // Initialize selectedCardIndex

    val mock = viewModel.getApiResponse()

    Scaffold(
        topBar = { topBar() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                DetailCard(mock)
                AutoPayOptions(mock.cardItems, selectedCardIndex) { selectedIndex ->
                    selectedCardIndex = selectedIndex
                }
                PayUsing(mock.cardItems, selectedCardIndex)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBar() {
    return CenterAlignedTopAppBar(
        modifier = Modifier
            .shadow(4.dp)
            .zIndex(1f),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.White),
        title = {
            Text(
                text = "Mandate Details", // Replace with your app title
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    // Handle navigation icon click (e.g., navigation drawer)
                },

                ) {
                showLocalIcon(R.drawable.baseline_arrow_back_ios_new_24, "Back", orange)
            }
        },
    )
}

@Composable
fun DetailCard(mock: ApiResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ValidityWithAmount(mock.expiry, mock.amountDescription)
            Divider()
            Frequency(mock.frequency)
            Divider()
            Nudge(mock.amountDescription)
        }
    }
}

@Composable
fun ValidityWithAmount(expiry: String, amountDescription: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            setText("Valid till - ", FontWeight.Normal)
            setText(expiry, FontWeight.Bold)
        }
        Row() {
            setText("Upto Amount - ", FontWeight.Normal)
            setText(amountDescription, FontWeight.Bold)
        }
    }
}

@Composable
fun Frequency(frequency: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
    ) {
        setText("Frequency - ", FontWeight.Normal)
        setText(frequency, FontWeight.Bold)
    }
}

@Composable
fun setText(text: String, weight: FontWeight) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        fontWeight = weight
    )
}

@Composable
fun Nudge(amount: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .background(
                color = light_orange,
                shape = RoundedCornerShape(12.dp) // Adjust the corner radius as needed
            ),
        contentAlignment = Alignment.CenterStart // Align content to the start (left)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            // Icon on the left
            Icon(
                painter = painterResource(id = R.drawable.baseline_error_outline_24),
                contentDescription = "Info Icon",
                tint = orange, // Icon color
                modifier = Modifier.size(24.dp) // Icon size
            )

            // Text on the right

            setNugdeText(amount)
        }
    }
}

@Composable
fun Divider() {
    Divider(
        modifier = Modifier
            .height(1.dp)
            .padding(start = 16.dp),
        color = light_gray
    )
}

@Composable
fun setNugdeText(amount: String) {
    Surface(
        modifier = Modifier.padding(start = 8.dp),
        color = Color.Transparent
    ) {
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize),
                ) {
                    append("The amount will be blocked when ride is booked with safeboards, subject to above mentioned limit and validity.\nThe limit is upto ")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                ) {
                    append(amount)
                }
            }
        )
    }
}

@Composable
fun AutoPayOptions(
    cards: List<CardItem>,
    selectedCardIndex: Int,
    onCardSelected: (Int) -> Unit
) {
    Spacer(modifier = Modifier.height(1.dp)) // Add margin from the top

    Surface(
        modifier = Modifier.padding(start = 10.dp),
        color = Color.Transparent
    ) {
        Text(
            text = "Autopay Payment Options",
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = (screenWidth - 70.dp) / 3
    LazyRow(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        itemsIndexed(cards) { index, cardItem ->
            SelectedPaymentOption(cardItem, index, onCardSelected, cardWidth, selectedCardIndex)
        }
    }
}

@Composable
fun SelectedPaymentOption(
    cardItem: CardItem,
    index: Int,
    onCardSelected: (Int) -> Unit,
    cardWidth: Dp,
    selectedCardIndex: Int
) {

    Card(
        modifier = Modifier
            .width(cardWidth)
            .height(100.dp)
            .padding(5.dp)
            .clickable {
                onCardSelected(index)
            }
            .border(
                width = 2.dp,
                color = if (index == selectedCardIndex) orange else Color.Transparent, // Apply border only to the selected card
                shape = MaterialTheme.shapes.medium
            ),
        elevation = CardDefaults.elevatedCardElevation(6.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (index == selectedCardIndex) light_orange else Color.White,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            setNetworkImg(
                cardItem.imageUrl, "image", ContentScale.Fit, Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
            )
        }
    }
}

@Composable
fun PayUsing(cardItems: List<CardItem>, selectedCardIndex: Int) {
    Spacer(modifier = Modifier.height(1.dp)) // Add margin from the top

    Surface(
        modifier = Modifier.padding(start = 10.dp),
        color = Color.Transparent
    ) {
        Text(
            text = "Pay Using",
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        )
    }

    PayUsingRow(
        cardItems[selectedCardIndex].title,
        cardItems[selectedCardIndex].imageUrl,
        R.drawable.baseline_keyboard_arrow_right_24
    )
}

@Composable
fun PayUsingRow(
    text: String,
    startIcon: String,
    endIcon: Int,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                clip = true
            ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .border(
                    width = 0.6.dp,
                    color = orange, // Change the border color as needed
                    shape = RoundedCornerShape(10.dp) // Set corner radius to 0.dp
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                setNetworkImg(startIcon, "icon", ContentScale.Fit, Modifier.size(24.dp))
                Text(text = text, color = Color.Black)
            }

            showLocalIcon(endIcon, "Back", gray)
        }
    }
}

@Composable
fun setNetworkImg(
    image: String,
    contentDescription: String,
    contentScale: ContentScale,
    modifier: Modifier
) {
    AsyncImage(
        model = image,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}

@Composable
fun showLocalIcon(icon: Int, contentDescription: String, tint: Color) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = contentDescription,
        tint = tint,
        modifier = Modifier.size(26.dp)
    )
}