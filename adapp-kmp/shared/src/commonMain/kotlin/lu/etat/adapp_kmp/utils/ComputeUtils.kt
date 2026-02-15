package lu.etat.adapp_kmp.utils

import kotlin.math.ln
import kotlin.math.sqrt

object ComputeUtils {

    fun computeSieveOfEratosthenes(limit: Int): List<Int> {
        if (limit < 2) return emptyList()

        val isPrime = BooleanArray(limit + 1) { true }

        isPrime[0] = false
        isPrime[1] = false

        val limit = sqrt(limit.toDouble()).toInt()

        for (i in 2..limit) {
            if (!isPrime[i]) continue

            for (j in (i * i)..limit step i) {
                isPrime[j] = false
            }
        }

        val approxCount = (limit / ln(limit.toDouble())).toInt()
        val primes = ArrayList<Int>(approxCount)

        for (i in 2..limit) {
            if (isPrime[i]) {
                primes.add(i)
            }
        }

        return primes
    }

    fun computeGrowthRate(samples: List<Double>): Double {
        val n = samples.size
        var sumX = 0.0
        var sumY = 0.0
        var sumXY = 0.0
        var sumX2 = 0.0

        for (i in samples.indices) {
            val x = i + 1
            val y = samples[i]
            sumX += x
            sumY += y
            sumXY += x * y
            sumX2 += x * x
        }

        val numerator = n * sumXY - sumX * sumY
        val denominator = n * sumX2 - sumX * sumX
        return if (denominator != 0.0)
            numerator / denominator
        else
            0.0
    }

    fun computeVolatility(samples: List<Double>, slope: Double): Pair<Double, Double> {
        val n = samples.size
        val sumX = n * (n + 1) / 2.0
        val sumY = samples.sum()
        val intercept = (sumY - slope * sumX) / n

        var sumSqErr = 0.0
        for (i in samples.indices) {
            val x = i + 1
            val yHat = slope * x + intercept
            val err = samples[i] - yHat
            sumSqErr += err * err
        }

        val rmse = sqrt(sumSqErr / n)
        return Pair(rmse, intercept)
    }
}