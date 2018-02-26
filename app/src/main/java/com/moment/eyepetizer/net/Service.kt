package com.moment.eyepetizer.net

import com.moment.eyepetizer.net.entity.*
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

    //标签详情页列标
    @GET("/api/v4/categories/detail/tab")
    fun categoriesDetail(@Query("id") id: Int): Observable<CategoryInfo>

    //标签详情页列标
    @GET
    fun categoriesTagList(@Url path: String,
                          @QueryMap map: HashMap<String, String>): Observable<Result>


    //全部分类
    @GET("/api/v4/categories/all")
    fun categoriesAll(): Observable<Result>

    //本周排行
    @GET("/api/v4/rankList")
    fun rankList(): Observable<RankList>

    //本周排行详情
    @GET
    fun rankListVideo(@Url path: String,
                      @QueryMap map: HashMap<String, String>): Observable<Result>

    //专题
    @GET("/api/v3/specialTopics")
    fun specialTopics(@Query("start") start: Int,
                      @Query("num") num: Int): Observable<Result>

    //360全景视频
    @GET("/api/v1/tag/index")
    fun tagIndex(@Query("id") id: Int): Observable<TagIndex>

    //近期话题
    @GET
    fun getDiscussList(@Url path: String,
                       @QueryMap map: HashMap<String, String>): Observable<Result>

    //视频相关推荐
    @GET("/api/v4/video/related")
    fun related(@Query("id") id: Int): Observable<Result>

}