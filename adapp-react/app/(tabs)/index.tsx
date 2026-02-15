import React from 'react';
import {View, ScrollView, StyleSheet} from 'react-native';
import { useSafeAreaInsets } from 'react-native-safe-area-context';
import {PageTitle, DataCard, AccText} from '@/components/custom';
import {commonStyles} from "@/constants/style";
import * as Device from "expo-device"
import {useAccelerometer} from "@/hooks/use-accelerometer";
import {useLocation} from "@/hooks/use-location";
import {useBatteryLevel} from "@/hooks/use-battery-level";
import {Labels} from "@/constants/labels";

export default function DeviceScreen() {

  const insets = useSafeAreaInsets();
  const { x, y, z } = useAccelerometer();
  const { latitude, longitude, altitude } = useLocation();
  const level = useBatteryLevel()

  return (
    <View style={{ flex: 1, paddingTop: insets.top}}>
      <ScrollView
        style={commonStyles.scrollView}
        contentContainerStyle={[
          commonStyles.contentContainer,
          { paddingBottom: insets.bottom + 20 }
        ]}
      >
        <PageTitle accessibleText={Labels.device.title} />

        <DataCard accessibleText={Labels.device.deviceInfoSection}>
          <AccText
            accessibleText={ Labels.device.deviceName(Device.deviceName) }
            style={commonStyles.text} />
          <AccText
            accessibleText={ Labels.device.manufacturer(Device.manufacturer) }
            style={commonStyles.text} />
          <AccText
            accessibleText={ Labels.device.model(Device.modelName) }
            style={commonStyles.text} />
          <AccText
            accessibleText={ Labels.device.osVersion(Device.osVersion) }
            style={commonStyles.text} />
        </DataCard>

        <DataCard accessibleText={Labels.device.liveMetricsSection}>
          <AccText
            accessibleText={Labels.device.accelrometerX(x)}
            style={commonStyles.text} />
          <AccText
            accessibleText={Labels.device.accelrometerY(y)}
            style={commonStyles.text} />
          <AccText
            accessibleText={Labels.device.accelrometerZ(z)}
            style={commonStyles.text} />

          <View style={styles.spacer} />

          <AccText
            accessibleText={Labels.device.latitude(latitude)}
            style={commonStyles.text} />
          <AccText
            accessibleText={Labels.device.longitude(longitude)}
            style={commonStyles.text} />
          <AccText
            accessibleText={Labels.device.altitude(altitude)}
            style={commonStyles.text} />

          <View style={styles.spacer} />

          <AccText
            accessibleText={Labels.device.batteryLevel(Math.round(level * 100))}
            style={commonStyles.text} />
        </DataCard>
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  spacer: {
    height: 10,
  }
});