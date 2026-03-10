import {ScrollView, View, Button, FlatList} from "react-native";
import {AccText, DataCard, ImageList, PageTitle} from "@/components/custom";
import {commonStyles} from "@/constants/style";
import {useSafeAreaInsets} from "react-native-safe-area-context";
import {useCallback, useState} from "react";
import {runSieveOfEratosthenes} from "@/utils/cpu-benchmark";
import {MemoryTestResult} from "@/models/memory-object";
import {runMemoryStressTest} from "@/utils/memory-benchmark";
import {Labels} from "@/constants/labels";

const ERATOSTHENES_LIMIT = 10_000_000;
const ERATOSTHENES_CYCLES = 10;

const MEMORY_STRESS_OBJECT_COUNT = 1_000_000;
const MEMORY_STRESS_PAYLOAD_SIZE = 256;
const MEMORY_STRESS_SURVIVAL_RATE_PERCENT = 10;
const MEMORY_STRESS_CYCLES = 10;

const UI_TEST_LIMIT = 10_000;
const IMAGES = [
    require('@/assets/images/image1.jpg'),
    require('@/assets/images/image2.jpg'),
    require('@/assets/images/image3.jpg'),
    require('@/assets/images/image4.jpg'),
];

export default function PerformanceScreen() {
    const insets = useSafeAreaInsets();

    const [isEratosthenesRunning, setIsEratosthenesRunning] = useState(false);
    const [cpuAverageElapsedTimePerCycle, setCpuAverageElapsedTimePerCycle] = useState<number | null>(null);

    const [isMemoryRunning, setIsMemoryRunning] = useState(false);
    const [memoryResults, setMemoryResults] = useState<MemoryTestResult | null>(null);

    const runCpuTest = async () => {
        setIsEratosthenesRunning(true);
        setCpuAverageElapsedTimePerCycle(null);

        setTimeout(async () => {
          try {
            setCpuAverageElapsedTimePerCycle(await runSieveOfEratosthenes(ERATOSTHENES_LIMIT, ERATOSTHENES_CYCLES));
          } finally {
            setIsEratosthenesRunning(false);
          }
        }, 50);
    };

    const runMemoryTest = async () => {
        setIsMemoryRunning(true);
        try {
            setMemoryResults(await runMemoryStressTest(MEMORY_STRESS_CYCLES, MEMORY_STRESS_OBJECT_COUNT, MEMORY_STRESS_PAYLOAD_SIZE, MEMORY_STRESS_SURVIVAL_RATE_PERCENT));
        } finally {
            setIsMemoryRunning(false);
        }
    }

    const listData = Array.from({ length: UI_TEST_LIMIT }, (_, index) => ({
      id: index.toString(),
      imageSource: IMAGES[index % 4],
    }));

    // useCallback est important pour garder la même référence de fonction
    const renderItem = useCallback(({ item }) => (
      <ImageList imageSource={item.imageSource} />
    ), []);

    return(
        <View style={{ flex: 1, paddingTop: insets.top}}>
            <ScrollView
                style={commonStyles.scrollView}
                contentContainerStyle={[
                  commonStyles.contentContainer,
                  { paddingBottom: insets.bottom + 20 }
                ]}
              >
                <PageTitle accessibleText={Labels.performance.title} />

                <DataCard accessibleText={Labels.performance.cpuTestSection}>
                    <AccText
                        accessibleText={Labels.performance.cpuTestStatus(isEratosthenesRunning)}
                        style={commonStyles.text} />
                    <AccText
                        accessibleText={Labels.performance.cpuAverageTime(cpuAverageElapsedTimePerCycle)}
                        style={commonStyles.text} />

                    <Button
                        testID="sieve_eratosthenes_button"
                        title={Labels.performance.runCpuTest.visual}
                        onPress={runCpuTest}
                        disabled={isEratosthenesRunning}
                        accessibilityLabel={Labels.performance.runCpuTest.accessibility}/>
                </DataCard>

                <DataCard accessibleText={Labels.performance.memoryTestSection}>
                    <AccText
                        accessibleText={Labels.performance.memoryTestStatus(isMemoryRunning)}
                        style={commonStyles.text} />
                    <AccText
                        accessibleText={Labels.performance.growthRate(memoryResults?.growthRate)}
                        style={commonStyles.text} />
                    <AccText
                        accessibleText={Labels.performance.volatility(memoryResults?.volatility)}
                        style={commonStyles.text} />
                    <AccText
                        accessibleText={Labels.performance.intercept(memoryResults?.intercept)}
                        style={commonStyles.text} />
                    <AccText
                        accessibleText={Labels.performance.memoryAverageTime(memoryResults?.memoryAverageElapsedTimePerCycle)}
                        style={commonStyles.text} />

                    <Button
                        testID="memory_test_button"
                        title={Labels.performance.runMemoryTest.visual}
                        onPress={runMemoryTest}
                        disabled={isMemoryRunning}
                        accessibilityLabel={Labels.performance.runMemoryTest.accessibility}/>
                </DataCard>

                <DataCard accessibleText={Labels.performance.uiTestSection}>
                    <FlatList
                        nestedScrollEnabled={true}
                        scrollEnabled={true}
                        data={listData}
                        renderItem={renderItem}
                        keyExtractor={item => item.id}
                        removeClippedSubviews={true}
                        style={{ height: 650 }}
                      />
                </DataCard>
              </ScrollView>
        </View>
    )
}