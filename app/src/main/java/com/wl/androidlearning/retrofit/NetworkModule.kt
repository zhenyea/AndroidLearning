package com.wl.androidlearning.retrofit

import android.util.TimeUtils
import com.example.hiltsample.retrofit.RetrofitService
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
            .baseUrl("http://guolin.tech/")
            .client(okHttpClient)
            .build()

    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit):RetrofitService{

        return retrofit.create(RetrofitService::class.java)

    }
}