package com.example.myapplication.screen

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.enableLiveLiterals
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.myapplication.R
import com.example.myapplication.activities.MainActivity2
import com.example.myapplication.utils.composables.CustomizedTextField
import com.example.myapplication.viewModel.UserDetailEvent
import com.example.myapplication.viewModel.UserDetailViewModel
import com.google.android.gms.common.AccountPicker

@Composable
fun SecondScreen(navController: NavController) {
    val userDetailViewModel = viewModel<UserDetailViewModel>()
    val uiState by userDetailViewModel.uiState.collectAsStateWithLifecycle()
    val setEvents: (UserDetailEvent) -> Unit = userDetailViewModel::setEvents
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        val context = LocalContext.current
        var selectedAccount by remember {
            mutableStateOf<String?>(null)
        }
        val emailLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                selectedAccount =
                    activityResult.data?.extras?.getString(AccountManager.KEY_ACCOUNT_NAME)
            }
        }
        val accountPickerIntent = remember {
            AccountPicker.newChooseAccountIntent(
                AccountPicker.AccountChooserOptions
                    .Builder()
                    .setAlwaysShowAccountPicker(true)
                    .setAllowableAccountsTypes(null)
                    .build()
            )
        }
        val id = "id"
        val greetingText = buildAnnotatedString {
            append("Hi ")
            appendInlineContent(id, "icon")
        }
        val inlineContent = remember {
            mapOf(
                Pair(
                    id,
                    InlineTextContent(
                        Placeholder(
                            width = 40.sp,
                            height = 40.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                        )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_hand),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                )
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = greetingText,
                inlineContent = inlineContent,
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Blue
                )
            )
            Text(
                text = "Let's start with some basic details",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Blue
                )
            )
        }
        CustomizedTextField(
            placeholder = "Name",
            label = "Enter Your Name:",
            value = uiState.name,
            onValueChange = {
                setEvents(UserDetailEvent.EditName(name = it))
            })
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 12.dp),
            shape = RoundedCornerShape(4.dp),
            contentPadding = PaddingValues(16.dp),
            border = BorderStroke(width = 1.dp, color = Color.Gray),
            onClick = {
                emailLauncher.launch(accountPickerIntent)
            },
            content = {
                Text(
                    text = if (!selectedAccount.isNullOrEmpty()) selectedAccount!! else "Select Email",
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        val intent = Intent(context, MainActivity2::class.java)
        intent.putExtra(uiState.name, "name")
        intent.putExtra(uiState.email, "email")
        Button(
            enabled = uiState.name.isNotEmpty() && !uiState.email.isNullOrEmpty(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { context.startActivity(Intent(context, MainActivity2::class.java)) }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Ready to scan",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                )
                LottieAnimation(
                    composition = rememberLottieComposition(
                        spec = LottieCompositionSpec.RawRes(
                            resId = R.raw.btn_arrow
                        )
                    ).value,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(40.dp),
                    speed = 3f
                )
            }
        }
    }
}