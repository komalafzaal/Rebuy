package com.example.rebuy.di
import com.example.rebuy.Api.QuoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    fun provide_BASE_URL() : String = "https://quotable.io"

    @Provides
    @Singleton
    fun provideRetrofitBuilder(base_URL :String) :Retrofit =
        Retrofit.Builder()
            .baseUrl(base_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideQuotesService(retrofit: Retrofit) : QuoteService =
        retrofit.create(QuoteService::class.java)
}