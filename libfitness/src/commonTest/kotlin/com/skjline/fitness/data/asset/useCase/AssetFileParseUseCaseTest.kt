package com.skjline.fitness.data.asset.useCase

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AssetFileParseUseCaseTest {

    @Test
    fun testParseValidMrcFile() {
        val lines = listOf(
            "VERSION = 2",
            "UNITS = PERCENT",
            "DESCRIPTION = Test Workout",
            "FILE NAME = test.mrc",
            "[COURSE DATA]",
            "0\t50",
            "10\t100",
            "[END COURSE DATA]"
        )
        val file = "test.mrc"
        
        val useCase = AssetFileParseUseCase(file, lines)
        val result = useCase()
        
        assertNotNull(result)
        assertEquals("2", result.version)
        assertEquals("PERCENT", result.units)
        assertEquals("TestWorkout", result.description) 
        assertEquals("test.mrc", result.shortFileName)
        assertEquals(2, result.course.size)
        assertEquals(0f to 50f, result.course[0])
        assertEquals(10f to 100f, result.course[1])
    }

    @Test
    fun testParseEmptyFile() {
        val lines = emptyList<String>()
        val file = "empty.mrc"
        
        val useCase = AssetFileParseUseCase(file, lines)
        val result = useCase()
        
        assertNotNull(result)
        assertEquals("empty.mrc", result.shortFileName)
        assertEquals(0, result.course.size)
    }

    @Test
    fun testParseNoCourseData() {
        val lines = listOf(
            "VERSION = 2",
            "DESCRIPTION = No Data"
        )
        val file = "no_data.mrc"
        
        val useCase = AssetFileParseUseCase(file, lines)
        val result = useCase()
        
        assertNotNull(result)
        assertEquals("2", result.version)
        assertEquals("NoData", result.description)
        assertEquals(0, result.course.size)
    }
}
