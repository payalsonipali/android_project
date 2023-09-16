package com.payal.digital_movies.firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.lang.Exception

class AnalyticsTracker {

    fun log(message:String){
        FirebaseCrashlyticsModule.provideFirebaseCrashlytics().log(message)
    }

    fun exception(error:Exception){
        FirebaseCrashlyticsModule.provideFirebaseCrashlytics().recordException(error)
    }
}

@Module
@InstallIn(SingletonComponent::class)
private object FirebaseCrashlyticsModule {

    @Provides
    fun provideFirebaseCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }
}