package com.dc.tast1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dc.tast1.biometric.BiometricAuthListener
import com.dc.tast1.biometric.BiometricUtil
import com.dc.tast1.databinding.ActivityMainBinding
import com.dc.tast1.nav_graph.Screen
import com.dc.tast1.nav_graph.SetupNavGraph
import com.dc.tast1.presentation.viewmodel.ProfileViewModel
import com.dc.tast1.presentation.viewmodel.SharedViewModel
import com.dc.tast1.presentation.viewmodel.SplashViewModel
import com.dc.tast1.ui.theme.Tast1Theme
import com.firebase.ui.auth.AuthUI
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BiometricAuthListener {

    private lateinit var binding: ActivityMainBinding

    lateinit var navHostController: NavHostController

    val sharedViewModel: SharedViewModel by viewModels()
    val profileViewModel: ProfileViewModel by viewModels()
    val splashViewModel: SplashViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Tast1Theme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        navHostController = rememberNavController()
                        SetupNavGraph(
                            navHostController = navHostController,
                            splashViewModel = splashViewModel,
                            launchLoginFlow = ::launchLoginFlow,
                            launchImagePickerFlow = ::lauchImagePickerFlow,
                            profileViewModel = profileViewModel,
                            sharedViewModel = sharedViewModel,
                            showBiometric = ::showBiometric
                        )
                    }
                }
            }
        }

        registerLoginLauncher()
        registerImagePickerLauncher()
    }

    private lateinit var loginLauncher: ActivityResultLauncher<Intent>
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    // Step 1: Registration
    private fun registerLoginLauncher() {
        Log.d("TAG", "Inside setupLoginLauncher")
        loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            Log.d("TAG", "Inside ActivityResult $result")
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("TAG", "Inside ResultLambda ")
                loginHandler()
            } else Toast.makeText(this, "Not able to Login, Try Again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerImagePickerLauncher() {
        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val uri = result.data

                when (resultCode) {
                    Activity.RESULT_OK -> {
                        if (uri != null) {
                            Log.d("TAG", "registerImagePickerLauncher: ${uri.data}")
                            profileViewModel.profileState.value =
                                profileViewModel.profileState.value!!.copy(displayPhoto = uri.data.toString())
                            Log.d(
                                "TAG",
                                "registerImagePickerLauncher: ${profileViewModel.profileState.value}"
                            )
                            sharedViewModel.addSenderProfile(profileViewModel.profileState.value!!)
                        } else Toast.makeText(this, "Not able to Pick Image", Toast.LENGTH_SHORT)
                            .show()
                    }
                    ImagePicker.RESULT_ERROR -> {
                        Toast.makeText(this, ImagePicker.getError(uri), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                    }
                }

            }
    }

    // Step 2: Launcher
    private fun launchLoginFlow(loginHandler: (() -> Unit)) {
        this.loginHandler = loginHandler

        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )
            )
            .build()

        loginLauncher.launch(intent)
    }

    private fun lauchImagePickerFlow() {
        Log.d("TAG", "lauchImagePickerFlow: launchImagePickerFlow")
        ImagePicker.with(this)
            .cropSquare()
            .compress(1024)
            .createIntent { intent ->
                imagePickerLauncher.launch(intent)
            }
    }

    private fun showBiometric() {
        Log.d("TAG", "showBiometric MainActivity")
        BiometricUtil.showBiometricPrompt(
            activity = this,
            listener = this,
            cryptoObject = null,
            allowDeviceCredential = true
        )
    }

    // Step 3: Handler (to get the result)
    private lateinit var loginHandler: (() -> Unit)

    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
        Toast.makeText(this, "Biometric success", Toast.LENGTH_SHORT)
            .show()
        navHostController.navigate(route = Screen.Welcome.route) {
            popUpTo(Screen.Profile.route) { inclusive = true }
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    override fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String) {
        Toast.makeText(this, "Biometric login. Error: $errorMessage", Toast.LENGTH_SHORT)
            .show()
        BiometricUtil.lunchBiometricSettings(this)
    }
}


