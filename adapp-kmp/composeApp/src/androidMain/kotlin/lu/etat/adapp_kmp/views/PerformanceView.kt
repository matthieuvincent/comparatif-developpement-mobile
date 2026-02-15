package lu.etat.adapp_kmp.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lu.etat.adapp_kmp.composables.AccText
import lu.etat.adapp_kmp.composables.DataCard
import lu.etat.adapp_kmp.composables.ImagesList
import lu.etat.adapp_kmp.composables.PageTitle
import lu.etat.adapp_kmp.resources.AccessibleText
import lu.etat.adapp_kmp.resources.Labels.CPU_TEST_SECTION
import lu.etat.adapp_kmp.resources.Labels.MEMORY_TEST_SECTION
import lu.etat.adapp_kmp.resources.Labels.PERFORMANCE_TITLE
import lu.etat.adapp_kmp.resources.Labels.RUN_CPU_TEST
import lu.etat.adapp_kmp.resources.Labels.RUN_MEMORY_TEST
import lu.etat.adapp_kmp.resources.Labels.UI_TEST_SECTION
import lu.etat.adapp_kmp.resources.Labels.cpuAverageTime
import lu.etat.adapp_kmp.resources.Labels.cpuTestStatus
import lu.etat.adapp_kmp.resources.Labels.growthRate
import lu.etat.adapp_kmp.resources.Labels.intercept
import lu.etat.adapp_kmp.resources.Labels.memoryAverageTime
import lu.etat.adapp_kmp.resources.Labels.memoryTestStatus
import lu.etat.adapp_kmp.resources.Labels.volatility
import lu.etat.adapp_kmp.viewmodels.SharedPerformanceViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PerformanceView() {
    val performanceVM: SharedPerformanceViewModel = koinViewModel()

    val isEratosthenesRunning by performanceVM.isEratosthenesRunning.collectAsStateWithLifecycle()
    val cpuAverageElapsedTimePerCycle by performanceVM.cpuAverageElapsedTimePerCycle.collectAsStateWithLifecycle()

    val isMemoryRunning by performanceVM.isMemoryRunning.collectAsStateWithLifecycle()
    val growthRate by performanceVM.growthRate.collectAsStateWithLifecycle()
    val volatility by performanceVM.volatility.collectAsStateWithLifecycle()
    val intercept by performanceVM.intercept.collectAsStateWithLifecycle()
    val memoryAverageElapsedTimePerCycle by performanceVM.memoryAverageElapsedTimePerCycle.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp, 40.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        PageTitle(PERFORMANCE_TITLE)

        DataCard(CPU_TEST_SECTION) {
            AccText(cpuTestStatus(isEratosthenesRunning))
            AccText(cpuAverageTime(cpuAverageElapsedTimePerCycle))

            RunButton(
                accessibleText = RUN_CPU_TEST,
                automationId = "sieve_eratosthenes_button",
                isLoading = isEratosthenesRunning,
                onClick = { performanceVM.runSieveOfEratosthenes() }
            )
        }

        DataCard(MEMORY_TEST_SECTION) {
            AccText(memoryTestStatus(isMemoryRunning))
            AccText(growthRate(growthRate))
            AccText(volatility(volatility))
            AccText(intercept(intercept))
            AccText(memoryAverageTime(memoryAverageElapsedTimePerCycle))

            RunButton(
                accessibleText = RUN_MEMORY_TEST,
                automationId = "memory_test_button",
                isLoading = isMemoryRunning,
                onClick = { performanceVM.runMemoryStressTest() }
            )
        }

        DataCard(UI_TEST_SECTION) {
            ImagesList()
        }
    }
}

@Composable
fun RunButton(accessibleText: AccessibleText, automationId: String, isLoading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        modifier = Modifier.fillMaxWidth().height(50.dp).semantics { testTagsAsResourceId = true }.testTag(automationId),
        shape = RoundedCornerShape(8.dp)
    ) {
        AccText(accessibleText)
    }
}