package org.briarproject.android.dontkillmelib

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.Context.POWER_SERVICE
import android.content.Intent
import android.content.pm.PackageManager.MATCH_DEFAULT_ONLY
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.PowerManager
import android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
import java.io.IOException
import java.util.Scanner

public object PowerUtils {

    private const val PACKAGE_NAME_HUAWEI = "com.huawei.systemmanager"
    private const val CLASS_NAME_POWER_MANAGER =
        "$PACKAGE_NAME_HUAWEI.power.ui.HwPowerManagerActivity"
    private const val CLASS_NAME_PROTECTED_APPS =
        "$PACKAGE_NAME_HUAWEI.optimize.process.ProtectActivity"

    @JvmStatic
    public fun needsDozeWhitelisting(ctx: Context): Boolean {
        if (SDK_INT < 23) return false
        val pm = ctx.getSystemService(POWER_SERVICE) as PowerManager
        return !pm.isIgnoringBatteryOptimizations(ctx.packageName)
    }

    @JvmStatic
    @TargetApi(23)
    @SuppressLint("BatteryLife")
    public fun getDozeWhitelistingIntent(ctx: Context): Intent = Intent().apply {
        action = ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
        data = Uri.parse("package:" + ctx.packageName)
    }

    /**
     * Determine whether a Huawei "Protected apps" feature is available on the
     * device.
     */
    @JvmStatic
    public fun huaweiAppLaunchNeedsToBeShown(context: Context): Boolean {
        // "App launch" was introduced in EMUI 8 (Android 8.0)
        if (SDK_INT < 26) return false
        val pm = context.packageManager
        val resolveInfos = pm.queryIntentActivities(huaweiProtectedAppsIntent, MATCH_DEFAULT_ONLY)
        return resolveInfos.isNotEmpty()
    }

    /**
     * Determine whether a Huawei "Protected apps" feature is available on the
     * device.
     */
    @JvmStatic
    public fun huaweiProtectedAppsNeedsToBeShown(context: Context): Boolean {
        // "Protected apps" no longer exists on Huawei EMUI 5.0 (Android 7.0)
        if (SDK_INT >= 24) return false
        val pm = context.packageManager
        val resolveInfos = pm.queryIntentActivities(huaweiPowerManagerIntent, MATCH_DEFAULT_ONLY)
        return resolveInfos.isNotEmpty()
    }

    @JvmStatic
    public val huaweiPowerManagerIntent: Intent = Intent().apply {
        setClassName(PACKAGE_NAME_HUAWEI, CLASS_NAME_POWER_MANAGER)
    }

    @JvmStatic
    public val huaweiProtectedAppsIntent: Intent = Intent().apply {
        setClassName(PACKAGE_NAME_HUAWEI, CLASS_NAME_PROTECTED_APPS)
    }

    @JvmStatic
    public val isXiaomiOrRedmiDevice: Boolean
        get() = "Xiaomi".equals(Build.BRAND, ignoreCase = true) ||
            "Redmi".equals(Build.BRAND, ignoreCase = true)

    @JvmStatic
    public val isMiuiTenOrLater: Boolean
        get() {
            var version = getSystemProperty("ro.miui.ui.version.name")
            if (version == null || version == "") return false
            version = version.replace("[^\\d]".toRegex(), "")
            return try {
                version.toInt() >= 10
            } catch (e: NumberFormatException) {
                false
            }
        }

    private fun getSystemProperty(propName: String): String? {
        return try {
            val p = Runtime.getRuntime().exec("getprop $propName")
            val s = Scanner(p.inputStream)
            val line = s.nextLine()
            s.close()
            line
        } catch (e: SecurityException) {
            null
        } catch (e: IOException) {
            null
        }
    }
}
