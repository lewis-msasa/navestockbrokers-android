package com.nafepay.authentication.ui.registration

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nafepay.common_ui.composers.textfields.CustomTextField
import com.nafepay.common_ui.composers.buttons.GoogleButton
import com.nafepay.common_ui.theme.buttonHeight
import com.nafepay.common_ui.theme.commonPadding
import com.facebook.login.widget.LoginButton
import com.nafepay.authentication.events.RegistrationEvent
import com.nafepay.authentication.states.RegistrationState
import com.nafepay.authentication.viewModels.RegistrationViewModel
import com.nafepay.common_ui.composers.general.LoadingBoxWithParameters
import kotlinx.coroutines.launch
import com.nafepay.common_ui.R
import com.nafepay.common_ui.theme.zeroPadding

@Composable
fun Registration(
    viewModel: RegistrationViewModel,
    OnGoogleSignIn : () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    Registration(
        viewState = state,
        events = viewModel::handleRegistrationEvent,
        OnGoogleSignIn
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Registration(
    viewState: RegistrationState,
    events: (event: RegistrationEvent) -> Unit,
    OnGoogleSignIn : () -> Unit,
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    //val focusRequester = remember {FocusRequester() }
    val (focusRequester) = FocusRequester.createRefs()


    val keyboardController = LocalSoftwareKeyboardController.current

    val focusManager = LocalFocusManager.current

    Surface(color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {

            var fbButton : LoginButton? = null
            BoxWithConstraints() {
                val screenWidth = maxWidth
                val scrollState = rememberScrollState(0)

                Box(Modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = commonPadding)
                            .align(Alignment.TopCenter)
                            .verticalScroll(scrollState)
                    )
                    {
                        SnackbarHost(
                            hostState = snackBarHostState,
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.background)

                        )
                        Text(
                            "Create New Account",
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

                        var emailField by remember { mutableStateOf(TextFieldValue(viewState.email)) }
                        CustomTextField(
                            value = emailField,
                            placeholder = "Email address",
                            hasError = viewState.isEmailValid,
                            modifier  = Modifier.focusRequester(focusRequester),
                            onChange = {
                                events(RegistrationEvent.EmailChanged(it.text))
                                emailField = it

                            },
                            //modifier = Modifier.focusRequester(focusRequester),
                            width = screenWidth,
                            padding = commonPadding,
                            enabled = !viewState.isLoading,
                            shape = RoundedCornerShape(10),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    //focusRequester.requestFocus()
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            )
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        var fullnameField by remember { mutableStateOf(TextFieldValue(viewState.fullname)) }
                        CustomTextField(
                            value = fullnameField,
                            placeholder = "Full-name",
                            modifier = Modifier.focusRequester(focusRequester),
                            onChange = {
                                events(RegistrationEvent.FullNameChanged(it.text))
                                fullnameField = it

                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    //focusRequester.requestFocus()
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            ),
                            enabled = !viewState.isLoading,
                            width = screenWidth,
                            padding = commonPadding,
                            shape = RoundedCornerShape(10)
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        var phoneNumberField by remember { mutableStateOf(TextFieldValue(viewState.phoneNumber)) }
                        CustomTextField(
                            value = phoneNumberField,
                            placeholder = "enter phone number",
                            onChange = {
                                events(RegistrationEvent.PhoneNumberChanged(it.text))
                                phoneNumberField = it
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Phone
                            ),
                            //modifier = Modifier.focusRequester(focusRequester),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    //focusRequester.requestFocus()
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            ),
                            width = screenWidth,
                            padding = commonPadding,
                            shape = RoundedCornerShape(10)
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        var isPasswordFieldFocused by remember { mutableStateOf(false)}
                        AnimatedVisibility(visible = isPasswordFieldFocused) {
                            Text(
                                "At least one special character, one digit and 6 characters long",
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    Color.Gray,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .align(alignment = Alignment.Start)
                                    .padding(start = commonPadding)

                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))

                        var passwordField by remember { mutableStateOf(TextFieldValue(viewState.password)) }
                        var passwordVisibility by remember { mutableStateOf(false) }
                        CustomTextField(
                            value = passwordField,
                            placeholder = "Password",
                            onFocused = {
                                isPasswordFieldFocused =  true
                            },
                            onUnFocused = {
                                isPasswordFieldFocused= false
                            },
                            onChange = {
                                events(RegistrationEvent.PasswordChanged(it.text))
                                passwordField = it
                            },
                            width = screenWidth,
                            enabled = !viewState.isLoading,
                            padding = commonPadding,
                            //modifier = Modifier.focusRequester(focusRequester),
                            shape = RoundedCornerShape(10),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    //focusRequester.requestFocus()
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            ),
                            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                Icon(
                                    painterResource(R.drawable.ic_eye),
                                    "",
                                    modifier = Modifier.clickable {
                                        passwordVisibility = !passwordVisibility
                                    },
                                    tint = MaterialTheme.colorScheme.onSecondary
                                )
                            }

                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        var confirmVisibility by remember { mutableStateOf(false) }
                        var confirmField by remember { mutableStateOf(TextFieldValue(viewState.confirmPassword)) }
                        CustomTextField(
                            value = confirmField,
                            placeholder = "Confirm Password",
                            onChange = {
                                events(RegistrationEvent.ConfirmPasswordChanged(it.text))
                                confirmField = it
                            },
                            width = screenWidth,
                            enabled = !viewState.isLoading,
                            padding = commonPadding,
                            //modifier = Modifier.focusRequester(focusRequester),
                            shape = RoundedCornerShape(10),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            ),
                            visualTransformation = if (confirmVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                Icon(
                                    painterResource(R.drawable.ic_eye), "",
                                    modifier = Modifier.clickable {
                                        confirmVisibility = !confirmVisibility
                                    },
                                    tint = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = zeroPadding)
                        ) {
                            Button(
                                shape = MaterialTheme.shapes.medium,
                                enabled = viewState.isRegistrationContentValid && !viewState.isLoading,
                                modifier = Modifier
                                    .width(screenWidth)
                                    .height(buttonHeight)
                                    .padding(horizontal = 12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                ),
                                onClick = {
                                    events(RegistrationEvent.RegistrationClicked)
                                }) {

                                Text(
                                    "Sign Up",
                                    color = Color.White
                                )


                            }

                            Spacer(modifier = Modifier.padding(12.dp))


                            Spacer(modifier = Modifier.padding(12.dp))

                            Spacer(modifier = Modifier.padding(12.dp))

                            Text(
                                "------------ OR ---------------",
                                color = MaterialTheme.colorScheme.onSecondary
                            )

                            Spacer(modifier = Modifier.padding(12.dp))

                            GoogleButton(width = screenWidth) {
                                OnGoogleSignIn()
                                //activity.OnGoogleSignIn()
                            }



                            Spacer(modifier = Modifier.padding(8.dp))


                            Spacer(modifier = Modifier.padding(12.dp))

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                //modifier = Modifier.align(Alignment.BottomCenter).padding(12.dp)

                            ) {
                                Text(
                                    "Already have an account?",

                                    color = MaterialTheme.colorScheme.onSurface,

                                    )
                                Text(
                                    "Login",
                                    Modifier
                                        .clickable {
                                            events(RegistrationEvent.LoginClicked)
                                        }
                                        .padding(start = 4.dp),
                                    style = TextStyle(),
                                    color = MaterialTheme.colorScheme.primary,

                                    )
                            }
                        }
                        Spacer(modifier = Modifier.padding(commonPadding))
                        if (!viewState.errorMessage.isNullOrEmpty()) {
                            scope.launch {
                                var result = snackBarHostState.showSnackbar(
                                    message = viewState.errorMessage
                                )

                            }
                        }




                    }
                    if (viewState.isLoading) {
                        LoadingBoxWithParameters(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.TopCenter)
                                .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                        )
                    }

                }






            }
        }



}