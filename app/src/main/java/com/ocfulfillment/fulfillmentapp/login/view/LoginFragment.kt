package com.ocfulfillment.fulfillmentapp.login.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ocfulfillment.fulfillmentapp.R
import com.ocfulfillment.fulfillmentapp.data.login.LoginResultState
import com.ocfulfillment.fulfillmentapp.data.login.LoginViewState
import com.ocfulfillment.fulfillmentapp.databinding.FragmentLoginBinding
import com.ocfulfillment.fulfillmentapp.login.hideKeyboard
import com.ocfulfillment.fulfillmentapp.login.viewmodel.LoginViewModel
import com.ocfulfillment.fulfillmentapp.navigation.Navigation
import org.koin.android.ext.android.inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val navigation by lazy { Navigation(findNavController()) }

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.signInButton.setOnClickListener { loginUser() }

        viewModel.state.observe(viewLifecycleOwner, Observer(::onStateChanged))
    }

    private fun loginUser() {
        val email = binding.emailAddress.text.toString()
        val password = binding.password.text.toString()
        viewModel.loginUserWithCredentials(email, password)
    }

    private fun onStateChanged(state: LoginResultState) {

        hideKeyboard()

        when (state) {
            LoginResultState.Cancelled -> Snackbar.make(
                binding.root,
                getString(R.string.login_cancel_text),
                Snackbar.LENGTH_SHORT
            ).show()

            LoginResultState.Failure -> Snackbar.make(
                binding.root,
                getString(R.string.login_failure_text),
                Snackbar.LENGTH_SHORT
            ).show()

            is LoginResultState.Successful -> navigation.toPickupList()

            LoginViewState.Empty -> Snackbar.make(
                binding.root,
                getString(R.string.login_empty_text),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}