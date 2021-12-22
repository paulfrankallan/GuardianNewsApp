package com.corbstech.guardian.app

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.corbstech.guardian.R
import com.corbstech.guardian.articles.api.GuardianService
import com.corbstech.guardian.articles.data.ArticleMapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

@InstallIn(ViewModelComponent::class)
@Module
class GuardianNewsAppModule {

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun providesWeatherAppClient(
        @ApplicationContext context: Context
    ) = createGuardianService(context)

    @Provides
    fun provideArticleMapper(): ArticleMapper {
        return ArticleMapper()
    }

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences(
        "${context.packageName}.${PREFERENCE_FILE_KEY}",
        Context.MODE_PRIVATE
    )

    private fun createGuardianService(context: Context): GuardianService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(createOkHttpClient(context.resources))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GuardianService::class.java)
    }

    private fun createOkHttpClient(resources: Resources): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(getAuthInterceptor(resources))
        clientBuilder.addInterceptor(loggingInterceptor)
        return clientBuilder.build()
    }

    private fun getAuthInterceptor(resources: Resources): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val hb = original.headers.newBuilder()
                hb.add(HEADER_API_KEY, resources.getString(R.string.guardian_api_key))
                return chain.proceed(original.newBuilder().headers(hb.build()).build())
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://content.guardianapis.com"
        private const val HEADER_API_KEY = "api-key"
        private const val PREFERENCE_FILE_KEY = "preferences"
    }
}