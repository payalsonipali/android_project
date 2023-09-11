package com.payal.unify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.payal.unify.view.mandateDetails
import com.payal.unify.viewmodel.MandateDetailsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(MandateDetailsViewModel::class.java)

        setContent {
            mandateDetails(viewModel = viewModel)
        }
    }
}