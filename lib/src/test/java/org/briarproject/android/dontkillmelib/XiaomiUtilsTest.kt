package org.briarproject.android.dontkillmelib

import org.briarproject.android.dontkillmelib.XiaomiUtils.parseMiuiVersion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

public class XiaomiUtilsTest {

    @Test
    public fun testParsesMiuiVersion() {
        val version = "V12.5.3.0.QFGMIXM"
        assertEquals(Pair(12, 5), parseMiuiVersion(version))
    }
}
