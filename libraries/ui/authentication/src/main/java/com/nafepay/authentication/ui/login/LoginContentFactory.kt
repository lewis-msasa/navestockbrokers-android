package com.nafepay.authentication.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.nafepay.authentication.events.LoginEvent
import com.nafepay.authentication.states.LoginState
import com.nafepay.authentication.viewModels.LoginViewModel
import com.nafepay.common_ui.theme.commonPadding
import com.nafepay.common_ui.theme.buttonHeight
import com.nafepay.common_ui.composers.buttons.GoogleButton
import com.nafepay.common_ui.composers.textfields.CustomTextField
import com.nafepay.common_ui.R
import com.nafepay.common_ui.theme.zeroPadding

@Composable
fun Login(
    loginViewModel: LoginViewModel,
    OnGoogleSignIn : () -> Unit,
){
    val state by loginViewModel.uiState.collectAsState()
    login(
        state,
        loginViewModel::handleRegistrationEvent,
        OnGoogleSignIn,
    )
}
@Composable
private fun login(
    state : LoginState,
    events: (event: LoginEvent) -> Unit,
    OnGoogleSignIn : () -> Unit,
){
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.testTag("TAG_PROGRESS"),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
    else{

        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {
            BoxWithConstraints() {
                val screenWidth = maxWidth
                val screenHeight = maxHeight
                val scrollState = rememberScrollState(0)
                //Box(Modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = commonPadding)
                            .align(Alignment.Center)
                            .verticalScroll(scrollState)
                    )
                    {

                        Text(
                            "Welcome Back",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleSmall.copy(
                                MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .align(alignment = Alignment.Start)
                                .padding(start = commonPadding)
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        var emailState by remember { mutableStateOf(TextFieldValue(state.emailAddress)) }
                        CustomTextField(
                            value = emailState,
                            placeholder = "Email address",
                            onChange = {
                                events(LoginEvent.EmailChanged(it.text))
                                emailState = it
                            },
                            width = screenWidth,
                            padding = commonPadding,
                            shape = RoundedCornerShape(10),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            )
                        )

                        Spacer(modifier = Modifier.padding(8.dp))
                        var passwordState by remember { mutableStateOf(TextFieldValue(state.password)) }
                        var passwordVisibility by remember { mutableStateOf(false) }
                        CustomTextField(
                            value = passwordState,
                            placeholder = "Password",
                            onChange = {
                                events(LoginEvent.PasswordChanged(it.text))
                                passwordState = it
                            },
                            width = screenWidth,
                            padding = commonPadding,
                            shape = RoundedCornerShape(10),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done,
                            ),
                            visualTransformation = if (passwordVisibility)
                                                        VisualTransformation.None
                                               else PasswordVisualTransformation(),
                            trailingIcon = {
                                Icon(
                                    painterResource(R.drawable.ic_eye),
                                    "",
                                    tint = MaterialTheme.colorScheme.onSecondary,
                                    modifier = Modifier.clickable {
                                        passwordVisibility = !passwordVisibility
                                    })
                            }

                        )

                        Spacer(modifier = Modifier.padding(8.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = commonPadding)
                        ) {
                            Button(
                                shape = MaterialTheme.shapes.medium,
                                enabled = state.isLoginContentValid,
                                colors = ButtonDefaults.buttonColors(
                                    disabledContentColor = MaterialTheme.colorScheme.primary.copy(
                                        alpha = 0.2f
                                    ),
                                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(
                                        alpha = 0.2f
                                    )
                                ),
                                modifier = Modifier
                                    .width(screenWidth)
                                    .height(buttonHeight),
                                //.padding(horizontal = 12.dp),

                                onClick = {
                                    events(LoginEvent.LoginClicked)
                                }) {

                                Text(
                                    "Login",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )


                            }

                            Spacer(modifier = Modifier.padding(8.dp))

                            Text(
                                "Forgot Password?",
                                Modifier.clickable {
                                    events(LoginEvent.ForgotPasswordClicked)
                                },
                                style = TextStyle(textDecoration = TextDecoration.Underline),
                                color = MaterialTheme.colorScheme.primary,

                                )

                            Spacer(modifier = Modifier.padding(12.dp))


                            Text(
                                "------------ OR ---------------",
                                color = MaterialTheme.colorScheme.onSecondary
                            )

                            Spacer(modifier = Modifier.padding(12.dp))

                            GoogleButton(width = screenWidth) {
                                OnGoogleSignIn()
                            }

                            Spacer(modifier = Modifier.padding(8.dp))





                            Spacer(modifier = Modifier.padding(12.dp))

                            Spacer(modifier = Modifier.padding(12.dp))


                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            //modifier = Modifier.align(Alignment.BottomCenter).padding(12.dp)
                        ) {
                            Text(
                                "Don't have an account?",

                                color = MaterialTheme.colorScheme.onSurface,

                                )
                            Text(
                                "Sign Up",
                                Modifier
                                    .clickable {
                                        events(LoginEvent.RegisterClicked)
                                    }
                                    .padding(start = 4.dp),
                                style = TextStyle(),
                                color = MaterialTheme.colorScheme.primary,

                                )
                        }
                    }
                  //}
                Spacer(modifier = Modifier.padding(commonPadding))


            }
        }
    }
}