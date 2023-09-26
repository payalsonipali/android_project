package com.example.movies.view.AuthScreens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movies.Constants
import com.example.movies.R
import com.example.movies.view.Common.CircularProgressBarWidget
import com.example.movies.view.Nav.BottomBarScreens
import com.example.movies.view.Nav.Screens
import com.example.movies.viewmodel.SigninViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch

@Composable
fun GoogleSignInWidget(navHostController: NavHostController) {
    val signinViewModel: SigninViewModel = hiltViewModel()
    val googleSingInState = signinViewModel.googleState.value
    var scope = rememberCoroutineScope()
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            signinViewModel.callLauncher(it.data)
        }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken(Constants.SERVER_CLIENT_ID).build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            }) {
            Icon(
                painterResource(id = R.drawable.google),
                contentDescription = "Google_icon",
                modifier = Modifier.size(50.dp),
                tint = Color.Unspecified
            )
        }

        LaunchedEffect(key1 = googleSingInState.success) {
            scope.launch {
                if (googleSingInState.success != null) {
                    navHostController.navigate(BottomBarScreens.Home.route) {
                        popUpTo(Screens.Login.route) {
                            inclusive = true
                        }
                    }
                    Toast.makeText(context, "Logged In Successfully", Toast.LENGTH_LONG).show()
                }
            }
        }

        LaunchedEffect(key1 = googleSingInState.error) {
            scope.launch {
                if (googleSingInState.error.isNotEmpty()) {
                    val error = googleSingInState.error
                    Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    if (googleSingInState.loading) {
        CircularProgressBarWidget()
    }
}