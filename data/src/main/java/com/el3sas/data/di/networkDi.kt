package com.el3sas.data.di

import android.content.Context
import android.net.ConnectivityManager
import com.el3sas.data.network.EndPoint
import com.el3sas.data.network.newsList.NewsListService
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonNetworkObjDI {

    @Provides
    @Singleton
    @Named("provideNonCashedRetrofit")
    fun provideNonCashedRetrofit(
        @Named("provideNonCashedOkhttp") okHttpClient: OkHttpClient, builder: Retrofit.Builder
    ): Retrofit {
        return builder.client(okHttpClient).build()
    }

    @Provides
    @Singleton
    @Named("provideCashedRetrofit")
    fun provideCashedRetrofit(
        @Named("provideCashedOkhttp") okHttpClient: OkHttpClient, builder: Retrofit.Builder
    ): Retrofit {
        return builder.client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(moshi: Moshi): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(EndPoint.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    @Named("provideCashedOkhttp")
    fun provideCashedOkhttp(
        @ApplicationContext context: Context, okBuilder: OkHttpClient.Builder
    ): OkHttpClient {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val cache = Cache(context.cacheDir, cacheSize)
        return okBuilder.addNetworkInterceptor {
            val response: Response = it.proceed(it.request())
            val cacheControl =
                CacheControl.Builder().maxAge(10, TimeUnit.DAYS).onlyIfCached().build()

            response.newBuilder().header("Cache-Control", cacheControl.toString())
                .removeHeader("Pragma").build()
        }.addInterceptor {
            val builder: Request.Builder = it.request().newBuilder()
            if (isInternetAvailable(context).not()) {
                builder.cacheControl(CacheControl.FORCE_CACHE)
            }
            it.proceed(builder.build())
        }.cache(cache).build()
    }

    @Provides
    @Singleton
    @Named("provideNonCashedOkhttp")
    fun provideNonCashedOkhttp(
        okBuilder: OkHttpClient.Builder
    ): OkHttpClient {
        return okBuilder.build()
    }

    @Provides
    @Singleton
    fun provideMainOkhttp(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder().addInterceptor(
            LoggingInterceptor.Builder().setLevel(Level.BASIC).log(Platform.INFO).request(" ")
                .response(" ").addQueryParam("apiKey", "284af891f32f4a00b28febe91bc2e04b").build()
        ).addInterceptor { chain ->
            val url = chain.request().url.newBuilder().build()
            val requestBuilder =
                chain.request().newBuilder().url(url).addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json").build()
            chain.proceed(requestBuilder)
        }.connectTimeout(EndPoint.connectTimeout, TimeUnit.MINUTES)
            .writeTimeout(EndPoint.writeTimeout, TimeUnit.MINUTES)
            .readTimeout(EndPoint.readTimeout, TimeUnit.MINUTES)
            .callTimeout(EndPoint.callTimeout, TimeUnit.MINUTES)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object NetworkClientsProvidersDI {
    @Provides
    @ViewModelScoped
    fun providerNewsListService(@Named("provideCashedRetrofit") retrofit: Retrofit): NewsListService =
        retrofit.create(NewsListService::class.java)
}


fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.isDefaultNetworkActive
}


@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher
