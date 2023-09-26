package com.example.movies.view.botoomNavScreens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.TextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.movies.model.UserProfile
import com.example.movies.ui.theme.light_grey
import com.example.movies.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import com.example.movies.R
import com.example.movies.view.AuthScreens.CommonButton
import com.example.movies.view.AuthScreens.TextFiledColors
import com.example.movies.view.Common.CircularProgressBarWidget

@Composable
fun Profile(navHostController: NavHostController) {

    var isEditMode by remember { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("") }
    val profileViewModel: ProfileViewModel = hiltViewModel()
    var scope = rememberCoroutineScope()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var userInfo by remember { mutableStateOf<UserProfile>(UserProfile()) }

    val profileState = profileViewModel.profileState.value
    val updateProfileState = profileViewModel.updatedProfileState.value
    val context = LocalContext.current

    // Camera capture launcher
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri = it
        }
    }

    LaunchedEffect(Unit) {
        profileViewModel.getUserProfile()
    }

    LaunchedEffect(key1 = profileState.error) {
        scope.launch {
            if (profileState.error.isNotEmpty()) {
                val error = profileState.error
                Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(key1 = profileState.success) {
        scope.launch {
            if (profileState.success != null) {
                val success = profileState.success
                userInfo = success
                isEditMode = false
            }
        }
    }

    LaunchedEffect(key1 = updateProfileState.success) {
        scope.launch {
            if (updateProfileState.success != null) {
                profileViewModel.getUserProfile()
                isEditMode = false
                Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(key1 = updateProfileState.error) {
        scope.launch {
            if (updateProfileState.error.isNotEmpty()) {
                val error = updateProfileState.error
                Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isEditMode) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                IconButton(
                    onClick = {
                        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_LONG).show()
                        profileViewModel.logout(navHostController)
                    },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_logout_24),
                        tint = light_grey,
                        contentDescription = "Logout",
                    )
                }

                IconButton(
                    onClick = { isEditMode = !isEditMode },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        tint = light_grey,
                        contentDescription = "Edit",
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Box(modifier = Modifier.size(150.dp)) {
            Image(
                painter = if (imageUri == null) rememberImagePainter(data = userInfo?.avatarUrl)
                else rememberImagePainter(data = imageUri),
                contentDescription = "User Image",
                modifier = Modifier
                    .fillMaxSize()
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,

                )
            if (isEditMode) {
                Icon(painterResource(id = R.drawable.baseline_camera_alt_24),
                    tint = light_grey,
                    contentDescription = "Icon",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp) // Adjust the padding as needed
                        .background(MaterialTheme.colorScheme.background)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .size(32.dp)
                        .clickable {
                            pickImageLauncher.launch("image/*")
                        })
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isEditMode) {
            TextField(
                value = name,
                onValueChange = { newName ->
                    name = newName
                },
                placeholder = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                colors = TextFiledColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CommonButton(text = "Save", onClick = {
                scope.launch {
                    profileViewModel.updateUserProfile(name, imageUri)
                }
            })

        } else {
            RowContent(title = "Username : ", userInfo.name)
            RowContent(title = "Email : ", userInfo.email)
        }

        if (profileState.isLoading || updateProfileState.isLoading) {
            CircularProgressBarWidget()
        }

    }
}

@Composable
fun RowContent(title: String, name: String) {
    Row {
        CommonText(name = title)
        CommonText(name)
    }
}

@Composable
fun CommonText(name: String) {
    Text(
        text = name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White
    )
}