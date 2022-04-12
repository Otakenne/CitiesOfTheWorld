package com.otakenne.citiesoftheworld.utility

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MathTest {
    @Test
    fun roundCoordinateTest() {
        val target = "65.23942"
        val result = Math.roundCoordinate(65.2394223, 5)
        assertThat(target).isEqualTo(result)
    }

}