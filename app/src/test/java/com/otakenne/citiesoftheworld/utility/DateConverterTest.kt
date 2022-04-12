package com.otakenne.citiesoftheworld.utility

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DateConverterTest {
    @Test
    fun makeDateReadableTest() {
        val dateMillis = 1649793414626
        val target = "2022-04-12 20:56:54"
        val result = DateConverter.makeDateReadable(dateMillis)
        assertThat(result).isEqualTo(target)
    }
}