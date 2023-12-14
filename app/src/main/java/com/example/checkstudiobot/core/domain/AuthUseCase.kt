package com.example.checkstudiobot.core.domain

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.example.checkstudiobot.core.data.repository.AuthRepository
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authRepository: AuthRepository,
) {
    suspend fun signIn(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        val signInClient = Identity.getSignInClient(context)
        val pendingIntent = authRepository.requestGoogleForSignIn(signInClient)
        launcher.launch(IntentSenderRequest.Builder(pendingIntent).build())
    }

    suspend fun signInResult(result: ActivityResult) {
        if (result.resultCode != Activity.RESULT_OK) {
            Log.e("AuthUseCase", "signInResult: failed")
            return
        }

        val data = if (result.data != null) {
            result.data!!
        } else {
            Log.e("AuthUseCase", "signInResult: failed")
            return
        }

        val signInClient = Identity.getSignInClient(context)
        authRepository.signInWithGoogle(signInClient, data)
    }
}