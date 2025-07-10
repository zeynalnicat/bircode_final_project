package com.example.profile.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.Uri
import coil3.compose.AsyncImage
import com.example.common.R
import com.example.common.presentation.components.CoreTextField
import com.example.common.presentation.theme.DTextStyle
import com.example.common.presentation.theme.Primary
import com.example.common.presentation.theme.Secondary
import com.example.core.AppStrings

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {

    val state = viewModel.state.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<android.net.Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
          selectedImageUri= uri
    }

    LaunchedEffect(Unit) {
        viewModel.initiateController(navController)
        viewModel.onIntent(ProfileIntent.OnGetProfileDetails)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when(it){
                is ProfileUiEffect.OnShowError -> snackbarHostState.showSnackbar(it.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {

                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            viewModel.onIntent(ProfileIntent.OnNavigateBack)
                        },
                    tint = Secondary,
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(AppStrings.profile, style = DTextStyle.title.copy(color = Primary))
                }


            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                val avatarModifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .border(0.5.dp, Color.Transparent, CircleShape)

                Box(
                    modifier = Modifier.size(96.dp)
                ) {
                    if (state.profileImg.isEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.default_avatar),
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = avatarModifier
                        )
                    } else {
                        AsyncImage(
                            model = selectedImageUri?:state.profileImg,
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = avatarModifier
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.BottomEnd)
                            .background(Color.Black, shape = CircleShape)
                            .padding(4.dp)
                            .clickable{
                                    launcher.launch("image/*")
                            }
                    )
                }

                Spacer(Modifier.height(16.dp))
                Text(state.name, style = DTextStyle.t16.copy(color = Secondary))

            }

            Spacer(Modifier.height(24.dp))

            Text(AppStrings.yourName, style = DTextStyle.t14Primary)
            Spacer(Modifier.height(8.dp))
            CoreTextField(
                value = state.name,
                enabled = false,
                onChange = {},
                placeHolder = AppStrings.yourName
            )

            Spacer(Modifier.height(16.dp))
            Text(AppStrings.email, style = DTextStyle.t14Primary)
            Spacer(Modifier.height(8.dp))
            CoreTextField(
                value = state.email,
                enabled = false,
                onChange = {},
                placeHolder = AppStrings.email
            )

            Spacer(Modifier.height(16.dp))
        }
    }


}