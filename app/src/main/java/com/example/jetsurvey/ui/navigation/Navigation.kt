package com.example.jetsurvey.ui.navigation

import androidx.fragment.app.Fragment
import java.security.InvalidParameterException

enum class Screen {Welcome, SignUp, SignIn, Survey}

fun Fragment.navigate(to: Screen, from: Screen) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }

    when(to) {
        Screen.Welcome -> {

        }

        Screen.SignUp -> {

        }

        Screen.SignIn -> {

        }

        Screen.Survey -> {

        }
    }
}
