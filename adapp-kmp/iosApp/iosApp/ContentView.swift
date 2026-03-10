import SwiftUI
import Shared

struct ContentView: View {
    let deviceViewModel = KoinHelper.shared.getDeviceViewModel()
    let performanceViewModel = KoinHelper.shared.getPerformanceViewModel()

    var body: some View {

        TabView {
            Tab(TabItem.device.title, systemImage: TabItem.device.icon()) {
                DeviceView(viewModel: deviceViewModel)
            }

            Tab(TabItem.qrCode.title, systemImage: TabItem.qrCode.icon()) {
                QRCodeView()
            }

            Tab(TabItem.performances.title, systemImage: TabItem.performances.icon()) {
                PerformanceView(viewModel: performanceViewModel)
            }
        }
    }
}
