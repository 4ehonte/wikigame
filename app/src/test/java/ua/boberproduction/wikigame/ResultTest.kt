package ua.boberproduction.wikigame

import org.junit.Test
import ua.boberproduction.wikigame.models.Result

class ResultTest {

    @Test
    fun `test points calculation with zero seconds`() {
        // 5 clicks, 0 seconds
        val result = Result(1, "", "", 5, 0, 0)

        val points = result.getPoints()
        val maxPoints = result.level * 1000

        assert(points == (maxPoints * 0.6).toInt() / 5 + (maxPoints * 0.4).toInt())
    }

    @Test
    fun `test points calculation with zero seconds and one click`() {
        // 5 clicks, 0 seconds
        val result = Result(1, "", "", 1, 0, 0)

        val points = result.getPoints()
        val maxPoints = result.level * 1000

        assert(points == maxPoints)
    }

    @Test(expected = ArithmeticException::class)
    fun `test points calculation with zero seconds and zero clicks`() {
        // 5 clicks, 0 seconds
        val result = Result(1, "", "", 0, 0, 0)

        val points = result.getPoints()
        val maxPoints = result.level * 1000

        assert(points == maxPoints)
    }
}