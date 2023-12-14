package com.example.checkstudiobot.core.network.di

import com.example.checkstudiobot.core.network.firebase.DefaultFirebaseApi
import com.example.checkstudiobot.core.network.firebase.FirebaseApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Use Studio Bot to write this file's code.

@Module
@InstallIn(SingletonComponent::class)
interface ApiModule {
    @Binds
    fun bindFirebaseApi(
        defaultFirebaseApi: DefaultFirebaseApi,
    ): FirebaseApi
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireAuth(): FirebaseAuth {
        return Firebase.auth
    }
}
