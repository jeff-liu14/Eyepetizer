package com.moment.eyepetizer.utils

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule


/**
 * Created by moment on 2018/2/7.
 */
@GlideModule
class DiskCacheModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        var calculator = MemorySizeCalculator.Builder(context.applicationContext)
                .setMemoryCacheScreens(2f)
                .build()
        val calculatorBitmap = MemorySizeCalculator.Builder(context)
                .setBitmapPoolScreens(3f)
                .build()
        val diskCacheSizeBytes = 1024 * 1024 * 100 // 100 MB
        builder.setDiskCache(InternalCacheDiskCacheFactory(context.applicationContext, "glideImageCache", diskCacheSizeBytes.toLong()))
                .setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
                .setBitmapPool(LruBitmapPool(calculatorBitmap.bitmapPoolSize.toLong()))
    }
}