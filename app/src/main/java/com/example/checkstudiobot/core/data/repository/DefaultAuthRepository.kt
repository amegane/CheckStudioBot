package com.example.checkstudiobot.core.data.repository

import android.app.PendingIntent
import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
): AuthRepository {
    private val _currentUserState = MutableStateFlow(auth.currentUser)
    override val currentUserState = _currentUserState.asStateFlow()

    // Use GitHub Copilot to write this file's code.
    override suspend fun requestGoogleForSignIn(signInClient: SignInClient): PendingIntent {
        val singInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(SERVER_CLIENT_ID)
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()
        return signInClient.beginSignIn(singInRequest).await().pendingIntent
    }

    // Use Studio Bot to write this file's code.
    override suspend fun signInWithGoogle(signInClient: SignInClient, intent: Intent) {
        val signInCredential = signInClient.getSignInCredentialFromIntent(intent)
        val authCredential = GoogleAuthProvider.getCredential(signInCredential.googleIdToken, null)
        auth.signInWithCredential(authCredential).addOnCompleteListener {
            if (it.isSuccessful) {
                _currentUserState.value = auth.currentUser
            } else {
                throw it.exception ?: FirebaseAuthException("Unknown error", "Unknown error")
            }
        }.addOnFailureListener {
            throw it
        }
    }

    companion object {
        private const val SERVER_CLIENT_ID = "SERVER_CLIENT_ID"
    }
}