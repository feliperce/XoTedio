package br.com.mobileti.commons.extensions

import org.junit.Test
import java.util.*


internal class DateExtensionsKtTest {

    @Test
    fun `GIVEN date THEN get passed hours`() {
        val hours24 = Date(1681401600000).getPassedHours(Date(1681315200000))
        val hours1 = Date(1681405200000).getPassedHours(Date(1681408800000))
        val hours12 = Date(1681239600000).getPassedHours(Date(1681282800000))
        val hours0 = Date(1681282800000).getPassedHours(Date(1681282800000))

        assert(hours24 == 24L)
        assert(hours1 == 1L)
        assert(hours12 == 12L)
        assert(hours0 == 0L)
    }
}