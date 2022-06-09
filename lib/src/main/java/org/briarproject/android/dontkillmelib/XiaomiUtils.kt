package org.briarproject.android.dontkillmelib

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.MATCH_DEFAULT_ONLY
import android.os.Build.BRAND
import android.os.Build.VERSION.INCREMENTAL
import android.util.Log

public object XiaomiUtils {

    private const val TAG = "XiaomiUtils"

    private const val PACKAGE_NAME = "com.miui.securitycenter"
    private const val CLASS_NAME = "com.miui.securityscan.ui.settings.SettingsActivity"

    @JvmStatic
    public val isXiaomiOrRedmiDevice: Boolean
        get() = "Xiaomi".equals(BRAND, ignoreCase = true) ||
                "Redmi".equals(BRAND, ignoreCase = true)

    @JvmStatic
    public val isMiuiTenOrLater: Boolean
        get() = isMiuiVersionAtLeast(10, 0)

    @JvmStatic
    public fun isMiuiVersionAtLeast(major: Int, minor: Int): Boolean {
        // E.g. "V12.5.3.0.QFGMIXM"
        var version = INCREMENTAL
        Log.i(TAG, "MIUI version $version")
        version = version.replace("[^\\d.]".toRegex(), "")
        val parts = version.split("\\.")
        if (parts.size >= 2) {
            try {
                val maj = parts[0].toInt()
                val min = parts[1].toInt()
                Log.i(TAG, "Parsed version $maj.$min")
                return maj > major || (maj == major && min >= minor)
            } catch (e: NumberFormatException) {
                // Fall through
            }
        }
        Log.w(TAG, "Could not parse MIUI version")
        return false
    }

    @JvmStatic
    public val xiaomiRecentAppsNeedsToBeShown: Boolean
        get() = isXiaomiOrRedmiDevice && !isMiuiVersionAtLeast(12, 5)

    @JvmStatic
    public fun xiaomiLockAppsNeedsToBeShown(context: Context): Boolean {
        if (!isXiaomiOrRedmiDevice || !isMiuiVersionAtLeast(12, 5)) return false
        val pm = context.packageManager
        val resolveInfos = pm.queryIntentActivities(xiaomiLockAppsIntent, MATCH_DEFAULT_ONLY)
        return resolveInfos.isNotEmpty()
    }

    @JvmStatic
    public val xiaomiLockAppsIntent: Intent = Intent().apply {
        setClassName(PACKAGE_NAME, CLASS_NAME)
    }
}
