package com.example.movies.view.AuthScreens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movies.R
import com.example.movies.ui.theme.light_grey
import com.example.movies.view.Common.CircularProgressBarWidget
import com.example.movies.view.Nav.BottomBarScreens
import com.example.movies.view.Nav.Screens
import com.example.movies.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navHostController: NavHostController
) {
    val signUpViewModel: SignUpViewModel = hiltViewModel()
    val state = signUpViewModel.signUpState.collectAsState(initial = null)

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SignUp",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = light_grey
        )

        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            colors = TextFiledColors(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            placeholder = { Text(text = stringResource(id = R.string.email_hint)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFiledColors(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            placeholder = { Text(text = stringResource(id = R.string.password_hint)) },
        )

        CommonButton(text = "Signup", onClick = {
            scope.launch {
                signUpViewModel.UserSignUp(email, password)
            }
        })

        if (state.value?.isLoading == true) {
            CircularProgressBarWidget()
        }

        UserAlreadyHaveAccountOrNot(
            navHostController,
            "Already have an Account? ",
            "Sign In",
            "login"
        )

        GoogleSignInWidget(navHostController)

        LaunchedEffect(key1 = state.value?.isSuccess) {
            scope.launch {
                if (state.value?.isSuccess?.isNotEmpty() == true) {
                    val success = state.value?.isSuccess
                    navHostController.navigate(Screens.Login.route) {
                        popUpTo(Screens.Login.route) {
                            inclusive = true
                        }
                    }
                    Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                }
            }
        }

        LaunchedEffect(key1 = state.value?.isError) {
            scope.launch {
                if (state.value?.isError?.isNotEmpty() == true) {
                    val error = state.value?.isError
                    Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}