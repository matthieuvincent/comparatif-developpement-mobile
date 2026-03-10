package lu.etat.adapp_kmp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lu.etat.adapp_kmp.models.MemoryObjectData
import lu.etat.adapp_kmp.models.MemoryTestResultData
import lu.etat.adapp_kmp.utils.ComputeUtils
import lu.etat.adapp_kmp.utils.MemoryUtils
import kotlin.time.DurationUnit
import kotlin.time.measureTime

private const val ERATOSTHENES_LIMIT = 10_000_000
private const val ERATOSTHENES_CYCLES = 10

private const val MEMORY_STRESS_OBJECT_COUNT = 1_000_000;
private const val MEMORY_STRESS_PAYLOAD_SIZE = 256;
private const val MEMORY_STRESS_SURVIVAL_RATE_PERCENT = 10;
private const val MEMORY_STRESS_CYCLES = 10;

class SharedPerformanceViewModel: ViewModel() {

    private val _isEratosthenesRunning = MutableStateFlow(false)
    val isEratosthenesRunning: StateFlow<Boolean> = _isEratosthenesRunning.asStateFlow()

    private val _cpuAverageElapsedTimePerCycle = MutableStateFlow(0.0)
    val cpuAverageElapsedTimePerCycle: StateFlow<Double> = _cpuAverageElapsedTimePerCycle.asStateFlow()

    private val _isMemoryRunning = MutableStateFlow(false)
    val isMemoryRunning: StateFlow<Boolean> = _isMemoryRunning.asStateFlow()

    private val _growthRate = MutableStateFlow(0.0)
    val growthRate: StateFlow<Double> = _growthRate.asStateFlow()

    private val _volatility = MutableStateFlow(0.0)
    val volatility: StateFlow<Double> = _volatility.asStateFlow()

    private val _intercept = MutableStateFlow(0.0)
    val intercept: StateFlow<Double> = _intercept.asStateFlow()

    private val _memoryAverageElapsedTimePerCycle = MutableStateFlow(0.0)
    val memoryAverageElapsedTimePerCycle: StateFlow<Double> = _memoryAverageElapsedTimePerCycle.asStateFlow()

    fun runSieveOfEratosthenes() {
        viewModelScope.launch {
            _isEratosthenesRunning.value = true

            try {
                var totalMilliseconds: Long = 0

                withContext(Dispatchers.Default) {
                    for (i in 0 until ERATOSTHENES_CYCLES) {
                        val duration = measureTime {
                            ComputeUtils.computeSieveOfEratosthenes(ERATOSTHENES_LIMIT)
                        }
                        totalMilliseconds += duration.toLong(DurationUnit.MILLISECONDS)
                    }
                }

                _cpuAverageElapsedTimePerCycle.value = totalMilliseconds.toDouble() / ERATOSTHENES_CYCLES
            } finally {
                _isEratosthenesRunning.value = false
            }
        }
    }

    fun runMemoryStressTest() {
        viewModelScope.launch {
            _isMemoryRunning.value = true

            try {
                val survivors = mutableListOf<MemoryObjectData>();
                val memorySamples = ArrayList<Double>(MEMORY_STRESS_CYCLES)
                var totalMilliseconds: Long = 0

                withContext(Dispatchers.Default) {
                    val result = run {
                        for (cycle in 1..MEMORY_STRESS_CYCLES) {
                            val duration = measureTime {
                                for (i in 0 until MEMORY_STRESS_OBJECT_COUNT) {
                                    val memoryObjectData = MemoryObjectData(MEMORY_STRESS_PAYLOAD_SIZE)

                                    if (i % (100 / MEMORY_STRESS_SURVIVAL_RATE_PERCENT) == 0)
                                        survivors.add(memoryObjectData)
                                }

                                memorySamples.add(MemoryUtils.getUsedMemoryMb())
                            }
                            totalMilliseconds += duration.toLong(DurationUnit.MILLISECONDS)
                        }

                        val slope = ComputeUtils.computeGrowthRate(memorySamples)
                        val (rmse, intercept) = ComputeUtils.computeVolatility(memorySamples, slope)

                        MemoryTestResultData(intercept, slope, rmse, (totalMilliseconds / MEMORY_STRESS_CYCLES.toDouble()))
                    }

                    _growthRate.value = result.slope
                    _volatility.value = result.rmse
                    _intercept.value = result.intercept
                    _memoryAverageElapsedTimePerCycle.value = result.averageElapsedTimePerCycle
                }

                memorySamples.clear()
                survivors.clear()
            } finally {
                _isMemoryRunning.value = false
            }
        }
    }
}