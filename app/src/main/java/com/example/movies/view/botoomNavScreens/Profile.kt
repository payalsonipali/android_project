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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import coil.compose.rememberImagePainter
import com.example.movies.model.UserProfile
import com.example.movies.ui.theme.light_grey
import com.example.movies.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import com.example.movies.R
import com.example.movies.ui.theme.grey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile() {
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
            // Image selected, store the URI
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
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(
                    onClick = { isEditMode = !isEditMode },
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        tint = light_grey,
                        contentDescription = "Edit",
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .size(150.dp)

        ) {
            Image(
                painter = if (imageUri == null) rememberImagePainter(data = userInfo?.avatarUrl)
                else rememberImagePainter(data = imageUri),
                contentDescription = "User Image",
                modifier = Modifier.fillMaxSize().size(150.dp).clip(CircleShape),
                contentScale = ContentScale.Crop,

            )
            if (isEditMode) {
                Icon(
                    painterResource(id = R.drawable.baseline_camera_alt_24),
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
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.White,
                    backgroundColor = light_grey,
                    textColor = Color.Black,
                    placeholderColor = grey
                )
            )

        } else {
            RowContent(title = "Username : ", userInfo.name)
            RowContent(title = "Email : ", userInfo.email)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isEditMode) {
            Button(
                onClick = {
                    scope.launch {
                        profileViewModel.updateUserProfile(name, imageUri)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 30.dp, end = 30.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = light_grey,
                    contentColor = Color.Black,
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Save",
                    color = Color.Black,
                    modifier = Modifier.padding(7.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (profileState.isLoading || updateProfileState.isLoading) {
                CircularProgressIndicator()
            }

        }

    }
}

@Composable
fun RowContent(title: String, name: String){
    Row {
        CommonText(name = title)
        CommonText(name)
    }
}

@Composable
fun CommonText(name:String){
    Text(
        text = name,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}