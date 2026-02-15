package lu.etat.adapp_kmp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import lu.etat.adapp_kmp.resources.TabItem
import lu.etat.adapp_kmp.resources.icon
import lu.etat.adapp_kmp.views.DeviceView
import lu.etat.adapp_kmp.views.PerformanceView
import lu.etat.adapp_kmp.views.QRCodeView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
                TabRow(selectedTabIndex = pagerState.currentPage) {
                    Tab(
                        selected = pagerState.currentPage == 0,
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(0) }
                        },
                        text = { Text(TabItem.DEVICE.title) },
                        icon = { Icon(TabItem.DEVICE.icon(), contentDescription = null) }
                    )
                    Tab(
                        selected = pagerState.currentPage == 1,
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(1) }
                        },
                        text = { Text(TabItem.QR_CODE.title) },
                        icon = { Icon(TabItem.QR_CODE.icon(), contentDescription = null) }
                    )
                    Tab(
                        selected = pagerState.currentPage == 2,
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(2) }
                        },
                        text = { Text(TabItem.PERFORMANCES.title) },
                        icon = { Icon(TabItem.PERFORMANCES.icon(), contentDescription = null) }
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) { pageIndex ->
            Surface(modifier = Modifier.fillMaxSize()) {
                when (pageIndex) {
                    0 -> DeviceView()
                    1 -> QRCodeView()
                    2 -> PerformanceView()
                }
            }
        }
    }
}
