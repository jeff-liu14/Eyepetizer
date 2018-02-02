package com.moment.eyepetizer.net

import android.util.Log

import java.io.IOException
import java.net.URLDecoder
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit

import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.moment.eyepetizer.utils.Constant
import com.moment.eyepetizer.utils.StringUtils

/**
 * Created by moment on 2018/2/1.
 */

class RetrofitUtils {

    // Install the all-trusting trust manager
    // Create an ssl socket factory with our all-trusting manager
    private val unsafeOkHttpClient: OkHttpClient
        get() {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) =
                        Unit

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) =
                        Unit

                override fun getAcceptedIssuers(): Array<X509Certificate?> = arrayOfNulls(0)
            })
            var sslContext: SSLContext? = null
            try {
                sslContext = SSLContext.getInstance("TLS")
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            try {
                sslContext!!.init(null, trustAllCerts, SecureRandom())
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            }

            val sslSocketFactory = sslContext!!.socketFactory
            val okBuilder = OkHttpClient.Builder()
            okBuilder.readTimeout(20, TimeUnit.SECONDS)
            okBuilder.connectTimeout(10, TimeUnit.SECONDS)
            okBuilder.writeTimeout(20, TimeUnit.SECONDS)
            okBuilder.sslSocketFactory(sslSocketFactory)
            okBuilder.hostnameVerifier { hostname, session -> true }


            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC

            okBuilder.addInterceptor { chain ->
                val builder = chain.request().newBuilder()
//                builder.addHeader("Content-Type", "application/form-data; charset=UTF-8")
                builder.addHeader("udid", "1dad62050ee54c10965021ed1bff209cdee1f09e")
                builder.addHeader("vc", "256")
                builder.addHeader("vn", "3.14")
                builder.addHeader("deviceModel", "MIX%202")

                builder.addHeader("first_channel", "eyepetizer_yingyongbao_market")
                builder.addHeader("last_channel", "eyepetizer_yingyongbao_market")
                builder.addHeader("system_version_code", "25")
                chain.proceed(builder.build())
            }
            okBuilder.addInterceptor(LogInterceptor())

            return okBuilder.build()
        }

    fun with(): RetrofitUtils {
        val client = unsafeOkHttpClient
        retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build()
        return this
    }

    internal class LogInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val startTime = System.currentTimeMillis()
            val response = chain.proceed(chain.request())
            val endTime = System.currentTimeMillis()
            val duration = endTime - startTime
            val mediaType = response.body()!!.contentType()
            val content = response.body()!!.string()
            if (true) {
                Log.d(TAG, "\n")
                Log.d(TAG, "\n")
                Log.d(TAG, "----------Start----------------")
                Log.d(TAG, "| " + request.toString())
                val headers = request.headers()
                Log.d(TAG, "| RequestHeaders:{" + headers.toString() + "}")
                val method = request.method()
                if ("POST" == method) {
                    val sb = StringBuilder()
                    if (request.body() is FormBody) {
                        val body = request.body() as FormBody?
                        for (i in 0 until body!!.size()) {
                            val str = URLDecoder.decode(body.encodedValue(i), "utf-8")
                            if (StringUtils.isNumeric(str)) {
                                sb.append("\"" + body.encodedName(i) + "\"" + ":" + str + ",")
                            } else {
                                sb.append("\"" + body.encodedName(i) + "\"" + ":" + "\"" + str + "\"" + ",")
                            }
                        }
                        sb.delete(sb.length - 1, sb.length)
                        Log.d(TAG, "| RequestParams:{" + sb.toString() + "}")
                    }
                }
                Log.d(TAG, "| Response:" + content)
                Log.d(TAG, "----------End:" + duration + "毫秒----------")
                Log.d(TAG, "\n")
                Log.d(TAG, "\n")
            }
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build()
        }

        companion object {
            var TAG = "LogInterceptor"
        }
    }


    fun build(): Service = retrofit!!.create(Service::class.java)

    companion object {
        private var retrofit: Retrofit? = null
    }
}
