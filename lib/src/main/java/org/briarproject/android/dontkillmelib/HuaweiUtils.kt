package org.briarproject.android.dontkillmelib

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.MATCH_DEFAULT_ONLY
import android.os.Build.VERSION.SDK_INT

public object HuaweiUtils {

    private const val PACKAGE_NAME_HUAWEI = "com.huawei.systemmanager"
    private const val CLASS_NAME_POWER_MANAGER =
        "${PACKAGE_NAME_HUAWEI}.power.ui.HwPowerManagerActivity"
    private const val CLASS_NAME_PROTECTED_APPS =
        "${PACKAGE_NAME_HUAWEI}.optimize.process.ProtectActivity"

    /**
     * Determine whether a Huawei "Protected apps" feature is available on the
     * device.
     */
    @JvmStatic
    public fun appLaunchNeedsToBeShown(context: Context): Boolean {
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
    public fun protectedAppsNeedsToBeShown(context: Context): Boolean {
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

}
