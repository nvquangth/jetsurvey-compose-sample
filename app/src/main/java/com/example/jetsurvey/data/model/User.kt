package com.example.jetsurvey.data.model

sealed class User {

    data class LoggedInUser(val email: String) : User()

    object GuestUser : User()

    object NoUserLoggedIn : User()
}
