package com.moment.eyepetizer.net

import com.moment.eyepetizer.net.entity.Categories
import com.moment.eyepetizer.net.entity.Result
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by moment on 16/5/24.
 */
interface Service {

    //首页-发现
    @GET("api/v5/index/tab/discovery")
    fun discovery(): Observable<Result>

    //首页-推荐
    @GET("api/v5/index/tab/allRec")
    fun allRec(@Query("page") page: Int): Observable<Result>

    //首页-日报
    /**
     * nextPageUrl : http://baobab.kaiyanapp.com/api/v5/index/tab/feed?date=1516842000000&num=1
     */
    @GET("api/v5/index/tab/feed")
    fun feed(@Query("date") date: Long,
             @Query("num") num: Int): Observable<Result>

    //首页-Category
    @GET("api/v5/index/tab/category/{id}")
    fun category(@Path("id") id: Int,
                 @Query("start") start: Int,
                 @Query("num") num: Int): Observable<Result>

    //首页-Category
    @GET("/api/v4/categories")
    fun categories(): Observable<List<Categories>>

    //关注
    @GET("/api/v4/tabs/follow")
    fun follow(@Query("start") start: Int,
               @Query("num") num: Int,
               @Query("follow") follow: Boolean,
               @Query("startId") startId: Int): Observable<Result>

    //关注
    @GET("/api/v3/queries/hot")
    fun hot(): Observable<List<String>>

    //关注
    @GET("/api/v1/search")
    fun search(@Query("query") query: String,
               @Query("start") start: Int,
               @Query("num") num: Int): Observable<Result>

    //POST
//    @FormUrlEncoded
//    @POST("api/v5/index/tab/discovery")
//    fun dd(@Field("category_id") category_id: String,
//           @Field("page") page: String,
//           @Field("size") size: String): Observable<String>


}