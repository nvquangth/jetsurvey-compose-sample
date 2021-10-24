package com.example.jetsurvey.data.repository

import com.example.jetsurvey.data.model.User

object UserRepository {

    private var _user: User = User.NoUserLoggedIn
    val user: User = _user

    fun signIn(email: String, password: String) {
        _user = User.LoggedInUser(email)
    }

    fun signUp(email: String, password: String) {
        _user = User.LoggedInUser(email)
    }

    fun signInAsGuest() {
        _user = User.GuestUser
    }

    // if the email contains "sign up" we consider it unknown
    fun isKnownUserEmail(email: String): Boolean = !email.contains("signup")
}
