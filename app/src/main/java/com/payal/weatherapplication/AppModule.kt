package com.payal.weatherapplication

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(X_RAPID_KEY, X_RAPID_API_HOST))
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun getApis(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provieRepositoryImpl(api:Api): Repository{
        return RepositoryImpl(api)
    }

}

class AuthInterceptor(private val rapidApi: String, private val rapidApiHost: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", rapidApi)
            .addHeader("X-RapidAPI-Host", rapidApiHost)
            .build()
        return chain.proceed(request)
    }
}