package com.example.jetsurvey.ui.main.signin

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetsurvey.ui.theme.snackbarAction
import com.example.jetsurvey.R
import com.example.jetsurvey.extention.supportWideScreen
import com.example.jetsurvey.ui.main.welcome.*
import com.example.jetsurvey.ui.theme.JetsurveyTheme
import kotlinx.coroutines.launch

sealed class SignInEvent {

    data class SignIn(val email: String, val password: String) : SignInEvent()
    object SignUp : SignInEvent()
    object SignInAsGuest : SignInEvent()
    object NavigateBack : SignInEvent()
}

@Composable
fun SignIn(onNavigationEvent: (SignInEvent) -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackbarErrorText = stringResource(id = R.string.feature_not_available)
    val snackbarActionLabel = stringResource(id = R.string.dismiss)

    Scaffold(
        topBar = {
            SignInSignUpTopbar(
                topbarText = stringResource(id = R.string.sign_in),
                onBackPress = { onNavigationEvent(SignInEvent.NavigateBack) }
            )
        },
        content = {
            SignInSignUpScreen(
                modifier = Modifier.supportWideScreen(),
                onSignedInAsGuest = { onNavigationEvent(SignInEvent.SignInAsGuest) }) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    SignInContent(
                        onSignInSubmitTed = { email, password ->
                            onNavigationEvent(SignInEvent.SignIn(email, password))
                        })

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = snackbarErrorText,
                                    actionLabel = snackbarActionLabel
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.forgot_password))
                    }
                }
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        ErrorSnackbar(
            snackbarHostState = snackbarHostState,
            onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun SignInContent(onSignInSubmitTed: (email: String, password: String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val focusRequester = remember { FocusRequester() }
        val emailState = remember { EmailState() }

        Email(
            emailState = emailState,
            onImeAction = { focusRequester.requestFocus() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val passwordState = remember { PasswordState() }
        Password(
            modifier = Modifier.focusRequester(focusRequester),
            label = stringResource(id = R.string.password),
            passwordState = passwordState,
            onImeAction = { onSignInSubmitTed(emailState.text, passwordState.text) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSignInSubmitTed(emailState.text, passwordState.text) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = emailState.isValid && passwordState.isValid
        ) {
            Text(
                text = stringResource(id = R.string.sign_in)
            )
        }
    }
}

@Composable
fun ErrorSnackbar(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onDismiss: () -> Unit = {}
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                content = {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.body2
                    )
                },
                action = {
                    data.actionLabel?.let {
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = stringResource(id = R.string.dismiss),
                                color = MaterialTheme.colors.snackbarAction
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}

@Preview(name = "Sign in light theme")
@Composable
fun SignInPreview() {
    JetsurveyTheme {
        SignIn {}
    }
}
