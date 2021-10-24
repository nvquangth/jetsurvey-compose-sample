package com.example.jetsurvey.ui.main.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetsurvey.R
import com.example.jetsurvey.ui.theme.JetsurveyTheme

sealed class WelcomeEvent {
    data class SignInSignUp(val email: String) : WelcomeEvent()
    object SignInAsGuest : WelcomeEvent()
}

@Composable
fun WelcomeScreen(
    onEvent: (WelcomeEvent) -> Unit
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Brand()
        SignInCreateAccount(
            onEvent = onEvent,
            onFocusChange = {

            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun SignInCreateAccount(
    modifier: Modifier = Modifier,
    onFocusChange: (Boolean) -> Unit,
    onEvent: (WelcomeEvent) -> Unit,
) {

    val emailState = remember {
        EmailState()
    }

    ConstraintLayout(modifier = modifier) {
        val (titleSignIn, email, buttonContinue, titleOr, buttonSignGuest) = createRefs()

        Text(
            text = stringResource(id = R.string.sign_in_create_account),
            modifier = modifier.constrainAs(titleSignIn) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            })

        val onSubmit = {
            if (emailState.isValid) {
                onEvent(WelcomeEvent.SignInSignUp(emailState.text))
            } else {
                emailState.enableShowErrors()
            }
        }
        onFocusChange(emailState.isFocused)
        Email(
            emailState = emailState,
            imeAction = ImeAction.Done,
            onImeAction = onSubmit,
            modifier = modifier.constrainAs(email) {
                start.linkTo(titleSignIn.start)
                end.linkTo(titleSignIn.end)
                top.linkTo(titleSignIn.bottom)
            }
        )
        Button(
            onClick = onSubmit,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .constrainAs(buttonContinue) {
                    start.linkTo(email.start)
                    end.linkTo(email.end)
                    top.linkTo(email.bottom)
                }
        ) {
            Text(
                text = stringResource(id = R.string.user_continue),
                style = MaterialTheme.typography.subtitle2,
            )
        }
    }
}

@Composable
fun Brand(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier) {
        val (logo, slogan) = createRefs()

        Logo(modifier = modifier.constrainAs(logo) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        })

        Text(text = stringResource(id = R.string.app_tagline),
            modifier = modifier.constrainAs(slogan) {
                start.linkTo(logo.start)
                end.linkTo(logo.end)
                top.linkTo(logo.bottom)
            })
    }
}

@Composable
fun Logo(
    modifier: Modifier = Modifier,
    lightTheme: Boolean = MaterialTheme.colors.isLight
) {
    val assetId = if (lightTheme) {
        R.drawable.ic_logo_light
    } else {
        R.drawable.ic_logo_dark
    }

    Image(
        painter = painterResource(id = assetId),
        modifier = modifier,
        contentDescription = null
    )
}

@Preview
@Composable
fun DevPreview() {
    JetsurveyTheme(darkTheme = false) {
        WelcomeScreen {

        }
    }
}