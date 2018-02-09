package com.moment.eyepetizer.utils

import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by moment on 2018/1/5.
 */

object TimeUtils {

    /**
     * 1s==1000ms
     */
    private val TIME_MILLISECONDS = 1000
    /**
     * 时间中的分、秒最大值均为60
     */
    private val TIME_NUMBERS = 60
    /**
     * 时间中的小时最大值
     */
    private val TIME_HOURSES = 24
    /**
     * 一个月按30天计算
     */
    private val TIME_DAYS = 30

    var dateformat = "yyyy-MM-dd"
    var dateTimeformat = "yyyy-MM-dd HH:mm:ss"
    var dateTimeMillisformat = "yyyy-MM-dd HH:mm:ss:sss"

    val nowStr: String
        get() {
            val sdf = SimpleDateFormat(dateTimeformat, Locale.US)
            return sdf.format(Calendar.getInstance().time)
        }

    private fun timeFormat(timeMillis: Long, pattern: String): String {
        val format = SimpleDateFormat(pattern, Locale.CHINA)
        return format.format(Date(timeMillis))
    }

    private fun formatPhotoDate(time: Long): String = timeFormat(time, "yyyy-MM-dd hh:mm:ss")

    fun formatPhotoDate(path: String): String {
        val file = File(path)
        if (file.exists()) {
            val time = file.lastModified()
            return formatPhotoDate(time)
        }
        return "1970-01-01 00:00:00"
    }

    fun toSimpleDateString(dt: Date): String {
        val sdf = SimpleDateFormat(dateTimeformat, Locale.US)
        return sdf.format(dt)
    }

    fun secToTime(time: Int): String {
        var timeStr: String? = null
        var hour = 0
        var minute = 0
        var second = 0
        if (time <= 0)
            return "00:00"
        else {
            minute = time / 60
            if (minute < 60) {
                second = time % 60
                timeStr = unitFormat(minute) + ":" + unitFormat(second)
            } else {
                hour = minute / 60
                if (hour > 99)
                    return "99:59:59"
                minute = minute % 60
                second = time - hour * 3600 - minute * 60
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second)
            }
        }
        return timeStr
    }

    private fun unitFormat(i: Int): String {
        var retStr: String? = null
        retStr = if (i in 0..9)
            "0" + Integer.toString(i)
        else
            "" + i
        return retStr
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    private fun formartDate(date: Date): String = SimpleDateFormat(dateformat).format(date)

    /**
     * 毫秒数格式化日期
     *
     * @param millistimes
     * @return
     */
    private fun formartDate(millistimes: Long): String = SimpleDateFormat(dateformat).format(millistimes)

    fun formartStringToMillinstimes(time: String): Long {
        val date = parseDate(time)
        return date!!.time
    }

    /**
     * 转换日期
     *
     * @param date
     * @return
     */
    private fun parseDate(date: String): Date? {
        try {
            return SimpleDateFormat(dateformat).parse(date)
        } catch (e: ParseException) {
        }

        return null
    }

    /**
     * 格式化时间日期
     *
     * @param date
     * @return
     */
    fun formartDateTime(date: Date): String = SimpleDateFormat(dateTimeformat).format(date)

    /**
     * 转换时间日期
     *
     * @param dateTime
     * @return
     */
    fun parseDateTime(dateTime: String): Date? {
        try {
            return SimpleDateFormat(dateTimeformat).parse(dateTime)
        } catch (e: ParseException) {
        }

        return null
    }

    /**
     * 格式化时间日期
     *
     * @param millistimes
     * @return
     */
    fun formartDateTime(millistimes: Long): String =
            SimpleDateFormat(dateTimeformat).format(millistimes)

    /**
     * 格式化时间日期毫秒
     *
     * @param date
     * @return
     */
    fun formartDateTimeMillis(date: Date): String =
            SimpleDateFormat(dateTimeMillisformat).format(date)

    /**
     * 转换时间日期毫秒
     *
     * @param dateTime
     * @return
     */
    fun parseDateTimeMillis(dateTime: String): Date? {
        try {
            return SimpleDateFormat(dateTimeformat).parse(dateTime)
        } catch (e: ParseException) {
        }

        return null
    }

    /**
     * 获取当前时间距离指定日期时差的大致表达形式
     *
     * @param millistimes 日期
     * @return 时差的大致表达形式
     */
    fun getDiffTime(millistimes: Long): String {
        val strTime: String
        val diffTime = Math.abs(Date().time - millistimes)
        if (formartDate(millistimes) >= formartDate(Date())) {
            strTime = if (diffTime < TIME_NUMBERS * TIME_MILLISECONDS) {
                "刚刚"
            } else {
                val diffMin = (diffTime / (TIME_MILLISECONDS * TIME_NUMBERS)).toInt()
                if (diffMin < TIME_NUMBERS) {
                    diffMin.toString() + "分钟前"
                } else {
                    (diffMin / TIME_NUMBERS).toString() + "小时前"
                }
            }
        } else {
            val diffDay = (diffTime / (TIME_MILLISECONDS * TIME_NUMBERS * TIME_NUMBERS * TIME_HOURSES)).toInt()
            strTime = if (diffDay <= 1) {
                "昨天"
            } else if (diffDay < TIME_DAYS) {
                diffDay.toString() + "天前"
            } else {
                val diffMon = diffDay / TIME_DAYS
                if (diffMon < 3) {
                    diffMon.toString() + "月前"
                } else {
                    SimpleDateFormat(dateformat).format(millistimes)
                }
            }
        }

        return strTime
    }

    /**
     * 比较两个日期相差的天数
     */
    @Throws(ParseException::class)
    fun day(d1: String, d2: String): String {
        val format = SimpleDateFormat(dateformat)
        val date1 = format.parse(d1).time
        val date2 = format.parse(d2).time
        val dayOfMills = date2 - date1
        return (dayOfMills / (24 * 60 * 60 * 1000)).toString() + ""
    }
}
