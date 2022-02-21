package org.briarproject.android.dontkillmelib

import android.content.Context

public class DozeHelperImpl : DozeHelper {
    override fun needToShowDoNotKillMeFragment(context: Context): Boolean {
        val appContext = context.applicationContext
        return PowerUtils.needsDozeWhitelisting(appContext) ||
            PowerUtils.huaweiProtectedAppsNeedsToBeShown(appContext) ||
            PowerUtils.huaweiAppLaunchNeedsToBeShown(appContext) ||
            PowerUtils.isXiaomiOrRedmiDevice
    }
}
