package org.briarproject.android.dontkillmelib

import android.os.Build.BRAND
import java.io.IOException
import java.util.Scanner

public object XiaomiUtils {

    @JvmStatic
    public val isXiaomiOrRedmiDevice: Boolean
        get() = "Xiaomi".equals(BRAND, ignoreCase = true) ||
            "Redmi".equals(BRAND, ignoreCase = true)

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
