package com.example.jetsurvey.ui.main.welcome

class PasswordState :
    TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)

class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {

    override val isValid: Boolean
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(): String? {
        return if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
    }
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean =
    isPasswordValid(password) && confirmedPassword == password

private fun isPasswordValid(password: String): Boolean = password.length > 3

private fun passwordValidationError(msg: String): String = "Invalid password"

private fun passwordConfirmationError(): String = "Password don't match"
