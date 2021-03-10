package com.nurbk.ps.countryweather.utils

import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.annotation.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.nurbk.ps.countryweather.R
import java.lang.reflect.InvocationTargetException
import java.util.*

object AppUtil {
    private var fastOutSlowIn: Interpolator? = null

    /**
     * Get timestamp of start of day 00:00:00
     *
     * @param calendar instance of [Calendar]
     * @return timestamp
     */
    fun getStartOfDayTimestamp(calendar: Calendar): Long {
        val newCalendar = Calendar.getInstance(TimeZone.getDefault())
        newCalendar.timeInMillis = calendar.timeInMillis
        newCalendar[Calendar.HOUR_OF_DAY] = 0
        newCalendar[Calendar.MINUTE] = 0
        newCalendar[Calendar.SECOND] = 0
        newCalendar[Calendar.MILLISECOND] = 0
        return newCalendar.timeInMillis
    }

    /**
     * Get timestamp of end of day 23:59:59
     *
     * @param calendar instance of [Calendar]
     * @return timestamp
     */
    fun getEndOfDayTimestamp(calendar: Calendar): Long {
        val newCalendar = Calendar.getInstance(TimeZone.getDefault())
        newCalendar.timeInMillis = calendar.timeInMillis
        newCalendar[Calendar.HOUR_OF_DAY] = 23
        newCalendar[Calendar.MINUTE] = 59
        newCalendar[Calendar.SECOND] = 59
        newCalendar[Calendar.MILLISECOND] = 0
        return newCalendar.timeInMillis
    }

    /**
     * Add days to calendar and return result
     *
     * @param cal  instance of [Calendar]
     * @param days number of days
     * @return instance of [Calendar]
     */
    fun addDays(cal: Calendar, days: Int): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.timeInMillis = cal.timeInMillis
        calendar.add(Calendar.DATE, days)
        return calendar
    }

    /**
     * Set icon to imageView according to weather code status
     *
     * @param context     instance of [Context]
     * @param imageView   instance of [android.widget.ImageView]
     * @param weatherCode code of weather status
     */
    fun setWeatherIcon(context: Context?, imageView: AppCompatImageView, weatherCode: Int) {
        if (weatherCode / 100 == 2) {
            Glide.with(context!!).load(R.drawable.ic_storm_weather).into(imageView)
        } else if (weatherCode / 100 == 3) {
            Glide.with(context!!).load(R.drawable.ic_rainy_weather).into(imageView)
        } else if (weatherCode / 100 == 5) {
            Glide.with(context!!).load(R.drawable.ic_rainy_weather).into(imageView)
        } else if (weatherCode / 100 == 6) {
            Glide.with(context!!).load(R.drawable.ic_snow_weather).into(imageView)
        } else if (weatherCode / 100 == 7) {
            Glide.with(context!!).load(R.drawable.ic_unknown).into(imageView)
        } else if (weatherCode == 800) {
            Glide.with(context!!).load(R.drawable.ic_clear_day).into(imageView)
        } else if (weatherCode == 801) {
            Glide.with(context!!).load(R.drawable.ic_few_clouds).into(imageView)
        } else if (weatherCode == 803) {
            Glide.with(context!!).load(R.drawable.ic_broken_clouds).into(imageView)
        } else if (weatherCode / 100 == 8) {
            Glide.with(context!!).load(R.drawable.ic_cloudy_weather).into(imageView)
        }
    }

    /**
     * Show fragment with fragment manager with animation parameter
     *
     * @param fragment        instance of [Fragment]
     * @param fragmentManager instance of [FragmentManager]
     * @param withAnimation   boolean value
     */
    fun showFragment(
        fragment: Fragment?,
        fragmentManager: FragmentManager,
        withAnimation: Boolean
    ) {
        val transaction = fragmentManager.beginTransaction()
        if (withAnimation) {
            transaction.setCustomAnimations(R.anim.slide_up_anim, R.anim.slide_down_anim)
        } else {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
        transaction.add(R.id.content, fragment!!).addToBackStack(null).commit()
    }

    /**
     * Get time of calendar as 00:00 format
     *
     * @param calendar instance of [Calendar]
     * @param context  instance of [Context]
     * @return string value
     */
    fun getTime(calendar: Calendar, context: Context): String {
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val hourString: String
        hourString = if (hour < 10) {
            String.format(Locale.getDefault(), context.getString(R.string.zero_label), hour)
        } else {
            String.format(Locale.getDefault(), "%d", hour)
        }
        val minuteString: String
        minuteString = if (minute < 10) {
            String.format(Locale.getDefault(), context.getString(R.string.zero_label), minute)
        } else {
            String.format(Locale.getDefault(), "%d", minute)
        }
        return "$hourString:$minuteString"
    }

    /**
     * Get animation file according to weather status code
     *
     * @param weatherCode int weather status code
     * @return id of animation json file
     */
    fun getWeatherAnimation(weatherCode: Int, textView: TextSwitcher? = null): Int {
        if (weatherCode / 100 == 2) {
            textView?.let {
                it.setText("Storm\nWeather")
            }
            return R.raw.storm_weather
        } else if (weatherCode / 100 == 3) {
            textView?.let {
                it.setText("Rainy\nWeather")
            }
            return R.raw.rainy_weather
        } else if (weatherCode / 100 == 5) {
            textView?.let {
                it.setText("Rainy\nWeather")
            }
            return R.raw.rainy_weather
        } else if (weatherCode / 100 == 6) {
            textView?.let {
                it.setText("Snow\nWeather")
            }
            return R.raw.snow_weather
        } else if (weatherCode / 100 == 7) {
            textView?.let {
                it.setText("Unknown")
            }
            return R.raw.unknown
        } else if (weatherCode == 800) {
            textView?.let {
                it.setText("Clear\nDay")
            }
            return R.raw.clear_day
        } else if (weatherCode == 801) {
            textView?.let {
                it.setText("Few\nClouds")
            }
            return R.raw.few_clouds
        } else if (weatherCode == 803) {
            textView?.let {
                it.setText("Broken\nClouds")
            }
            return R.raw.broken_clouds
        } else if (weatherCode / 100 == 8) {
            textView?.let {
                it.setText("Cloudy\nWeather")
            }
            return R.raw.cloudy_weather
        }
        return R.raw.unknown
    }

    /**
     * Get weather status string according to weather status code
     *
     * @param weatherCode weather status code
     * @param isRTL       boolean value
     * @return String weather status
     */
    fun getWeatherStatus(weatherCode: Int, isRTL: Boolean): String {
        if (weatherCode / 100 == 2) {
            return if (isRTL) {
                ConstanceString.WEATHER_STATUS_PERSIAN[0]
            } else {
                ConstanceString.WEATHER_STATUS[0]
            }
        } else if (weatherCode / 100 == 3) {
            return if (isRTL) {
                ConstanceString.WEATHER_STATUS_PERSIAN[1]
            } else {
                ConstanceString.WEATHER_STATUS[1]
            }
        } else if (weatherCode / 100 == 5) {
            return if (isRTL) {
                ConstanceString.WEATHER_STATUS_PERSIAN[2]
            } else {
                ConstanceString.WEATHER_STATUS[2]
            }
        } else if (weatherCode / 100 == 6) {
            return if (isRTL) {
                ConstanceString.WEATHER_STATUS_PERSIAN[3]
            } else {
                ConstanceString.WEATHER_STATUS[3]
            }
        } else if (weatherCode / 100 == 7) {
            return if (isRTL) {
                ConstanceString.WEATHER_STATUS_PERSIAN[4]
            } else {
                ConstanceString.WEATHER_STATUS[4]
            }
        } else if (weatherCode == 800) {
            return if (isRTL) {
                ConstanceString.WEATHER_STATUS_PERSIAN[5]
            } else {
                ConstanceString.WEATHER_STATUS[5]
            }
        } else if (weatherCode == 801) {
            return if (isRTL) {
                ConstanceString.WEATHER_STATUS_PERSIAN[6]
            } else {
                ConstanceString.WEATHER_STATUS[6]
            }
        } else if (weatherCode == 803) {
            return if (isRTL) {
                ConstanceString.WEATHER_STATUS_PERSIAN[7]
            } else {
                ConstanceString.WEATHER_STATUS[7]
            }
        } else if (weatherCode / 100 == 8) {
            return if (isRTL) {
                ConstanceString.WEATHER_STATUS_PERSIAN[8]
            } else {
                ConstanceString.WEATHER_STATUS[8]
            }
        }
        return if (isRTL) {
            ConstanceString.WEATHER_STATUS_PERSIAN[4]
        } else {
            ConstanceString.WEATHER_STATUS[4]
        }
    }

    /**
     * If thirty minutes is pass from parameter return true otherwise return false
     *
     * @param lastStored timestamp
     * @return boolean value
     */
    fun isTimePass(lastStored: Long): Boolean {
        return System.currentTimeMillis() - lastStored > ConstanceString.TIME_TO_PASS
    }

    /**
     * Showing dialog for set api key value
     *
     * @param context  instance of [Context]
     * @param prefser  instance of [Prefser]
     * @param listener instance of [OnSetApiKeyEventListener]
     */
//    fun showSetAppIdDialog(context: Context, prefser: Prefser, listener: OnSetApiKeyEventListener) {
//        val dialog = Dialog(context)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
//        dialog.setContentView(R.layout.dialog_set_appid)
//        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
//        dialog.setCancelable(false)
//        val lp = WindowManager.LayoutParams()
//        lp.copyFrom(dialog.window!!.attributes)
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//        dialog.window!!.attributes = lp
//        dialog.findViewById<View>(R.id.open_openweather_button).setOnClickListener {
//            val i = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse(ConstanceString.OPEN_WEATHER_MAP_WEBSITE)
//            )
//            context.startActivity(i)
//        }
//        dialog.findViewById<View>(R.id.store_button).setOnClickListener {
//            val apiKeyEditText: AppCompatEditText = dialog.findViewById(R.id.api_key_edit_text)
//            val apiKey = apiKeyEditText.text.toString()
//            if (apiKey != "") {
//                prefser.put(ConstanceString.API_KEY, apiKey)
//                listener.setApiKey()
//                dialog.dismiss()
//            }
//        }
//        dialog.show()
//    }

    /**
     * Set text of textView with html format of html parameter
     *
     * @param textView instance [TextView]
     * @param html     String
     */
    @SuppressLint("ClickableViewAccessibility")
    fun setTextWithLinks(textView: TextView, html: CharSequence?) {
        textView.text = html
        textView.setOnTouchListener(OnTouchListener { v, event ->
            val action = event.action
            if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN
            ) {
                var x = event.x.toInt()
                var y = event.y.toInt()
                val widget = v as TextView
                x -= widget.totalPaddingLeft
                y -= widget.totalPaddingTop
                x += widget.scrollX
                y += widget.scrollY
                val layout = widget.layout
                val line = layout.getLineForVertical(y)
                val off = layout.getOffsetForHorizontal(line, x.toFloat())
                val link = Spannable.Factory.getInstance()
                    .newSpannable(widget.text)
                    .getSpans(off, off, ClickableSpan::class.java)
                if (link.size != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget)
                    }
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    /**
     * Change string to html format
     *
     * @param htmlText String text
     * @return String text
     */
    fun fromHtml(htmlText: String?): CharSequence? {
        if (TextUtils.isEmpty(htmlText)) {
            return null
        }
        val spanned: CharSequence
        spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(htmlText)
        }
        return trim(spanned)
    }

    /**
     * Trim string text
     *
     * @param charSequence String text
     * @return String text
     */
    private fun trim(charSequence: CharSequence): CharSequence {
        if (TextUtils.isEmpty(charSequence)) {
            return charSequence
        }
        var end = charSequence.length - 1
        while (Character.isWhitespace(charSequence[end])) {
            end--
        }
        return charSequence.subSequence(0, end + 1)
    }

    /**
     * Check version of SDK
     *
     * @param version int SDK version
     * @return boolean value
     */
    fun isAtLeastVersion(version: Int): Boolean {
        return Build.VERSION.SDK_INT >= version
    }

    /**
     * Check current direction of application. if is RTL return true
     *
     * @param context instance of [Context]
     * @return boolean value
     */
    fun isRTL(context: Context): Boolean {
        val locale = ConfigurationCompat.getLocales(context.resources.configuration)[0]
        val directionality = Character.getDirectionality(locale.displayName[0]).toInt()
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT.toInt() || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC.toInt()
    }

    /**
     * Network status functions.
     */
    @SuppressLint("StaticFieldLeak")
    private var sApplication: Application? = null
    private fun init(app: Application?) {
        if (sApplication == null) {
            if (app == null) {
                sApplication = applicationByReflect
            } else {
                sApplication = app
            }
        } else {
            if (app != null && app.javaClass != sApplication!!.javaClass) {
                sApplication = app
            }
        }
    }

    val app: Application?
        get() {
            if (sApplication != null) return sApplication
            val app = applicationByReflect
            init(app)
            return app
        }
    private val applicationByReflect: Application
        private get() {
            try {
                @SuppressLint("PrivateApi") val activityThread =
                    Class.forName("android.app.ActivityThread")
                val thread = activityThread.getMethod("currentActivityThread").invoke(null)
                val app = activityThread.getMethod("getApplication").invoke(thread)
                    ?: throw NullPointerException("u should init first")
                return app as Application
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
            throw NullPointerException("u should init first")
        }

    /**
     * If network connection is connect, return true
     *
     * @return boolean value
     */
    @get:RequiresPermission(permission.ACCESS_NETWORK_STATE)
    val isNetworkConnected: Boolean
        get() {
            val info = activeNetworkInfo
            return info != null && info.isConnected
        }

    /**
     * Get activity network info instace
     *
     * @return instance of [NetworkInfo]
     */
    @get:RequiresPermission(permission.ACCESS_NETWORK_STATE)
    private val activeNetworkInfo: NetworkInfo?
        private get() {
            val cm = app!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return null
            return cm.activeNetworkInfo
        }

    /**
     * Determine if the navigation bar will be on the bottom of the screen, based on logic in
     * PhoneWindowManager.
     */
    fun isNavBarOnBottom(context: Context): Boolean {
        val res = context.resources
        val cfg = context.resources.configuration
        val dm = res.displayMetrics
        val canMove = dm.widthPixels != dm.heightPixels &&
                cfg.smallestScreenWidthDp < 600
        return !canMove || dm.widthPixels < dm.heightPixels
    }

    fun getFastOutSlowInInterpolator(context: Context?): Interpolator? {
        if (fastOutSlowIn == null) {
            fastOutSlowIn = AnimationUtils.loadInterpolator(
                context,
                R.interpolator.fast_out_slow_in
            )
        }
        return fastOutSlowIn
    }

    /**
     * Set the alpha component of `color` to be `alpha`.
     */
    @CheckResult
    @ColorInt
    fun modifyAlpha(
        @ColorInt color: Int,
        alpha: Int
    ): Int {
        return color and 0x00ffffff or (alpha shl 24)
    }

    /**
     * Set the alpha component of `color` to be `alpha`.
     */
    @CheckResult
    @ColorInt
    fun modifyAlpha(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) alpha: Float
    ): Int {
        return modifyAlpha(color, (255f * alpha).toInt())
    }
}
