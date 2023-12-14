package com.example.checkstudiobot.core.data.repository

import android.app.PendingIntent
import android.content.Intent
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val currentUserState: StateFlow<FirebaseUser?>

    suspend fun requestGoogleForSignIn(signInClient: SignInClient): PendingIntent

    suspend fun signInWithGoogle(signInClient: SignInClient, intent: Intent)
}