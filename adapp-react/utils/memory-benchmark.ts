import {MemoryObject, MemoryTestResult} from "@/models/memory-object";

async function getUsedMemoryMb(): Promise<number> {
    const stats = global.HermesInternal.getInstrumentedStats();
    const totalBytes = stats['js_allocatedBytes'] + stats['js_externalBytes'];
    return totalBytes / (1024 * 1024);
}

function computeGrowthRate(samples: number[]): number {
    const n = samples.length;
    let sumX = 0;
    let sumY = 0;
    let sumXY = 0;
    let sumX2 = 0;

    for (let i = 0; i < n; i++) {
        const x = i + 1;
        const y = samples[i];
        sumX += x;
        sumY += y;
        sumXY += x * y;
        sumX2 += x * x;
    }

    const numerator = n * sumXY - sumX * sumY;
    const denominator = n * sumX2 - sumX * sumX;

    return denominator !== 0 ? numerator / denominator : 0;
}

function computeVolatility(samples: number[], slope: number): [number, number] {
    const n = samples.length;
    const sumX = (n * (n + 1)) / 2;
    const sumY = samples.reduce((acc, curr) => acc + curr, 0);
    const intercept = (sumY - slope * sumX) / n;

    let sumSqErr = 0;
    for (let i = 0; i < n; i++) {
        const x = i + 1;
        const yHat = slope * x + intercept;
        const err = samples[i] - yHat;
        sumSqErr += err * err;
    }

    const rmse = Math.sqrt(sumSqErr / n);
    return [rmse, intercept];
}

export async function runMemoryStressTest(cycles: number, objectCount: number, payloadSize: number, survivalRatePercent: number): Promise<MemoryTestResult> {
    const survivors: MemoryObject[] = [];
    const memorySamples: number[] = [];
    let totalMilliseconds = 0;

    for (let cycle = 1; cycle <= cycles; cycle++) {
        const start = performance.now();

        for (let i = 0; i < objectCount; i++) {
            const obj = new MemoryObject(payloadSize);
            if (i % Math.floor(100 / survivalRatePercent) === 0) {
                survivors.push(obj);
            }
        }

        const currentMemory = await getUsedMemoryMb();
        memorySamples.push(currentMemory);

        totalMilliseconds += (performance.now() - start);

        // Libère le thread pour mise à jour UI
        await new Promise(resolve => setTimeout(resolve, 1));
    }

    const slope = computeGrowthRate(memorySamples);
    const [rmse, intercept] = computeVolatility(memorySamples, slope);

    return {
        growthRate: slope,
        volatility: rmse,
        intercept,
        memoryAverageElapsedTimePerCycle: totalMilliseconds / cycles
    };
};