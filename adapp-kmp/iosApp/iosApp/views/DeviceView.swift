import SwiftUI
import Shared

struct DeviceView: View {
    let viewModel: SharedDeviceViewModel

    @State private var accelerometerData: AccelerometerData?
    @State private var geolocationData: GeolocationData?
    @State private var batteryData: BatteryData?

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 15) {
                Title(accessibleText: Labels.shared.DEVICE_TITLE)

                VStack(alignment: .leading, spacing: 10) {
                    AccText(accessibleText: Labels.shared.DEVICE_INFO_SECTION)
                        .font(.system(size: 15, weight: .bold))

                    AccText(accessibleText: Labels.shared.deviceName(deviceName: viewModel.deviceDetails.name))
                    AccText(accessibleText: Labels.shared.manufacturer(manufacturer: viewModel.deviceDetails.manufacturer))
                    AccText(accessibleText: Labels.shared.model(model: viewModel.deviceDetails.model))
                    AccText(accessibleText: Labels.shared.osVersion(osVersion: viewModel.deviceDetails.osVersion))
                }
                .modifier(CardStyle())

                VStack(alignment: .leading, spacing: 10) {
                    AccText(accessibleText: Labels.shared.LIVE_METRICS_SECTION)
                        .font(.system(size: 15, weight: .bold))

                    Group {
                        AccText(accessibleText: Labels.shared.accelerometerX(accelerometerX: accelerometerData?.x ?? 0))
                        AccText(accessibleText: Labels.shared.accelerometerY(accelerometerY: accelerometerData?.y ?? 0))
                        AccText(accessibleText: Labels.shared.accelerometerZ(accelerometerZ: accelerometerData?.z ?? 0))
                            .padding(.bottom, 10)
                    }

                    Group {
                        AccText(accessibleText: Labels.shared.latitude(latitude: KotlinDouble(value: geolocationData?.latitude ?? 0.0)))
                        AccText(accessibleText: Labels.shared.longitude(longitude: KotlinDouble(value: geolocationData?.longitude ?? 0.0)))
                        AccText(accessibleText: Labels.shared.altitude(altitude: KotlinDouble(value: geolocationData?.altitude ?? 0)))
                            .padding(.bottom, 10)
                    }

                    AccText(accessibleText: Labels.shared.batteryLevel(batteryLevel: batteryData?.level ?? 0))
                }
                .modifier(CardStyle())
            }
            .padding(EdgeInsets(top: 40, leading: 20, bottom: 20, trailing: 20))
            .task {
                for await newAccelerometerData in viewModel.accelerometerData {
                    self.accelerometerData = newAccelerometerData
                }
            }
            .task {
                for await newGeolocationData in viewModel.geolocationData {
                    self.geolocationData = newGeolocationData
                }
            }
            .task {
                for await newBatteryData in viewModel.batteryData {
                    self.batteryData = newBatteryData
                }
            }
        }
    }
}