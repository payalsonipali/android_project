package com.example.movies.view.AuthScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movies.ui.theme.light_grey

@Composable
fun UserAlreadyHaveAccountOrNot(navHostController:NavHostController, t1:String, type:String, route:String){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row() {
            androidx.compose.material3.Text(
                text = t1,
                fontWeight = FontWeight.Bold,
                color = light_grey,
            )

            androidx.compose.material3.Text(
                text = type,
                fontWeight = FontWeight.Bold,
                color = Color.Yellow,
                modifier = Modifier.clickable {
                    navHostController.navigate(route)
                }
            )
        }

        androidx.compose.material3.Text(
            text = "Or connect with",
            fontWeight = FontWeight.Medium,
            color = light_grey,
        )
    }
}

@Composable
fun TextFiledColors() = TextFieldDefaults.textFieldColors(
    backgroundColor = Color.White,
    cursorColor = light_grey,
    disabledLabelColor = Color.White,
    unfocusedIndicatorColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    placeholderColor = light_grey
)

@Composable
fun CommonBlackText(text:String){
    Text(
        text = text,
        color = Color.Black,
        modifier = Modifier.padding(7.dp)
    )
}

@Composable
fun CommonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().padding(top = 20.dp, start = 30.dp, end = 30.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black,
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        CommonBlackText(text = text)
    }
}

