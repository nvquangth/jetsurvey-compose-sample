package com.example.jetsurvey.ui.main.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jetsurvey.di.WelcomeViewModelFactory
import com.example.jetsurvey.ui.navigation.Screen
import com.example.jetsurvey.ui.navigation.navigate
import com.example.jetsurvey.ui.theme.JetsurveyTheme

class WelcomeFragment : Fragment() {

    private val viewModel: WelcomeViewModel by viewModels { WelcomeViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.Welcome)
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                JetsurveyTheme {
                    WelcomeScreen(
                        onEvent = { event ->
                            when (event) {
                                is WelcomeEvent.SignInSignUp -> {
                                    viewModel.handleContinue(event.email)
                                }
                                is WelcomeEvent.SignInAsGuest -> {
                                    viewModel.signInAsGuest()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
