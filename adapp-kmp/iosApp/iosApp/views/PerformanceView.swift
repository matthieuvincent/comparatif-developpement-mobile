import SwiftUI
import Shared

struct PerformanceView: View {
    let viewModel: SharedPerformanceViewModel

    @State private var isEratosthenesRunning: Bool = false
    @State private var cpuAverageElapsedTimePerCycle: Double = 0.0

    @State private var isMemoryRunning = false
    @State private var growthRate: Double = 0.0
    @State private var volatility: Double = 0.0
    @State private var intercept: Double = 0.0
    @State private var memoryAverageElapsedTimePerCycle: Double = 0.0

    @State private var items: [String] = []

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 15) {
                Title(accessibleText: Labels.shared.PERFORMANCE_TITLE)

                VStack(alignment: .leading, spacing: 10) {
                    AccText(accessibleText: Labels.shared.CPU_TEST_SECTION)
                        .font(.system(size: 15, weight: .bold))

                    AccText(accessibleText: Labels.shared.cpuTestStatus(isRunning: isEratosthenesRunning))
                    AccText(accessibleText: Labels.shared.cpuAverageTime(cpuAverageTime: cpuAverageElapsedTimePerCycle))

                    Button(action: { viewModel.runSieveOfEratosthenes() }) {
                        AccText(accessibleText: Labels.shared.RUN_CPU_TEST)
                            .frame(maxWidth: .infinity, minHeight: 50)
                            .background(isEratosthenesRunning ? Color.gray : Color.blue)
                            .foregroundColor(.white)
                            .cornerRadius(5)
                    }
                    .accessibilityIdentifier("sieve_eratosthenes_button")
                    .disabled(isEratosthenesRunning)
                }
                .modifier(CardStyle())
                .task {
                    for await running in viewModel.isEratosthenesRunning {
                        self.isEratosthenesRunning = running as! Bool
                    }
                }
                .task {
                    for await averageElapsedTime in viewModel.cpuAverageElapsedTimePerCycle {
                        self.cpuAverageElapsedTimePerCycle = averageElapsedTime as! Double
                    }
                }

                VStack(alignment: .leading, spacing: 10) {
                    AccText(accessibleText: Labels.shared.MEMORY_TEST_SECTION)
                        .font(.system(size: 15, weight: .bold))

                    AccText(accessibleText: Labels.shared.memoryTestStatus(isRunning: isMemoryRunning))
                    AccText(accessibleText: Labels.shared.growthRate(growthRate: growthRate))
                    AccText(accessibleText: Labels.shared.volatility(volatility: volatility))
                    AccText(accessibleText: Labels.shared.intercept(intercept: intercept))
                    AccText(accessibleText: Labels.shared.memoryAverageTime(memoryAverageTime: memoryAverageElapsedTimePerCycle))

                    Button(action: { viewModel.runMemoryStressTest() }) {
                        AccText(accessibleText: Labels.shared.RUN_MEMORY_TEST)
                            .frame(maxWidth: .infinity, minHeight: 50)
                            .background(isMemoryRunning ? Color.gray : Color.blue)
                            .foregroundColor(.white)
                            .cornerRadius(5)
                    }
                    .accessibilityIdentifier("memory_test_button")
                    .disabled(isMemoryRunning)
                }
                .modifier(CardStyle())
                .task {
                    for await running in viewModel.isMemoryRunning {
                        self.isMemoryRunning = running as! Bool
                    }
                }
                .task {
                    for await growthRate in viewModel.growthRate {
                        self.growthRate = growthRate as! Double
                    }
                }
                .task {
                    for await volatility in viewModel.volatility {
                        self.volatility = volatility as! Double
                    }
                }
                .task {
                    for await intercept in viewModel.intercept {
                        self.intercept = intercept as! Double
                    }
                }
                .task {
                    for await averageElapsedTime in viewModel.memoryAverageElapsedTimePerCycle {
                        self.memoryAverageElapsedTimePerCycle = averageElapsedTime as! Double
                    }
                }

                VStack(alignment: .leading, spacing: 10) {
                    AccText(accessibleText: Labels.shared.UI_TEST_SECTION)
                        .font(.system(size: 15, weight: .bold))

                    KmpViewControllerBridge(makeController: MainViewControllerKt.PerformanceController).frame(width: .infinity, height: 650)
                }
                .modifier(CardStyle())
            }
            .padding(EdgeInsets(top: 40, leading: 20, bottom: 20, trailing: 20))
        }
    }
}


