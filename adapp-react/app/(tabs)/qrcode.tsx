import {useSafeAreaInsets} from "react-native-safe-area-context";
import {Alert, ScrollView, View, StyleSheet} from "react-native";
import {commonStyles} from "@/constants/style";
import {AccText, PageTitle} from "@/components/custom";
import {useCameraPermission} from "@/hooks/use-camera-permission";
import {useState} from "react";
import {CameraView} from "expo-camera";
import {Labels} from "@/constants/labels";

export default function QRCodeScreen() {
    const insets = useSafeAreaInsets();

    const hasPermission = useCameraPermission();
    const [scanned, setScanned] = useState(false);

    const handleBarcodeScanned = ({ type, data }) => {
      setScanned(true);
      Alert.alert(
        Labels.qrCode.alert.title.visual,
        Labels.qrCode.alert.content(data).visual,
        [{ text: Labels.qrCode.alert.dismiss.visual, onPress: () => setScanned(false) }],
      );
    };

    return(
        <View style={{ flex: 1, paddingTop: insets.top}}>
            <ScrollView
                style={commonStyles.scrollView}
                contentContainerStyle={[
                  commonStyles.contentContainer,
                  { paddingBottom: insets.bottom + 20 }
                ]}
              >
                <PageTitle accessibleText={Labels.qrCode.title} />

                <View style={styles.cameraContainer}>
                  {hasPermission === true ? (
                      <CameraView
                        onBarcodeScanned={scanned ? undefined : handleBarcodeScanned}
                        barcodeScannerSettings={{ barcodeTypes: ["qr"] }}
                        style={styles.cameraPreview}
                      />
                  ) : hasPermission === false ? (
                      <AccText
                        accessibleText={Labels.qrCode.cameraNotGranted}
                        style={styles.errorText} />
                  ) : (
                      <AccText accessibleText={Labels.qrCode.cameraLoading} />
                  )}
                </View>
            </ScrollView>
        </View>
    )
}

const styles = StyleSheet.create({
  cameraContainer: {
    width: '100%',
    alignItems: 'center',
    justifyContent: 'center',
    marginVertical: 20,
  },
  cameraPreview: {
    width: 300,
    height: 300,
    overflow: 'hidden',
  },
  errorText: {
    textAlign: 'center'
  },
});