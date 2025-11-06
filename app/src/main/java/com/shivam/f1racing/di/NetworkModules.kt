package com.shivam.f1racing.di


import com.google.gson.Gson
import com.shivam.f1racing.data.RacingInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mocki.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAPIService(retrofit: Retrofit): RacingInterface =
        retrofit.create(RacingInterface::class.java)

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson() // Provides a default Gson instance
    }

}
