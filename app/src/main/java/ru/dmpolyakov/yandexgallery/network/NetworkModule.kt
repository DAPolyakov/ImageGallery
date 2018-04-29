package ru.dmpolyakov.yandexgallery.network

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.dmpolyakov.yandexgallery.API_YANDEX_DISK_BASE_URL
import ru.dmpolyakov.yandexgallery.DEFAULT_CONNECT_TIMEOUT
import ru.dmpolyakov.yandexgallery.network.services.PublicApi
import ru.dmpolyakov.yandexgallery.network.services.PublicService
import java.util.concurrent.TimeUnit


class NetworkModule {

    companion object {

        val publicService: PublicService by lazy {
            PublicService(retrofit.create(PublicApi::class.java))
        }

        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .build()
        }

        private val moshi: Moshi by lazy {
            Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
        }

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(okHttpClient)
                    .baseUrl(API_YANDEX_DISK_BASE_URL)
                    .build()
        }
    }

}