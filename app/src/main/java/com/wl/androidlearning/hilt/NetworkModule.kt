package com.wl.androidlearning.hilt

import android.util.TimeUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideOkhttpClients():OkHttpClient{
        return  OkHttpClient.Builder().connectTimeout(20,TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(20,TimeUnit.SECONDS).build()
    }
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{

        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.0.18/")
            .client(okHttpClient)
            .build()

    }
}